package com.hahow.android_recruit_project.ui

import android.app.Application
import android.os.Bundle
import com.hahow.android_recruit_project.BaseActivity
import com.hahow.android_recruit_project.utils.FragmentUtil
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
    private lateinit var hahowCourseFragment: HahowCourseFragment
    override fun layoutId(): Int {
        return R.layout.activity_hahow_main
    }

    override fun getBundleExtras(extras: Bundle?) {
    }

    override fun bindLayoutWithViewModel() {
        dataBinding.viewModel = viewModel
    }

    override fun initView(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            hahowCourseFragment = HahowCourseFragment.newInstance()
            FragmentUtil.addFragment(this, R.id.container, hahowCourseFragment, "MePage", null)
        }
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
        return false
    }

    override fun setObserver() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}