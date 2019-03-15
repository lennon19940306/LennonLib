package com.lennon.push.conf

import android.app.ActivityManager
import android.app.Notification
import android.content.Context
import android.os.Process
import cn.droidlover.xdroidmvp.log.XLog
import cn.jpush.android.api.BasicPushNotificationBuilder
import cn.jpush.android.api.JPushInterface
import com.lennon.cn.utill.base.BaseApplication
import com.lennon.cn.utill.conf.Lennon
import com.lennon.push.Rom
import com.xiaomi.channel.commonutils.logger.LoggerInterface
import com.xiaomi.mipush.sdk.Logger
import com.xiaomi.mipush.sdk.MiPushClient

abstract class LennonPushProvide {
    abstract fun initPushError(t: Throwable)
    abstract fun initPushComplete()
    fun initPush() {
        if (Rom.isMiui() && shouldInit() && LennonPush.getPushConf("mi_push") != null && LennonPush.getPushConf("mi_push") != null) {
            // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
            // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
            val pushConf = LennonPush.getPushConf("mi_push")
            MiPushClient.registerPush(BaseApplication.context()!!, pushConf!!.AppID(), pushConf!!.AppKey())
            MiPushClient.resumePush(BaseApplication.context()!!, null)
            //打开Log
            val newLogger = object : LoggerInterface {

                override fun setTag(tag: String) {
                    // ignore
                    XLog.e(tag)
                }

                override fun log(content: String, t: Throwable) {
                    XLog.e(content, t)
                }

                override fun log(content: String) {
                    XLog.e(content)
                }
            }
            Logger.setLogger(BaseApplication.context()!!, newLogger)
        } else {
            setNotification()
            JPushInterface.setDebugMode(BaseApplication.isTest())
            JPushInterface.init(BaseApplication.context()!!)
            JPushInterface.initCrashHandler(BaseApplication.context()!!)
        }
    }

    private fun shouldInit(): Boolean {
        val am = BaseApplication.getAppliction()!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfos = am.runningAppProcesses
        val mainProcessName = BaseApplication.getAppliction()!!.packageName
        val myPid = Process.myPid()
        for (info in processInfos) {
            if (info.pid == myPid && mainProcessName == info.processName) {
                return true
            }
        }
        return false
    }

    //自定义报警通知（震动铃声都要）
    private fun setNotification() {
        val builder = BasicPushNotificationBuilder(BaseApplication.context()!!)
        builder.statusBarDrawable = Lennon.getLogo()//消息栏显示的图标
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL //设置为自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE or
                Notification.DEFAULT_LIGHTS// 设置为铃声与震动都要
        JPushInterface.setDefaultPushNotificationBuilder(builder)
    }

    fun isPushAble(enable: Boolean) {

    }
}
