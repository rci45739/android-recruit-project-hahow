package com.hahow.android_recruit_project.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.datamodel.CourseList
import com.hahow.android_recruit_project.room.CourseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HahowCourseRepository(private val application: Application, private val coroutineScope
:CoroutineScope) {
    val courseList: MutableLiveData<List<CourseData>> = MutableLiveData()
    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var cacheList:List<CourseData> = listOf()
    private val gson: Gson = Gson()

    fun fetchCourseData(courseDao: CourseDao) {
        coroutineScope.launch(Dispatchers.IO) {
            val jsonString = loadCourseData()
            val courseDataList = parseCourseData(jsonString)
            courseList.postValue(courseDataList)
            courseDao.insertAll(courseDataList)
            cacheList = courseDao.getAllCourses()
        }
    }

    private fun loadCourseData(): String {
        return application.assets.open("data.json").bufferedReader().use { it.readText() }
    }

    private suspend fun parseCourseData(jsonString: String ): List<CourseData> {
        return withContext(Dispatchers.Default) {
            try {
                isLoading.postValue(true)
                val courseType = object : TypeToken<CourseList>() {}.type
                gson.fromJson<CourseList>(jsonString, courseType).data
            } catch (e: Exception) {
                isLoading.postValue(false)
                emptyList()
            } finally {
                delay(2000)
                isLoading.postValue(false)
            }
        }
    }
}