package com.hahow.android_recruit_project.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hahow.android_recruit_project.BaseFragment
import com.hahow.android_recruit_project.adapter.CourseListAdapter
import com.hahow.android_recruit_project.viewmodel.HahowCourseViewModel
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.FragmentHahowCourseBinding

class HahowCourseFragment: BaseFragment<FragmentHahowCourseBinding, HahowCourseViewModel>(
    HahowCourseViewModel(application = Application())) {
    private lateinit var courseListAdapter: CourseListAdapter
    override fun layoutId(): Int {
        return R.layout.fragment_hahow_course
    }

    override fun getBundleExtras(bundle: Bundle?) {
    }

    override fun bindLayoutWithViewModel() {
       dataBinding.viewModel = viewModel
    }

    override fun setAppbar() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        setCourseListInit()
    }
    override fun initData() {
        viewModel.fetchCourseData()
        setCourseListData()
    }

    override fun setObserver() {
    }

    private fun setCourseListInit() {
        dataBinding.courseListRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter =
                CourseListAdapter(
                    context,
                    this@HahowCourseFragment,
                    this@HahowCourseFragment.viewModel
                ).also { it1 ->
                    courseListAdapter = it1
                }
               /*     .apply {
                    onLoadmore = {
                        loadNextPage()
                    }
                }*/

        }
    }

    private fun setCourseListData() {
        viewModel.run {
            viewModel.courseList.observe(this@HahowCourseFragment ){
                courseListAdapter.setData(it.toMutableList())
                courseListAdapter.submitList(it)
            }
        }
    }

    companion object {
        fun newInstance() = HahowCourseFragment()
    }

}