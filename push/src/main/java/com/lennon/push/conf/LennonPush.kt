package com.lennon.push.conf

import java.util.concurrent.Callable

class LennonPush {
    companion object {
        var provide: LennonPushProvide? = null
        var map = HashMap<String, IPushConf>()
        fun registProvide(provide: LennonPushProvide) {
            Companion.provide = provide
        }

        fun registMiPush(pushConf: IPushConf) {
            map.put("mi_push", pushConf)
        }

        fun registPush(pushName: String, pushConf: IPushConf) {
            map.put(pushName, pushConf)
        }

        fun getPushConf(pushName: String): IPushConf? {
            return if (map.containsKey(pushName)) {
                map[pushName]
            } else {
                null
            }
        }

        fun initPushError(t: Throwable) {
            if (provide == null) {
                throw Throwable("请先注册rovider")
            }
            provide!!.initPushError(t)
        }

        fun initPushComplete() {
            if (provide == null) {
                throw Throwable("请先注册rovider")
            }
            provide!!.initPushComplete()
        }

        fun initPush(): Callable<Any> {
            if (provide == null) {
                throw Throwable("请先注册rovider")
            }
            return Callable { provide!!.initPush() }
        }

        fun isPushAble(enable: Boolean) {
            if (provide == null) {
                throw Throwable("请先注册rovider")
            }
            provide!!.isPushAble(enable)
        }
    }
}
