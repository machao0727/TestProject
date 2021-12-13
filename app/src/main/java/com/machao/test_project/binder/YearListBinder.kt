package com.machao.test_project.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dongyuwuye.compontent_base.BaseActivity
import com.machao.test_project.R
import com.machao.test_project.mvp.model.YearModel
import kotlinx.android.synthetic.main.item_year_layout.view.*
import me.drakeet.multitype.ItemViewBinder

/**
 * create by：mc on 2021/12/11 20:11
 * email:
 * 年数据item
 */
class YearListBinder(val mContext: BaseActivity) : ItemViewBinder<YearModel, YearListBinder.ViewHolder>() {


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_year_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, item: YearModel) {


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvTitle: TextView = itemView.mTvTitle

    }


}