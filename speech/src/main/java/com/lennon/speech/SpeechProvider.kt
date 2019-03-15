package com.lennon.speech

import android.content.Context
import android.content.Intent
import android.os.Build
import cn.droidlover.xdroidmvp.log.XLog
import com.iflytek.cloud.SpeechUtility

abstract class SpeechProvider {
    abstract fun getSpeaker(): Speaker?
    fun initSpeech() {
        SpeechUtility.createUtility(getContext(), "appid=" + getSpeechId())
        val intent = Intent(getContext(), SpeechService::class.java)
        if (Build.VERSION.SDK_INT >= 26) {
            getContext().startForegroundService(intent)
        } else {
            getContext().startService(intent)
        }
    }

    abstract fun getContext(): Context
    abstract fun getSpeechId(): String
    abstract fun getDataFile(): String
    fun handleSpeech(text: String): Boolean {
        XLog.e("handleSpeech  $text")
        return false
    }

    abstract fun getSpeechNotifTitle(): String
    abstract fun getLogo(): Int
    abstract fun getSpeechNotifContent(): String
    abstract fun getSpeechAction(): String
}