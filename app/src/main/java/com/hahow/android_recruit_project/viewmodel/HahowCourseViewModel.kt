package com.hahow.android_recruit_project.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.repository.HahowCourseRepository

class HahowCourseViewModel(application: Application) : AndroidViewModel(application) {
    val clickListener = ObservableField<View.OnClickListener>()
    private val repository: HahowCourseRepository = HahowCourseRepository(application , viewModelScope)
    val courseList: MutableLiveData<List<CourseData>> = repository.courseList

    fun fetchCourseData() {
        repository.fetchCourseData()
    }
}