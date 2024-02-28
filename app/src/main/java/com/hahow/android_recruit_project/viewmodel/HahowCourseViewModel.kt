package com.hahow.android_recruit_project.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hahow.android_recruit_project.apiservice.ApiResult
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.datamodel.CourseList
import com.hahow.android_recruit_project.repository.HahowCourseRepository
import com.hahow.android_recruit_project.room.CourseDao
import com.hahow.android_recruit_project.utils.JsonTransferTypeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HahowCourseViewModel @Inject constructor(
    private val repository: HahowCourseRepository,
    private val savedStateHandle: SavedStateHandle,
    private val jsonTransferTypeUtils: JsonTransferTypeUtils
) : ViewModel() {
    val clickListener = ObservableField<View.OnClickListener>()
    val courseList: MutableLiveData<List<CourseData>?> = MutableLiveData()
    var progressBarLoading: MutableLiveData<Boolean> = MutableLiveData()
    var swipeStatus: MutableLiveData<Boolean> = MutableLiveData()
    private var cacheList: List<CourseData> = listOf()

    fun fetchCourseData(courseDao: CourseDao, isSwipe: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (shouldFetchData(courseDao, isSwipe)) {
                fetchData(courseDao, isSwipe)
            } else {
                courseList.postValue(courseDao.getAllCourses())
                updateLoadingStatus(false, isSwipe)
            }
        }
    }

    private fun shouldFetchData(courseDao: CourseDao, isSwipe: Boolean): Boolean {
        return courseDao.getAllCourses().isEmpty() || isSwipe
    }

    private suspend fun fetchData(courseDao: CourseDao, isSwipe: Boolean) {
        if (isSwipe) {
            courseDao.deleteAll()
        }
        when (val result = parseCourseData(repository.loadCourseData(), isSwipe)) {
            is ApiResult.Success -> {
                handleSuccessResult(result.data, courseDao, isSwipe)
            }
            else -> {
                handleFailureResult(isSwipe)
            }
        }
    }

    private suspend fun handleSuccessResult(data: List<CourseData>, courseDao: CourseDao, isSwipe: Boolean) {
        cacheList = emptyList()
        courseList.postValue(data)
        courseDao.insertAll(data)
        cacheList = courseDao.getAllCourses()
        updateLoadingStatus(false, isSwipe)
    }

    private fun handleFailureResult(isSwipe: Boolean) {
        updateLoadingStatus(false, isSwipe)
    }

    private fun updateLoadingStatus(isLoading: Boolean, isSwipe: Boolean) {
        if (isSwipe) {
            swipeStatus.postValue(isLoading)
        } else {
            progressBarLoading.postValue(isLoading)
        }
    }

    /**
     * 解析課程data並返回相應的 [ApiResult]。
     * @param jsonString 包含課程data的 JSON 字符串
     * @param isSwipe 是否是滑動刷新操作
     * @return [ApiResult] 對象，包含成功、失敗或完成的狀態
     */
    private suspend fun parseCourseData(
        jsonString: String,
        isSwipe: Boolean
    ): ApiResult<List<CourseData>> {
        return withContext(Dispatchers.Default) {
            try {
                updateLoadingStatus(true, isSwipe)
                val courseList = jsonTransferTypeUtils.parse<CourseList>(jsonString).data
                ApiResult.Success(courseList)
            } catch (e: Exception) {
                ApiResult.Error(e.message ?: "David is handsome")
            } finally {
                delay(2000)
                ApiResult.Complete
            }
        }
    }
}