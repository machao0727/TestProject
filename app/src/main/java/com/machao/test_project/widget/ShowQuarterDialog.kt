package com.machao.test_project.widget

import android.app.Dialog
import android.content.Context
import butterknife.ButterKnife
import com.dongyuwuye.compontent_sdk.utils.DensityUtils
import com.dongyuwuye.compontent_sdk.utils.ScreenUtils
import com.machao.test_project.R
import com.machao.test_project.mvp.model.QuarterModel
import kotlinx.android.synthetic.main.dialog_quarter_view.*

/**
 * create by：mc on 2021/12/14 21:30
 * email:
 *
 */
class ShowQuarterDialog(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    init {
        initView(context)
    }



    private fun initView(context: Context) {
        window?.setContentView(R.layout.dialog_quarter_view)
        setCanceledOnTouchOutside(true)
//        val lp = window!!.attributes
//        lp.width = (ScreenUtils.getScreenW() - DensityUtils.dpTopx(100f, context)) //设置宽度
//        window!!.attributes = lp
        window!!.setDimAmount(0.1f)
    }

     fun setData(data:List<QuarterModel>):ShowQuarterDialog{
        mQuarterView.setData(data)
        return this
    }
}