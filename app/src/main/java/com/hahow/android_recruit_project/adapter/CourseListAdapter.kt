package com.hahow.android_recruit_project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.utils.CourseStatusUtils
import com.hahow.android_recruit_project.utils.ProgressBarUtils
import com.hahow.android_recruit_project.viewmodel.HahowCourseViewModel
import `in`.hahow.android_recruit_project.R
import `in`.hahow.android_recruit_project.databinding.ItemCourselListBinding

/**
 * Course List Adapter
 *
 * @author David
 * @create 2023/2/25
 */
class CourseListAdapter(
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner,
    private val hahowCourseViewModel: HahowCourseViewModel
) : ListAdapter<CourseData, CourseListAdapter.ViewHolder>(DiffCallback) {

    private var items = mutableListOf<CourseData>()

    // 上滑讀取更多CallBack
    var onLoadmore: (() -> Unit)? = null

    fun setData(items: MutableList<CourseData>?) {
        this.items = items as MutableList<CourseData>
    }

/*    fun removeData(position: Int) {
        val tempItemDataList = hahowCourseViewModel.itemDataList.value
        tempItemDataList?.removeAt(position)
        hahowCourseViewModel.itemDataList.postValue(tempItemDataList)
    }*/

    override fun submitList(list: List<CourseData>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    inner class ViewHolder(private val binding: ItemCourselListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: CourseData,
            viewLifecycleOwner: LifecycleOwner,
            hahowCourseViewModel: HahowCourseViewModel,
        ) {
            binding.run {
                lifecycleOwner = viewLifecycleOwner
                itemData = item
                this.viewModel = hahowCourseViewModel

                executePendingBindings()
            }
            Glide.with(itemView)
                .load(item.coverImageUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background))
                .into(binding.imgCourse)

            val statusType = CourseStatusUtils.StatusType.fromValue(item.status)
            if (statusType != null) {
                binding.tvStatus.text = statusType.getText(context)
                statusType.setBackgroundResource(binding.tvStatus)
            }

            var alreadySoldOut = item.numSoldTickets > (item.successCriteria.numSoldTickets ?: 0)

            setTextViewNumSoldStatus(binding, item, alreadySoldOut)
            val statusTypeProgressBar = ProgressBarUtils.StatusType.fromValue(item.status)
            if (statusTypeProgressBar != null) {
                ProgressBarUtils.setProgressBar(
                    binding.progressBarNumSoldStatus,
                    statusTypeProgressBar,
                    alreadySoldOut,
                    item.numSoldTickets,
                    item.successCriteria.numSoldTickets
                )
            }
            if(item.proposalDueTime.isNullOrEmpty()){
                binding.tvCountdownDate.visibility = View.GONE
            }else{
                binding.tvCountdownDate.visibility = View.VISIBLE
            }

        }
    }

    private fun setTextViewNumSoldStatus(bindingView: ItemCourselListBinding, itemData: CourseData, isSoldOut: Boolean) {
        val numSoldTickets = itemData.numSoldTickets
        val successCriteria = itemData.successCriteria.numSoldTickets ?: 0
        val text = when {
            isSoldOut && numSoldTickets != 0 -> "100%"
            numSoldTickets == 0 -> "0%"
            else -> "$numSoldTickets/$successCriteria"
        }
        bindingView.tvNumSoldStatus.text = text
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemCourselListBinding.inflate(layoutInflater, parent, false))
    }

    @SuppressLint("RecyclerView", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], viewLifecycleOwner, hahowCourseViewModel)
        holder.itemView.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {

                }
            }
        })
    }

    private object DiffCallback : DiffUtil.ItemCallback<CourseData>() {
        override fun areItemsTheSame(
            oldItem: CourseData,
            newItem: CourseData,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CourseData,
            newItem: CourseData,
        ): Boolean {
            return oldItem == newItem
        }
    }
}