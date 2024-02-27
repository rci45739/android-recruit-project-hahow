package com.hahow.android_recruit_project.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hahow.android_recruit_project.apiservice.ApiResult
import com.hahow.android_recruit_project.datamodel.CourseData
/**
 * 定義接口資料庫操作
 */
@Dao
interface CourseDao {
    @Query("SELECT * FROM course")
    fun getAllCourses(): List<CourseData>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(course: List<CourseData>)
    @Query("DELETE FROM course")
    suspend fun deleteAll()
}