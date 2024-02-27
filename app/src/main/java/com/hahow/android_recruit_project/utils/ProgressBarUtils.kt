package com.hahow.android_recruit_project.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import `in`.hahow.android_recruit_project.R
/**
 * 課程售出進度條狀態單例
 */
object ProgressBarUtils {
    enum class StatusType(
        val value: String,
    )  {
        PUBLISHED("PUBLISHED"),
        INCUBATING("INCUBATING"),
        SUCCESS("SUCCESS");
        companion object {
            fun fromValue(value: String): StatusType? {
                return StatusType.values().find { it.value == value }
            }
        }
    }

    fun setProgressBar(progressBar: ProgressBar, statusType: StatusType,
                       alreadySoldOut: Boolean, numSoldTickets: Int, successCriteria: Int) {
        progressBar.max = 100
        val progress = if (alreadySoldOut) {
            100
        } else {
            (numSoldTickets.toFloat() / successCriteria.toFloat() * 100).toInt()
        }
        progressBar.progress = progress
        when (statusType) {
            StatusType.PUBLISHED -> setProgressBarColor(progressBar, ContextCompat.getColor(progressBar.context, R.color.teal_200))
            StatusType.INCUBATING -> setProgressBarColor(progressBar, ContextCompat.getColor(progressBar.context, R.color.color_orange))
            StatusType.SUCCESS -> setProgressBarColor(progressBar, ContextCompat.getColor(progressBar.context, R.color.color_green))
        }
    }

    private fun setProgressBarColor(progressBar: ProgressBar, color: Int) {
        progressBar.progressTintList = ColorStateList.valueOf(color)
    }
}
