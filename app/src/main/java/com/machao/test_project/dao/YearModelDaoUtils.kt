package com.machao.test_project.dao

import com.machao.test_project.mvp.model.YearModel
import com.raizlabs.android.dbflow.sql.language.SQLite

/**
 * create by：mc on 2021/12/11 20:16
 * email:
 *
 */
class YearModelDaoUtils {
    companion object{

        //保存数据
        fun saveYearModel(data:List<YearModel>){
            data.forEach { item: YearModel ->
                item.save()
            }
        }

        //获取数据
        fun  getYearModel(limit:Int):List<YearModel>{
            return SQLite.select().from(YearModel::class.java).limit(limit).queryList()
        }
    }
}