package com.hahow.android_recruit_project.customerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import `in`.hahow.android_recruit_project.R

/**
 * SwipeRefreshLayout
 *
 * @author David
 * @create 2024/2/27
 */
class SwipeRefreshLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : androidx.swiperefreshlayout.widget.SwipeRefreshLayout(context, attrs){
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setColorSchemeColors(Color.BLUE)
        setProgressBackgroundColorSchemeColor(resources.getColor(R.color.teal_700, context.theme))
    }
}