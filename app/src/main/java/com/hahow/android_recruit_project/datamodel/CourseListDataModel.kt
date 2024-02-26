package com.hahow.android_recruit_project.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey


data class CourseList(val data: MutableList<CourseData>)
@Entity(tableName = "course")
data class CourseData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
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