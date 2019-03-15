package com.lennon.speech

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import cn.droidlover.xdroidmvp.log.XLog
import java.util.ArrayList

object SpeechUtill{
    fun speech(context: Context, text: String) {
        if (!isServiceRunning(context, SpeechService.ACTION)) {
            val intent = Intent(context, SpeechService::class.java)
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent)
            } else {
                // Pre-O behavior.
                context.startService(intent)
            }
            while (isServiceRunning(context, SpeechService.ACTION) && SpeechService.alive);
        }
        XLog.e("send-----text------$text")
        val i = Intent()
        i.action = Speech.getSpeechAction()
        i.putExtra("text", text)
        context.sendBroadcast(i)
    }

    /**
     * 判断服务是否开启
     *
     * @return
     */
    fun isServiceRunning(context: Context?, ServiceName: String?): Boolean {
        if (context == null || "" == ServiceName || ServiceName == null)
            return false
        val myManager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningService = myManager
                .getRunningServices(30) as ArrayList<ActivityManager.RunningServiceInfo>
        for (i in runningService.indices) {
            if (runningService[i].service.className.toString() == ServiceName) {
                return true
            }
        }
        return false
    }
    fun initSpeech(context:Context){
        if (!isServiceRunning(context, com.lennon.speech.SpeechService.ACTION)) {
            val intent = Intent(context, SpeechService::class.java)
            if (Build.VERSION.SDK_INT >= 26) {
                context.startForegroundService(intent)
            } else {
                // Pre-O behavior.
                context.startService(intent)
            }
        }
    }
}
