package com.lennon.push.utill

import android.text.TextUtils
import cn.jpush.android.api.JPushInterface
import com.lennon.cn.utill.base.BaseApplication
import com.lennon.push.Rom
import com.lennon.push.receiver.MiPushReceiver

object Utill{
     fun getPushId(): String {
        if (Rom.isMiui() && !TextUtils.isEmpty(MiPushReceiver.pushCode)) {
            return "mi|" + MiPushReceiver.pushCode
        }
        return JPushInterface.getRegistrationID(BaseApplication.context())
    }
}
