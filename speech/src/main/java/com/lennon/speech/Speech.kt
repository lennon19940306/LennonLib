package com.lennon.speech

import android.content.Context

class Speech {

    companion object {
        var provider: SpeechProvider? = null
        fun getSpeechAction(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getSpeechAction()
        }

        fun getSpeaker(): Speaker {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return if (provider!!.getSpeaker() != null) {
                provider!!.getSpeaker()!!
            } else {
                Speaker.XiaoYan
            }
        }

        fun registProvider(provider: SpeechProvider) {
            this.provider = provider
            provider.initSpeech()
        }

        fun getContext(): Context {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getContext()
        }

        fun getDataFile(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getDataFile()
        }

        fun handleSpeech(text: String): Boolean {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.handleSpeech(text)
        }

        fun getSpeechNotifTitle(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getSpeechNotifTitle()
        }

        fun getSpeechNotifContent(): String {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getSpeechNotifContent()
        }

        fun getLogo(): Int {
            if (provider == null) {
                throw Throwable("请先注册provider")
            }
            return provider!!.getLogo()
        }
    }
}