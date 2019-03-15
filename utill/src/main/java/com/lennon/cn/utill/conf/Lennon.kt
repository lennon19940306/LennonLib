package com.lennon.cn.utill.conf

import android.app.Activity
import android.content.Context
import cn.droidlover.xdroidmvp.net.NetException
import com.lennon.cn.utill.utill.DensityUtils
import lennon.com.utill.R

class Lennon {
    companion object {
        var provider: LennonProvider? = null

        fun registProvider(provider: LennonProvider) {
            this.provider = provider
        }

        fun appName(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.appName()
        }

        fun requserLogin() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.requserLogin()
        }

        fun appExit() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.appExit()
        }

        fun restart(context: Context) {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.restart(context)
        }

        fun getLogo(): Int {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getLogo()
        }

        fun getTitleColor(): Int {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return if (provider!!.getTitleColor() > 0) {
                provider!!.getTitleColor()
            } else {
                R.color.color_fd0202
            }
        }

        fun clean() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            provider!!.clean()
        }

        fun isTest(): Boolean {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.isTest()
        }

        fun getFilePathName(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getFilePathName()
        }

        fun useDensity(activity: Activity) {
            if (provider == null) {
                DensityUtils.setDefault(activity)
            } else {
                provider!!.useDensity(activity)
            }
        }

        fun handleNetError(error: NetException): Boolean {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.handleNetError(error)
        }

        fun restartApp() {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
             provider!!.restartApp()
        }
    }
}