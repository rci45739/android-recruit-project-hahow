package com.hahow.android_recruit_project.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.listener.ApiStatusListener
import com.hahow.android_recruit_project.repository.HahowCourseRepository
import com.hahow.android_recruit_project.room.CourseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HahowCourseViewModel(application: Application ) : AndroidViewModel
    (application),
    ApiStatusListener {
    val clickListener = ObservableField<View.OnClickListener>()
    private val repository: HahowCourseRepository = HahowCourseRepository(application , viewModelScope)
    val courseList: MutableLiveData<List<CourseData>> = repository.courseList
   /* var progressBarLoading: MutableLiveData<Boolean> = repository.isLoading
    var swipeStatus: MutableLiveData<Boolean> = MutableLiveData()*/
    var progressBarLoading: MutableLiveData<Boolean> = MutableLiveData()
    var swipeStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchCourseData(courseDao: CourseDao , isSwipe:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (courseDao.getAllCourses().isNotEmpty() && !isSwipe) {
                courseList.postValue(courseDao.getAllCourses())
            } else {
                if (isSwipe) {
                    courseDao.deleteAll()
                }
                repository.fetchCourseData(courseDao ,this@HahowCourseViewModel)
            }
        }
    }

    override fun onSuccess() {
       progressBarLoading.postValue(true)
    }

    override fun onFailure() {
        progressBarLoading.postValue(false)
        swipeStatus.postValue(false)
    }

    override fun onComplete() {
        progressBarLoading.postValue(false)
        swipeStatus.postValue(false)
    }
}