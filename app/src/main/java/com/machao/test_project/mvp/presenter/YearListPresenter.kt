package com.machao.test_project.mvp.presenter

import com.dongyuwuye.component_net.RxCallBack
import com.machao.test_project.dao.YearModelDaoUtils
import com.machao.test_project.mvp.contact.YearListContact
import com.machao.test_project.mvp.model.NetData
import com.machao.test_project.mvp.model.QuarterModel
import com.machao.test_project.mvp.model.YearListModel
import com.machao.test_project.net.HttpManager
import java.time.Year
import java.util.ArrayList

/**
 * create by：mc on 2021/12/11 19:48
 * email:
 *
 */
class YearListPresenter : YearListContact.YearListPresenter {
    private var mRootView: YearListContact.YearListView? = null
    var year: LinkedHashMap<String, MutableList<QuarterModel>> = linkedMapOf()
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
                year.clear()
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
                }
                return
            }
            showContent(temp)
        }

    }

    fun showContent(temp: List<QuarterModel>){
        if (offset==0){//刷新
            year.clear()
        }
        temp.forEach {
            var key = it.quarter!!.substring(0, it.quarter!!.indexOf("-"))
            var quarterList: MutableList<QuarterModel>? = null
            quarterList = if (year[key] == null) {
                mutableListOf<QuarterModel>()
            } else {
                year[key]
            }
            quarterList!!.add(it)
            year[key] = quarterList
        }
        moreDate = temp.size == offset
        mRootView!!.complete(ArrayList(year.values) as List<Any>?,moreDate)
    }

    override fun refresh() {
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
        offset += 5
        HttpManager.instance!!.getData(
            callBack,
            mRootView!!.getResourceId(),
            limit = "$limit",
            offset = offset
        )
    }
}