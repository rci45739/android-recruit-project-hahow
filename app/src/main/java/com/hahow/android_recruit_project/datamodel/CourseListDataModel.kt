package com.hahow.android_recruit_project.datamodel


data class CourseList(val data: List<CourseData>)
data class CourseData(
    val successCriteria: SuccessCriteria,
    val numSoldTickets: Int,
    val status: String,
    val proposalDueTime: String?,
    val coverImageUrl: String,
    val title: String
)

data class SuccessCriteria(
    val numSoldTickets: Int
)