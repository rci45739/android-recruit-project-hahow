package com.hahow.android_recruit_project.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class JsonTransferTypeUtils @Inject constructor() {
    val gson = Gson()

    inline fun <reified T> parse(jsonString: String): T {
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(jsonString, type)
    }
}
