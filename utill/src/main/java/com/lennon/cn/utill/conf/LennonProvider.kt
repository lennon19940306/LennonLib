package com.lennon.cn.utill.conf

import android.app.Activity
import android.content.Context
import cn.droidlover.xdroidmvp.net.NetException
import com.lennon.cn.utill.utill.DensityUtils
import com.lennon.cn.utill.utill.Utill

abstract class LennonProvider {
    abstract fun appName(): String
    abstract fun requserLogin()
    abstract fun appExit()
    abstract fun restart(context: Context)
    abstract fun getLogo(): Int
    abstract fun getTitleColor(): Int
    abstract fun clean()
    abstract fun isTest(): Boolean
    abstract fun getFilePathName(): String
    open fun useDensity(activity: Activity) {
        if (Utill.isPad(activity)) {
            DensityUtils.setOrientation(activity, DensityUtils.HEIGHT)
        } else {
            DensityUtils.setDefault(activity)
        }
    }

    abstract fun handleNetError(error: NetException): Boolean
    abstract fun restartApp()
}