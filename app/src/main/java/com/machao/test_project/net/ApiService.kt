package com.machao.test_project.net

import retrofit2.http.POST
import com.dongyuwuye.compontent_base.BaseResp
import com.machao.test_project.mvp.model.NetData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by mc on 2018/3/28.
 * 网络接口
 */
interface ApiService {
    //=======================请求体======================//
    //    @POST("Account/ajax_Login")
    //    Observable<BaseResp<LoginResp>> Login(@Body RequestBody requestBody);
    //=======================表单======================//
    //    @GET("Sms/ajax_GetValidate")
    //    Observable<BaseResp<codeResp>> GetValidate(@QueryMap Map<String, String> map);
    //获取数据
    @GET("action/datastore_search")
    fun getData(@QueryMap map: Map<String, @JvmSuppressWildcards Any>):Observable<BaseResp<NetData>>

}