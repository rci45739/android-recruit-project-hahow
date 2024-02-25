package com.hahow.android_recruit_project.utils

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

/**
 * Fragment工具
 *
 * @author David
 * @create 2024/2/24
 */
class FragmentUtil {

    companion object {

        /* Detect the fragment is exist or not */
        fun isFragmentExist(activity: FragmentActivity, viewID: Int): Boolean {

            val fragmentManager: FragmentManager = activity.supportFragmentManager
            val fragment = fragmentManager.findFragmentById(viewID)

            return null != fragment
        }

        /* Detect the fragment is exist or not */
        fun isFragmentExist(activity: FragmentActivity, tag: String): Boolean {

            val fragmentManager = activity.supportFragmentManager
            val fragment = fragmentManager.findFragmentByTag(tag)

            return null != fragment
        }

        /* Add fragment */
        fun addFragment(
            activity: FragmentActivity, viewID: Int, fragment: Fragment, tag: String,
            bundle: Bundle?
        ) {

            val fragmentManager = activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (bundle != null) {
                fragment.arguments = bundle
            }

            fragmentTransaction.add(viewID, fragment, tag)
            fragmentTransaction.commit()
        }

        /* Add fragment */
        fun addFragment(
            activity: FragmentActivity, viewID: Int, fragment: Fragment, tag: String,
            bundle: Bundle?, addToBackStack: Boolean
        ) {

            val fragmentManager = activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (bundle != null) {
                fragment.arguments = bundle
            }

            fragmentTransaction.add(viewID, fragment, tag)

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null)
            }

            fragmentTransaction.commit()
        }

        /* Replace fragment */
        fun replaceFragment(
            activity: FragmentActivity, viewID: Int, fragment: Fragment,
            tag: String, bundle: Bundle?, addToBackStack: Boolean
        ) {

            val fragmentManager = activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (bundle != null) {
                fragment.arguments = bundle
            }

            fragmentTransaction.replace(viewID, fragment, tag)

            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null)
            }

            fragmentTransaction.commit()
        }

        /* Remove fragment */
        fun removeFragment(activity: FragmentActivity, fragment: Fragment, intent: Intent?) {

            val fragmentManager = activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (intent != null) {
                fragment.arguments = intent.extras
            }

            fragmentTransaction.remove(fragment)
            fragmentTransaction.commit()
        }

    }

}