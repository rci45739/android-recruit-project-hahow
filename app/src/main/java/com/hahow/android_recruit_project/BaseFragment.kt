package com.hahow.android_recruit_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *
 * @author David
 * @create 2024/2/24
 */
abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>(protected var viewModel: VM) :
    Fragment() {

    protected lateinit var dataBinding: B
    protected var bundle: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(viewModel::class.java)
        dataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bundle = this.arguments
        if (bundle != null) {
            getBundleExtras(bundle)
        }

        bindLayoutWithViewModel()
        setAppbar()
        initData()
        initView(savedInstanceState)
        setObserver()
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
    protected abstract fun getBundleExtras(bundle: Bundle?)

    /**
     * Bind Layouts With ViewModel
     */
    protected abstract fun bindLayoutWithViewModel()

    /**
     * Set Appbar
     */
    protected abstract fun setAppbar()

    /**
     * Set init data
     */
    protected abstract fun initData()

    /**
     * Initial View
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * Add Observer or Listener
     */
    protected abstract fun setObserver()

}