package com.hahow.android_recruit_project.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hahow.android_recruit_project.datamodel.CourseData

/**
 * Room Database
 */
@Database(entities = [CourseData::class], version = 1)
@TypeConverters(Converters::class)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao

    companion object {
        private var INSTANCE: CourseDatabase? = null

        fun getInstance(context: Context): CourseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CourseDatabase::class.java,
                    "course_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun release(){
            if(INSTANCE != null){
                INSTANCE = null
            }
        }
    }
}