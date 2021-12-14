package com.machao.test_project.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dongyuwuye.compontent_base.BaseActivity
import com.google.android.flexbox.FlexboxLayout
import com.machao.test_project.R
import com.machao.test_project.mvp.model.QuarterModel
import com.machao.test_project.mvp.model.YearListModel
import kotlinx.android.synthetic.main.item_year_layout.view.*
import me.drakeet.multitype.ItemViewBinder
import org.w3c.dom.Text

/**
 * create by：mc on 2021/12/11 20:11
 * email:
 * 年数据item
 */
class YearListBinder(val mContext: BaseActivity) : ItemViewBinder<YearListModel, YearListBinder.ViewHolder>() {


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_year_layout, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, item: YearListModel) {
        holder.mTvYear.text="年份：${item.year}"
        holder.mFlexBox.removeAllViews()
        for (quarterModel in item.quarterList) {
            var view:View = LayoutInflater.from(mContext).inflate(R.layout.item_quarter_layout,holder.mFlexBox,false)
            var mTvQuarter:TextView=view.findViewById(R.id.mTvQuarter)
            var mTvData:TextView=view.findViewById(R.id.mTvData)
            mTvQuarter.text=quarterModel.quarter;
            mTvData.text=quarterModel.volume_of_mobile_data
            holder.mFlexBox.addView(view)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvYear: TextView = itemView.mTvYear
        val mFlexBox: FlexboxLayout = itemView.mFlexBox

    }


}