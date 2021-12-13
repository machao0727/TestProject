package com.machao.test_project.mvp.model

/**
 * create by：mc on 2021/12/13 16:03
 * email:
 * 经过格式化的年数据
 */
data class YearListModel(
    var year:String,
    var quarterList: List<QuarterModel>
)