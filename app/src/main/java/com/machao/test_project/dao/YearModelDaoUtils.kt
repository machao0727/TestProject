package com.machao.test_project.dao

import com.machao.test_project.mvp.model.QuarterModel
import com.raizlabs.android.dbflow.sql.language.SQLite

/**
 * create by：mc on 2021/12/11 20:16
 * email:
 *
 */
class YearModelDaoUtils {
    companion object{

        //保存数据
        fun saveYearModel(data:List<QuarterModel>){
            data.forEach { item: QuarterModel ->
                item.save()
            }
        }

        //获取数据
        fun  getYearModel(offset:Int,limit:Int):List<QuarterModel>{
            return SQLite.select().from(QuarterModel::class.java).limit(limit).offset(offset).queryList()
        }
    }
}