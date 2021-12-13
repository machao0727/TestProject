package com.machao.test_project.net

import com.google.gson.Gson
import java.util.HashMap

/**
 * create by：mc on 2021/12/13 15:53
 * email:
 *
 */
class ParamBuilder {
    private val gson = Gson()
    private var `object`: Any? = null
    private var objects: List<Any>? = null
    private var map = HashMap<String, Any>()

    /**
     * default constructor
     */
    fun ParamBuilder() {}

    //单实体类
    fun `object`(`object`: Any): ParamBuilder {
        this.`object` = `object`
        return this
    }

    //集合类
    fun objects(objects: List<Any>?): ParamBuilder {
        this.objects = objects
        return this
    }

    //map类
    fun typeValue(key: String, value: Any): ParamBuilder {
        map[key] = value
        return this
    }

    //type value类型
    fun typeBuild(): HashMap<String, Any> {
        return map
    }
}