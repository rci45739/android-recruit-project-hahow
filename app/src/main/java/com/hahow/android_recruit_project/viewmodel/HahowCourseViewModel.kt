package com.hahow.android_recruit_project.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.repository.HahowCourseRepository
import com.hahow.android_recruit_project.room.CourseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HahowCourseViewModel @Inject constructor( private val repository:
HahowCourseRepository, private val savedStateHandle: SavedStateHandle
) : ViewModel(){
    val clickListener = ObservableField<View.OnClickListener>()
    val courseList: MutableLiveData<List<CourseData>?> = repository.courseList
    var progressBarLoading: MutableLiveData<Boolean> = repository.progressBarLoading
    var swipeStatus: MutableLiveData<Boolean> = repository.swipeStatus

    fun fetchCourseData(courseDao: CourseDao , isSwipe:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (courseDao.getAllCourses().isNotEmpty() && !isSwipe) {
                courseList.postValue(courseDao.getAllCourses())
            } else {
                if (isSwipe) {
                    courseDao.deleteAll()
                }
                repository.fetchCourseData(courseDao , isSwipe )
            }
        }
    }
}