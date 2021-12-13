package com.machao.test_project.net

import com.dongyuwuye.component_net.RetrofitManager
import com.dongyuwuye.component_net.RxCallBack
import com.dongyuwuye.component_net.RxHelper
import com.machao.test_project.mvp.model.NetData

/**
 * <网络请求管理类>
 * email：
</网络请求管理类> */
class HttpManager private constructor() {

    private var apiService: ApiService? = null

    fun init(c: Class<*>?) {
        apiService = RetrofitManager.getInstance().retrofit.create<Any>(c as Class<Any>) as ApiService
    }

    /**
     * 获取网络数据
     */
    fun getData(callBack: RxCallBack<NetData>, resource_id: String, limit: String, offset:Int) {
        var body=ParamBuilder()
            .typeValue("resource_id",resource_id)
            .typeValue("limit",limit)
            .typeValue("offset",offset)
            .typeBuild()

        apiService!!.getData(body).compose(RxHelper.handleResult()).subscribe(
            callBack
        )
    }

    companion object {
        var instance: HttpManager? = null
            get() {
                synchronized(HttpManager::class.java) {
                    if (field == null) {
                        field = HttpManager()
                    }
                }
                return field
            }
            private set
    }
}