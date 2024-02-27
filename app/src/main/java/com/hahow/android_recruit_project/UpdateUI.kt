package com.hahow.android_recruit_project

import com.hahow.android_recruit_project.datamodel.CourseData

interface UpdateUI {
    fun updateUI(courseList: List<CourseData>?, p:Boolean, s:Boolean)
}