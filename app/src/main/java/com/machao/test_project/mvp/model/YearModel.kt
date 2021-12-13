package com.machao.test_project.mvp.model

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel

/**
 * create by：mc on 2021/12/11 19:43
 * email:
 *
 */
@Table(database = AppDataBase::class, allFields = true)
class YearModel :BaseModel(){
    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    var number: String? = null


}