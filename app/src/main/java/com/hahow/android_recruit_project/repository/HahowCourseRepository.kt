package com.hahow.android_recruit_project.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.datamodel.CourseList
import com.hahow.android_recruit_project.apiservice.ApiResult
import com.hahow.android_recruit_project.room.CourseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class HahowCourseRepository(
    private val application: Application, private val coroutineScope
    : CoroutineScope
) {
    val courseList: MutableLiveData<List<CourseData>> = MutableLiveData()
    private var cacheList: List<CourseData> = listOf()
    private val gson: Gson = Gson()
    var progressBarLoading: MutableLiveData<Boolean> = MutableLiveData()
    var swipeStatus: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun fetchCourseData(
        courseDao: CourseDao, isSwipe: Boolean
    ) {
        val jsonString = loadCourseData()
        val result = parseCourseData(jsonString, isSwipe)
        when (result) {
            is ApiResult.Success -> {
                cacheList = emptyList()
                courseList.postValue(result.data)
                courseDao.insertAll(result.data)
                cacheList = courseDao.getAllCourses()
                if(isSwipe){
                    swipeStatus.postValue(false)
                }else{
                    progressBarLoading.postValue(false)
                }
            }
            is ApiResult.Error -> {
                swipeStatus.postValue(false)
                progressBarLoading.postValue(false)
            }

            else -> {
                swipeStatus.postValue(false)
                progressBarLoading.postValue(false)
            }
        }

    }

    private fun loadCourseData(): String {
        return application.assets.open("data.json").bufferedReader().use { it.readText() }
    }

    private suspend fun parseCourseData(
        jsonString: String,
        isSwipe: Boolean
    ): ApiResult<List<CourseData>> {
        return withContext(Dispatchers.Default) {
            try {
                if(isSwipe){
                    swipeStatus.postValue(true)
                }else{
                    progressBarLoading.postValue(true)
                }
                val courseType = object : TypeToken<CourseList>() {}.type
                val courseList = gson.fromJson<CourseList>(jsonString, courseType).data
                ApiResult.Success(courseList)
            } catch (e: Exception) {
                ApiResult.Error(e.message ?: "An error occurred")
            } finally {
                delay(2000)
                ApiResult.Complete
            }
        }
    }
}