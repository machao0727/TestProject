package com.machao.test_project.net

import com.dongyuwuye.component_net.RetrofitManager
import com.dongyuwuye.component_net.RxCallBack
import com.dongyuwuye.component_net.RxHelper
import com.machao.test_project.mvp.model.YearModel
import java.util.*

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
     * 登录
     */
    fun login(callBack: RxCallBack<List<YearModel>>?, account: String?, pwd: String?) {
//        HashMap<String, String> body = new ParamBuilder()
//                .typeValue("UserName", account)
//                .typeValue("Password", DESUtil.encrypt(pwd, Config.DEFAULT_KEY))
//                .typeBuild();
        apiService!!.getData(HashMap())?.compose(RxHelper.handleResult())?.subscribe(
            callBack as RxCallBack<List<YearModel?>?>
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