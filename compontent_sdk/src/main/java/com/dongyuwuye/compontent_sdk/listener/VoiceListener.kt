package com.dongyuwuye.compontent_sdk.listener

import android.os.Bundle
import com.dongyuwuye.compontent_sdk.utils.JsonParser
import com.dongyuwuye.compontent_sdk.utils.LogUtils
import com.dongyuwuye.compontent_sdk.utils.ToastUtils

import com.iflytek.cloud.RecognizerListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechError
import org.json.JSONException
import org.json.JSONObject

abstract class VoiceListener : RecognizerListener {

    override fun onBeginOfSpeech() {
        LogUtils.d("VoiceListener", "开始说话")
    }

    override fun onEndOfSpeech() {
        LogUtils.d("VoiceListener", "结束说话")

    }

    override fun onVolumeChanged(p0: Int, p1: ByteArray) {
        // 音量大小
    }

    override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {
        // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        // 若使用本地能力，会话id为null
        //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
        //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
        //		Log.d(TAG, "session id =" + sid);
        //	}
    }

    override fun onError(p0: SpeechError) {
        // 10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
        if (p0.errorCode == 10118) {
            ToastUtils.toast("您没有说话或录音机权限被禁")
        }
    }

    override fun onResult(p0: RecognizerResult, p1: Boolean) {
        val text = JsonParser.parseIatResult(p0.resultString)
        var sn: String? = null
        // 读取json结果中的sn字段
        try {
            val resultJson = JSONObject(p0.resultString)
            sn = resultJson.optString("sn")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        getVoiceResult(sn, text)
    }

    abstract fun getVoiceResult(sn: String?, text: String?)

    abstract fun setFileName(filePath: String)
}