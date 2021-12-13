package com.machao.test_project.mvp.model

/**
 * create by：mc on 2021/12/13 15:45
 * email:
 *
 */
data class NetData(
    var records: List<QuarterModel>,
    var resource_id: String?,
    var limit:Int?,
    var total:Int?
)