package com.hahow.android_recruit_project.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class HahowMainViewModel(application: Application) : AndroidViewModel(application){
    val clickListener = ObservableField<View.OnClickListener>()
}