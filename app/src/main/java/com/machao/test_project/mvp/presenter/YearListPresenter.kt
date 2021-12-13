package com.machao.test_project.mvp.presenter

import com.machao.test_project.mvp.contact.YearListContact
import java.time.Year
import java.util.ArrayList

/**
 * create by：mc on 2021/12/11 19:48
 * email:
 *
 */
class YearListPresenter:YearListContact.YearListPresenter {
    private var mRootView: YearListContact.YearListView? = null

    private var dataItems: List<Any> = ArrayList()
    private var type = 0
    private var page = 1
    private var moreDate = true

    constructor(mRootView:YearListContact.YearListView){
        this.mRootView=mRootView
    }


    override fun refresh() {

    }

    override fun loadMore() {

    }
}