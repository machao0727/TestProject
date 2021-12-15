package com.machao.test_project.mvp.presenter

import android.os.Handler
import com.dongyuwuye.component_net.RxCallBack
import com.dongyuwuye.compontent_sdk.utils.ApplicationUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.machao.test_project.dao.YearModelDaoUtils
import com.machao.test_project.mvp.contact.YearListContact
import com.machao.test_project.mvp.model.NetData
import com.machao.test_project.mvp.model.QuarterModel
import com.machao.test_project.mvp.model.YearListModel
import com.machao.test_project.net.HttpManager
import com.machao.test_project.utils.JsonUtils
import java.time.Year
import java.util.ArrayList

/**
 * create by：mc on 2021/12/11 19:48
 * email:
 *
 */
class YearListPresenter : YearListContact.YearListPresenter {
    private var mRootView: YearListContact.YearListView? = null
    var yearMap: LinkedHashMap<String, MutableList<QuarterModel>> = linkedMapOf()
    private var dataItems: MutableList<Any> = mutableListOf()
    private var limit = 5 //每次请求数量 5
    private var offset = 0//起始值0
    private var moreDate = true

    constructor(mRootView: YearListContact.YearListView) {
        this.mRootView = mRootView
    }

    private var callBack: RxCallBack<NetData> = object : RxCallBack<NetData>() {
        override fun _onNext(value: NetData?) {
            var temp: List<QuarterModel> = value!!.records
            if (offset==0){//刷新
                yearMap.clear()
            }
            showContent(temp)
            YearModelDaoUtils.saveYearModel(value.records)
        }

        override fun _onError(e: String?) {
            //请求异常，获取本地数据
            var temp: List<QuarterModel> = YearModelDaoUtils.getYearModel(offset, limit)
            if (temp.isEmpty()){
                if (offset==0){//刷新
                    mRootView!!.showError()
                }else{
                    mRootView!!.complete(dataItems,false)
                    mRootView!!.showContent()
                }
                return
            }
            showContent(temp)
        }

    }

    fun showContent(temp: List<QuarterModel>){
        dataItems.clear()
        if (offset==0){//刷新
            yearMap.clear()
        }
        temp.forEach {
            var key = it.quarter!!.substring(0, it.quarter!!.indexOf("-"))
            var quarterList: MutableList<QuarterModel>? = null
            quarterList = if (yearMap[key] == null) {
                mutableListOf<QuarterModel>()
            } else {
                yearMap[key]
            }
            quarterList!!.add(it)
            yearMap[key] = quarterList
        }
        yearMap.forEach{
           dataItems.add(YearListModel(it.key,it.value))
        }
        moreDate = temp.size == offset
        mRootView!!.complete(dataItems,true)
        mRootView!!.showContent()
    }

    override fun refresh() {
//        offset = 0
//        val value: NetData = Gson().fromJson(
//            JsonUtils.getJson("MainJson", ApplicationUtils.application),
//            object : TypeToken<NetData>() {}.type
//        )
//        var temp: List<QuarterModel> = value!!.records
//        showContent(temp)
//        YearModelDaoUtils.saveYearModel(value.records)


        limit = 5
        offset = 0
        HttpManager.instance!!.getData(
            callBack,
            mRootView!!.getResourceId(),
            limit = "$limit",
            offset = offset
        )
    }

    override fun loadMore() {
//        Handler().postDelayed(Runnable {
//            offset += 5
//            val value: NetData = Gson().fromJson(
//                JsonUtils.getJson("json2", ApplicationUtils.application),
//                object : TypeToken<NetData>() {}.type
//            )
//            var temp: List<QuarterModel> = value!!.records
//            YearModelDaoUtils.saveYearModel(value.records)
//            showContent(temp)
//        },2000)


        offset += 5
        HttpManager.instance!!.getData(
            callBack,
            mRootView!!.getResourceId(),
            limit = "$limit",
            offset = offset
        )
    }
}