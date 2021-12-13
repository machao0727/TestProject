package com.dongyuwuye.compontent_sdk.utils

import android.content.Context
import com.dongyuwuye.compontent_sdk.listener.VoiceListener


import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechRecognizer

class VoiceDictationUtils {

    private var mIat: SpeechRecognizer? = null
    private var context: Context? = null
    private var voiceListener: VoiceListener? = null
    private var voicePath: String? = null

    constructor(context: Context, voiceListener: VoiceListener, voicePath: String) {
        this.context = context
        this.voiceListener = voiceListener
        this.voicePath = voicePath
        init()
    }

    private fun init() {
        //使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(context) { code: Int ->
            LogUtils.d(
                    "voice_code",
                    "VoiceDictationUtils:  = $code"
            )
        }
        // 清空参数
        mIat!!.setParameter(SpeechConstant.PARAMS, null)
        // 设置听写引擎,引擎类型
        mIat!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        // 设置返回结果格式
        mIat!!.setParameter(SpeechConstant.RESULT_TYPE, "json")
        mIat!!.setParameter(SpeechConstant.VAD_ENABLE, "false")
        //设置语言
        //zh_cn：中文
        //en_us：英文
        //ja_jp：日语
        //ko_kr：韩语
        //ru-ru：俄语
        //fr_fr：法语
        //es_es：西班牙语
        mIat!!.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
        // 方言 默认值：mandarin
        mIat!!.setParameter(SpeechConstant.ACCENT, "mandarin")
        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");
        //设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat!!.setParameter(SpeechConstant.VAD_BOS, "400000")
        //设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat!!.setParameter(SpeechConstant.VAD_EOS, "200000")
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat!!.setParameter(SpeechConstant.ASR_PTT, "1")
        //设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mIat!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
//        val filePath: String = String.format("%s%s.%s", voicePath, System.currentTimeMillis(), "wav")
        voiceListener!!.setFileName(voicePath!!)
        mIat!!.setParameter(SpeechConstant.ASR_AUDIO_PATH, voicePath!!)
    }

    fun startRecode() {
        mIat!!.startListening(voiceListener)
    }

    fun stopRecoder() {
        mIat!!.stopListening()
    }

    fun cancel() {
        mIat!!.cancel()
    }
}