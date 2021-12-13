package com.dongyuwuye.compontent_base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.dongyuwuye.compontent_sdk.utils.ScreenUtils

import kotlinx.android.synthetic.main.dialog_base.*

abstract class BaseDialog(val mContext: Context) : Dialog(mContext, R.style.cus_dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_base)

        mIvCancelFl.setOnClickListener {
            dismiss()
        }
        val view = View.inflate(mContext, getLayoutId(), null)
        containerFl.addView(view)

        mBtnLeft.setOnClickListener { setCancelListener() }

        mBtnRight.setOnClickListener { setSureListener() }

        initView()

        setWindowParams(ScreenUtils.dp2px(95f))
    }

   open fun setWindowParams(value: Int) {
        val attributes = window?.attributes!!
        attributes.width = ScreenUtils.getScreenW() - value
        attributes.height = ScreenUtils.getScreenH()
        window?.attributes = attributes
    }

    fun setDialogLocation(value: Float) {
        val layoutParams = rootDialogView.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = ScreenUtils.dp2px(value)
        rootDialogView.layoutParams = layoutParams
    }

    abstract fun initView()

    fun setTitleTvText(str: String?) {
        str?.apply {
            mTvTitle.text = str
        }
    }

    fun setTitleTvText(ids: Int?) {
        ids?.apply {
            mTvTitle.text = mContext.getString(ids)
        }
    }

    abstract fun getLayoutId(): Int


    //是否显示底部按钮
    fun isShowButtonLl(isShowButtonLL: Boolean) {
        mDoubleLayout.visibility = if (isShowButtonLL) View.VISIBLE else View.GONE
    }

    fun setCancelBtnText(str: String?) {
        str?.apply {
            mBtnLeft.text = str
        }
    }

    fun setSureBtnText(str: String?) {
        str?.apply {
            mBtnRight.text = str
        }
    }

    open fun setCancelListener() {}

    open fun setSureListener() {}

}