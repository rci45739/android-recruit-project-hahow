package com.hahow.android_recruit_project.repository

import android.app.Application
import com.hahow.android_recruit_project.apiservice.ApiResult
import javax.inject.Inject

class HahowCourseRepository@Inject
constructor(
    private val application: Application) {
    fun loadCourseData(): String {
        return application.assets.open("data.json").bufferedReader().use { it.readText() }
    }
}