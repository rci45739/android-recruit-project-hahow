package com.hahow.android_recruit_project.ui

import android.app.Application
import android.os.Bundle
import com.hahow.android_recruit_project.BaseActivity
import com.hahow.android_recruit_project.viewmodel.HahowMainViewModel
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.ActivityHahowMainBinding

class HahowMainActivity: BaseActivity<ActivityHahowMainBinding, HahowMainViewModel>(
    HahowMainViewModel
        (
        application
        = Application()
    )
) {
    override fun layoutId(): Int {
        return R.layout.activity_hahow_main
    }

    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun bindLayoutWithViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun statusBarColor(): Int {
        return R.color.main_style
    }

    override fun statusBarTextDarkMode(): Boolean {
        return true
    }

    override fun isFullScreenMode(): Boolean {
        return true
    }

    override fun setObserver() {
    }
}