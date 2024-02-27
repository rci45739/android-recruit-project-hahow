package com.hahow.android_recruit_project.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.hahow.android_recruit_project.datamodel.SuccessCriteria

/**
 * Room 資料庫中的類型轉換器（Type Converter）
 */
object Converters {
    @TypeConverter
    @JvmStatic
    fun successCriteriaToString(successCriteria: SuccessCriteria): String {
        return Gson().toJson(successCriteria)
    }

    @TypeConverter
    @JvmStatic
    fun stringToSuccessCriteria(value: String): SuccessCriteria {
        return Gson().fromJson(value, SuccessCriteria::class.java)
    }
}