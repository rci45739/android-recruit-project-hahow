package com.hahow.android_recruit_project.listener

interface ApiStatusListener {
    fun onSuccess()
    fun onFailure()
    fun onComplete()
}