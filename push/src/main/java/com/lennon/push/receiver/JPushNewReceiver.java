package com.lennon.push.receiver;

import android.content.Context;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 新的消息回调方式中相关回调类。
 * 新的 tag 与 alias 操作回调会在开发者定义的该类的子类中触发。
 * 手机号码设置的回调会在开发者定义的该类的子类中触发。
 */
public class JPushNewReceiver extends JPushMessageReceiver {
    /**
     * 增删查改的操作会在此方法中回调结果。
     */
    @Override
    public void onTagOperatorResult(Context var1, JPushMessage jPushMessage) {
        XLog.e("onTagOperatorResult");
    }

    /**
     * 查询某个 tag 与当前用户的绑定状态的操作会在此方法中回调结果。
     */
    @Override
    public void onCheckTagOperatorResult(Context var1, JPushMessage jPushMessage) {
        XLog.e("onCheckTagOperatorResult");
    }

    /**
     * alias 相关的操作会在此方法中回调结果。
     */
    @Override
    public void onAliasOperatorResult(Context var1, JPushMessage jPushMessage) {
        XLog.e("onAliasOperatorResult");
    }

    /**
     * 设置手机号码会在此方法中回调结果。
     */
    @Override
    public void onMobileNumberOperatorResult(Context var1, JPushMessage jPushMessage) {
        XLog.e("onMobileNumberOperatorResult");
    }
}
