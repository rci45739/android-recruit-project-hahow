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
import dagger.hilt.android.AndroidEntryPoint

/**
 *
 * @author David
 * @create 2024/2/24
 */
abstract class BaseFragment<B : ViewDataBinding, VM : ViewModel>(
    protected val viewModelClass: Class<VM>
) : Fragment() {

    protected lateinit var dataBinding: B
    protected var bundle: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    protected inline fun <reified VM : ViewModel> createViewModel(): VM {
        return ViewModelProvider(this).get(VM::class.java)
    }

    protected abstract fun layoutId(): Int

    protected abstract fun getBundleExtras(bundle: Bundle?)

    protected abstract fun bindLayoutWithViewModel()

    protected abstract fun setAppbar()

    protected abstract fun initData()

    protected abstract fun initView(savedInstanceState: Bundle?)

    protected abstract fun setObserver()
}
