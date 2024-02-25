package com.hahow.android_recruit_project

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 *
 * @author David
 * @create 2024/2/24
 */
abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(protected var viewModel: VM) :
    AppCompatActivity() {
    protected lateinit var dataBinding: B

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)

        val extras = intent?.extras
        if (extras != null) {
            getBundleExtras(extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            getBundleExtras(extras)
        }

        setStatusBarColor()
        setStatusBarTextMode()
        setFullScreen()
        initBindingViewModel()
        bindLayoutWithViewModel()
        initView(savedInstanceState)
        initData()
        setObserver()
    }

    /**
     * Set StatusBar Color
     */
    private fun setStatusBarColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, statusBarColor())
    }

    /**
     * Set StatusBar Text Color Mode
     */
    private fun setStatusBarTextMode() {
        val isDarkMode = statusBarTextDarkMode()
        val windowController = ViewCompat.getWindowInsetsController(window.decorView)

        if (isDarkMode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowController?.isAppearanceLightStatusBars = false
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowController?.isAppearanceLightStatusBars = true
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    /**
     * Set Full Screen
     */
    private fun setFullScreen() {
        if (isFullScreenMode()) {
            val window = window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
            } else {
                val view = window.decorView
                view.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * Initialize ViewModel and ViewDataBinding
     */
    private fun initBindingViewModel() {
        // Obtain ViewModel from ViewModelProviders
        viewModel = ViewModelProvider(this ).get(viewModel::class.java)

        // Obtain binding
        dataBinding = DataBindingUtil.setContentView(this, layoutId())

        // LiveData needs the lifecycle owner
        dataBinding.lifecycleOwner = this
    }

    /**
     * Get Layout ID
     *
     * @return layout xml id
     */
    protected abstract fun layoutId(): Int

    /**
     * Get intent bundle extras
     */
    protected abstract fun getBundleExtras(extras: Bundle?)

    /**
     * Bind Layouts With ViewModel
     */
    protected abstract fun bindLayoutWithViewModel()

    /**
     * Initial View
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * Initial Data
     */
    protected abstract fun initData()

    /**
     * Set status bar color
     */
    protected abstract fun statusBarColor(): Int

    /**
     * Set status bar text is dark/light mode
     */
    protected abstract fun statusBarTextDarkMode(): Boolean

    /**
     * Set Activity as Full Screen
     *
     * @return true: Full screen
     */
    protected abstract fun isFullScreenMode(): Boolean

    /**
     * Set Observer/Listeners
     */
    protected abstract fun setObserver()

    /**
     * 按返回鍵兩次
     */
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
//        toast("Please click BACK again to exit")


        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}