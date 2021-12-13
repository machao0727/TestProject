package com.machao.test_project.net

import retrofit2.http.POST
import com.dongyuwuye.compontent_base.BaseResp
import com.machao.test_project.mvp.model.YearModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
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
    @POST("Admin/Login")
    fun getData(@QueryMap map: Map<String?, String?>?): Observable<BaseResp<List<YearModel?>?>?>?

    //宣传引导
    @Headers("Content-Type:application/json")
    @POST("Admin/CreatePropaganda")
    fun CreateProgate(@Body body: RequestBody?): Observable<BaseResp<*>?>?
}