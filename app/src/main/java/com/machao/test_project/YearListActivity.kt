package com.machao.test_project

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import com.dongyuwuye.compontent_base.ListActivity
import com.dongyuwuye.compontent_base.annotation.ActivityFeature
import com.machao.test_project.binder.YearListBinder
import com.machao.test_project.mvp.contact.YearListContact
import com.machao.test_project.mvp.model.QuarterModel
import com.machao.test_project.mvp.model.YearListModel
import com.machao.test_project.mvp.presenter.YearListPresenter
import me.drakeet.multitype.MultiTypeAdapter

/**
 * create by：mc on 2021/12/11 19:28
 * email:
 *
 */
@SuppressLint("NonConstantResourceId")
@ActivityFeature(layout = R.layout.activity_list_year, titleTxt = R.string.year_list,rightTitleTxt = "")
class YearListActivity : ListActivity(),YearListContact.YearListView {
    override fun setRightViewClickListener(): View.OnClickListener? {
        return null
    }

    override fun initPresenter() {
        presenter=YearListPresenter(this)
    }

    override fun BackPressed() {
        finish()
    }

    override fun getResourceId(): String {
        return "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
    }

    override fun showError() {
        mStateLayout.showErrorView()
    }

    override fun showEmpty() {
        mStateLayout.showEmptyView()
    }

    override fun showContent() {
        mStateLayout.showContentView()
    }

    override fun showLoading() {
        mStateLayout.showProgressView()
    }

    override fun showText(text: String?) {
    }

    override fun hasLoadMore(): Boolean {
        return true
    }

    override fun hasPullRefresh(): Boolean {
        return true
    }

    override fun getRegisteredAdapter(): MultiTypeAdapter {
        var mAdapter=MultiTypeAdapter(dataItems)
        mAdapter.register(YearListModel::class.java,YearListBinder(this))
        return mAdapter
    }

    override fun getSpanCount(): Int {
        return 1
    }
}