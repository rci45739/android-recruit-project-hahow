package com.hahow.android_recruit_project.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.datamodel.CourseList
import com.hahow.android_recruit_project.viewmodel.LoadingListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HahowCourseRepository(private val application: Application, private val coroutineScope
:CoroutineScope) {
    val courseList: MutableLiveData<List<CourseData>> = MutableLiveData()
    private val gson: Gson = Gson()

    fun fetchCourseData(listner: LoadingListener) {
        coroutineScope.launch(Dispatchers.IO) {
            val jsonString = loadCourseData()
            val courseDataList = parseCourseData(jsonString , listner)
            courseList.postValue(courseDataList)
        }
    }

    private fun loadCourseData(): String {
        return application.assets.open("data.json").bufferedReader().use { it.readText() }
    }

    private suspend fun parseCourseData(jsonString: String , listner: LoadingListener): List<CourseData> {
        return withContext(Dispatchers.Default) {
            try {
                listner.loading(true)
                val courseType = object : TypeToken<CourseList>() {}.type
                gson.fromJson<CourseList>(jsonString, courseType).data
            } catch (e: Exception) {
                listner.loading(false)
                emptyList()
            } finally {
                delay(2000)
                listner.loading(false)
            }
        }
    }
}