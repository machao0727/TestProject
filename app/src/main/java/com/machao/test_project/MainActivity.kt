package com.machao.test_project

import android.os.Bundle
import android.view.View
import com.dongyuwuye.compontent_base.BaseActivity
import com.dongyuwuye.compontent_base.annotation.ActivityFeature

@ActivityFeature(layout = R.layout.activity_main)
class MainActivity : BaseActivity() {

    override fun setRightViewClickListener(): View.OnClickListener? {
        return null
    }

    override fun initPresenter() {
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
    }

    override fun BackPressed() {
    }

    fun aboutMe(view: View){

    }

    fun start(view: View){
        start(YearListActivity::class.java)
    }
}