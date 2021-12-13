package com.machao.test_project.mvp.model

import com.raizlabs.android.dbflow.annotation.Database

/**
 * create by：mc on 2021/12/11 19:39
 * email:
 *
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
class AppDataBase {
    companion object{
        const val NAME = "year_project"
        const val VERSION = 1
    }
}