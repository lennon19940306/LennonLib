package com.lennon.push.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lennon.push.bean.JPushMessage;
import com.lennon.push.conf.LennonPush;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.jpush.android.api.JPushInterface;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * @author mayixiang
 */
public class MiPushReceiver extends PushMessageReceiver {
    public static String pushCode = "";
    public static boolean enable = false;

    public MiPushReceiver() {
//        Context context=BaseApplication.getAppliction();
//        if (!Util.isServiceRunning(context, SpeechService.ACTION)) {
//            Intent intent = new Intent(context, SpeechService.class);
//            if (Build.VERSION.SDK_INT >= 26) {
//                context.startForegroundService(intent);
//            } else {
//                // Pre-O behavior.
//                context.startService(intent);
//            }
//        }
    }

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
//        mMessage = message.getContent();
//        if (!TextUtils.isEmpty(message.getTopic())) {
//            mTopic = message.getTopic();
//        } else if (!TextUtils.isEmpty(message.getAlias())) {
//            mAlias = message.getAlias();
//        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount = message.getUserAccount();
//        }
    }

    //    onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
//            这个回调方法会在用户手动点击通知后触发。
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
//        mMessage = message.getContent();
//        if (!TextUtils.isEmpty(message.getTopic())) {
//            mTopic = message.getTopic();
//        } else if (!TextUtils.isEmpty(message.getAlias())) {
//            mAlias = message.getAlias();
//        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount = message.getUserAccount();
//        }
    }

    //    nNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
//             这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        getMessage(context, message);
//        mMessage = message.getContent();
//        if (!TextUtils.isEmpty(message.getTopic())) {
//            mTopic = message.getTopic();
//        } else if (!TextUtils.isEmpty(message.getAlias())) {
//            mAlias = message.getAlias();
//        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount = message.getUserAccount();
//        }
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
//        String command = message.getCommand();
//        List<String> arguments = message.getCommandArguments();
//        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
//        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
//        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mRegId = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mStartTime = cmdArg1;
//                mEndTime = cmdArg2;
//            }
//        }
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        if (message != null && message.getCommandArguments() != null && !message.getCommandArguments().isEmpty()) {
            pushCode = message.getCommandArguments().get(0);
            enable = true;
        } else {
            enable = false;
        }
        LennonPush.Companion.isPushAble(enable);
        cn.droidlover.xdroidmvp.log.XLog.e(message.toString());
//        String command = message.getCommand();
//        List<String> arguments = message.getCommandArguments();
//        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
//        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
//        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mRegId = cmdArg1;
//            }
//        }
    }

    private void showActivity(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
        if (message == null || "".equals(message)) {
            return;
        }
        JPushMessage jPushMessage = new Gson().fromJson(message, JPushMessage.class);
        if (jPushMessage.getCategory() == null) {
            return;
        }
        if (!TextUtils.isEmpty(jPushMessage.getUrl())) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(jPushMessage.getUrl());
            intent.setData(content_url);
            context.startActivity(intent);
        }
    }

    private void getMessage(Context context, MiPushMessage message) {
        Map<String, String> map = message.getExtra();
        if (map == null) {
            return;
        }
        String voice = map.get("voice");
        XLog.e("jPushMessage.getSpeak()    " + voice);
    }
}
