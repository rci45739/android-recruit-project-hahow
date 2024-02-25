package com.hahow.android_recruit_project.utils

import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import `in`.hahow.android_recruit_project.R

object CourseStatusUtils {
    enum class StatusType(
        val value: String,
        @StringRes val textResId: Int,
        @DrawableRes val backgroundDrawableResId: Int
    ) {
        PUBLISHED("PUBLISHED", R.string.published_text, R.drawable.bg_status_publised),
        INCUBATING("INCUBATING", R.string.incubating_text, R.drawable.bg_status_incubating),
        SUCCESS("SUCCESS", R.string.success_text, R.drawable.bg_status_success);

        companion object {
            fun fromValue(value: String): StatusType? {
                return values().find { it.value == value }
            }
        }

        fun setBackgroundResource(view: View) {
            view.setBackgroundResource(backgroundDrawableResId)
        }

        fun getText(context: Context): String {
            return context.getString(textResId)
        }
    }
}
