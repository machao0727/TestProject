package com.machao.test_project


import androidx.multidex.MultiDexApplication
import com.dongyuwuye.component_net.RetrofitManager
import com.dongyuwuye.compontent_sdk.utils.ActivityUtils
import com.dongyuwuye.compontent_sdk.utils.ApplicationUtils
import com.dongyuwuye.compontent_sdk.utils.VersionManager
import com.machao.test_project.config.Config


/**
 * create by：mc on 2021/12/11 17:23
 * email:
 *
 */
class TestProjectApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        VersionManager.init(this)

        ActivityUtils.initMain(this, BuildConfig.APPLICATION_ID)

        ApplicationUtils.init(this)

        RetrofitManager.getInstance()
            .init(BuildConfig.BASE_URL, Config.DEFAULT_TIMEOUT)
    }
}