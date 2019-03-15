package com.lennon.push.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by lennon on 2017/9/19.
 */

public class JPushReceiver extends BroadcastReceiver {
    Context context;
    public static boolean enable = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        this.context = context;
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            XLog.e("[JPushReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            XLog.e("[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE),
                    "[JPushReceiver] 接收Registration EXTRA_NOTIFICATION_TITLE : " + bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE),
                    "[JPushReceiver] 接收Registration EXTRA_ALERT : " + bundle.getString(JPushInterface.EXTRA_ALERT),
                    "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            getMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            XLog.e("[JPushReceiver] 接收到推送下来的通知",
                    "[JPushReceiver] 接收Registration EXTRA_NOTIFICATION_TITLE : " + bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE),
                    "[JPushReceiver] 接收Registration EXTRA_ALERT : " + bundle.getString(JPushInterface.EXTRA_ALERT),
                    "[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId,
                    "[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            getMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            XLog.e("[JPushReceiver] 用户点击打开了通知");
            showActivity(context, bundle);
            //打开自定义的Activity
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            XLog.e("[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            enable = connected;
            XLog.e("[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            enable = false;
            XLog.e("[JPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void showActivity(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (message == null || "".equals(message)) {
            return;
        }
//        JPushMessage jPushMessage = new Gson().fromJson(message, JPushMessage.class);
//        if (jPushMessage.getCategory() == null)
//            return;
//        if (!TextUtils.isEmpty(jPushMessage.getUrl())) {
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
//            Uri content_url = Uri.parse(jPushMessage.getUrl());
//            intent.setData(content_url);
//            context.startActivity(intent);
//        }
    }

    private void getMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (message == null || "".equals(message)) {
            return;
        }
//        JPushMessage jPushMessage = new Gson().fromJson(message, JPushMessage.class);
//        XLog.e("jPushMessage.getSpeak()    " + jPushMessage.getVoice());
//        if (!TextUtils.isEmpty(jPushMessage.getVoice())) {
//            if ("2".equals(jPushMessage.getVoice()) || !TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_ALERT)) && SharedPref.getInstance(App.context()).getBoolean("speech", true) && "1".equals(jPushMessage.getVoice())) {
//                XLog.e("     " + bundle.getString(JPushInterface.EXTRA_ALERT));
//                Util.speech(context, bundle.getString(JPushInterface.EXTRA_ALERT));
//            }
//        } else {
//            XLog.e("     " + bundle.getString(JPushInterface.EXTRA_ALERT));
//            Util.speech(context, bundle.getString(JPushInterface.EXTRA_ALERT));
//        }
//        if (jPushMessage.getCategory() == null)
//            return;
//        switch (jPushMessage.getCategory()) {
//            case "mail":
//                Activity activity = BaseApplication.getCuttureActivity();
////                if (activity != null && (activity instanceof MainActivity)) {
////                    Intent msgIntent = new Intent(MainActivity.class.getSimpleName());
////                    Bundle bd = new Bundle();
////                    bd.putSerializable(MainActivity.class.getSimpleName(), jPushMessage);
////                    msgIntent.putExtras(bd);
////                    context.sendBroadcast(msgIntent);
////                }
//                break;
//        }

    }
}