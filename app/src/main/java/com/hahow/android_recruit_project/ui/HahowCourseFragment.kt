package com.hahow.android_recruit_project.ui

import android.app.Application
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hahow.android_recruit_project.BaseFragment
import com.hahow.android_recruit_project.adapter.CourseListAdapter
import com.hahow.android_recruit_project.room.CourseDao
import com.hahow.android_recruit_project.room.CourseDatabase
import com.hahow.android_recruit_project.viewmodel.HahowCourseViewModel
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.FragmentHahowCourseBinding

class HahowCourseFragment : BaseFragment<FragmentHahowCourseBinding, HahowCourseViewModel>(
    HahowCourseViewModel(application = Application())
) {
    private lateinit var courseListAdapter: CourseListAdapter
    private lateinit var courseAppDatabase: CourseDatabase
    private lateinit var courseDao: CourseDao

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
        setSwipeRefreshListener()
    }

    override fun initData() {
        courseAppDatabase = CourseDatabase.getInstance(requireContext())
        courseDao = courseAppDatabase.courseDao()
        viewModel.fetchCourseData(courseDao, false)
    }

    override fun setObserver() {
        viewModel.run {
            viewModel.courseList.observe(this@HahowCourseFragment) {
                courseListAdapter.setData(it.toMutableList())
                courseListAdapter.submitList(it)
                courseListAdapter.notifyDataSetChanged()
            }

            viewModel.swipeStatus.observe(this@HahowCourseFragment) {
                if(it == false){
                    dataBinding.layoutSwipeRefresh.isRefreshing = false
                }
            }
        }
    }

    private fun setSwipeRefreshListener() {
        dataBinding.layoutSwipeRefresh.setOnRefreshListener {
            viewModel.fetchCourseData(courseDao , true)
        }
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
        }
    }

    companion object {
        fun newInstance() = HahowCourseFragment()
    }

}