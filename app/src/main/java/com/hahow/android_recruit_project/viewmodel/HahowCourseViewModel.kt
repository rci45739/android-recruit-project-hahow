package com.hahow.android_recruit_project.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import com.hahow.android_recruit_project.datamodel.CourseData
import com.hahow.android_recruit_project.datamodel.CourseList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HahowCourseViewModel(application: Application) : AndroidViewModel(application) {
    val clickListener = ObservableField<View.OnClickListener>()
    var courseItemDataList =
        MutableLiveData<MutableList<CourseList>>(mutableListOf())
    val mApplication = application
    private val _courseList = MutableLiveData<List<CourseData>>()
    val courseList: MutableLiveData<List<CourseData>> get() = _courseList
    private val gson = Gson()
    private suspend fun loadCourseDataFromJson(): List<CourseData> = withContext(Dispatchers.IO) {
        try {
            val jsonString = loadCourseData()
            val courseType = object : TypeToken<CourseList>() {}.type
            gson.fromJson<CourseList>(jsonString, courseType).data
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun loadCourseDataFromJsontest(): List<CourseData> {
        val jsonString = loadCourseData()
        val courseType = object : TypeToken<CourseList>() {}.type
        return gson.fromJson<CourseList>(jsonString, courseType).data
    }

    fun fetchTestOne(){
        _courseList.value = loadCourseDataFromJsontest()
    }


    private fun loadCourseData():String {
        return mApplication.assets.open("data.json").bufferedReader().use { it.readText() }
    }

//    fun refreshLiveBadge(context: Context, pStartKey: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            LiveAPIHandler.instance.requestGetLiveList(
//                context,
//                pStartKey,
//                object : ApiCallback<LiveListResponse> {
//                    override fun onSuccess(response: LiveListResponse, isCache: Boolean) {
//                        viewModelScope.launch(Dispatchers.Main) {
//                            isShowingBadge.value =
//                                !(response.data?.lives == null || response.data.lives.size == 0)
//                        }
//                    }
//
//                    override fun onError(errorCode: Int?, message: String?) {
//                        viewModelScope.launch(Dispatchers.Main) {
//                            isShowingBadge.value = false
//                        }
//                    }
//                })
//        }
//    }

//    private suspend fun getRankList(
//        context: Context,
//        roomId: String,
//        startKey: String
//    ): HttpResult<Response<BaseResponse>> {
//        val token = PreferenceUtil(context).getToken().toString()
//        val params = HashMap<String, String>()
//        params["roomId"] = roomId
//        if (!startKey.isNullOrEmpty()) {
//            params["startKey"] = startKey
//        }
//        return try {
//            val result = ApiInterface()
//                .createApiCall(ApiService::class.java)
//                .getLiveRankList(token, params)
//                .await()
//            return handleResponse(context, result as Response<BaseResponse>)
//        } catch (e: Throwable) {
//            HttpResult.Error(e)
//        }
//    }
}