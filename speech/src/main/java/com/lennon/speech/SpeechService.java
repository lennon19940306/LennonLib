package com.lennon.speech;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import cn.droidlover.xdroidmvp.log.XLog;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lennon on 2017/10/19.
 */

public class SpeechService extends Service {
    public static final String ACTION = "com.lennon.cn.utill.service.SpeechService";
    private SpeechBroadCast speechBroadCast;
    public static boolean stop = false;
    public static boolean alive = false;
    NotificationManager manager;
    TtsSpeech ttsSpeech;

    @Override
    public void onCreate() {
        super.onCreate();
        speechBroadCast = new SpeechBroadCast(getApplication());
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Speech.Companion.getSpeechAction());
        registerReceiver(speechBroadCast, filter);
    }


    private class SpeechBroadCast extends BroadcastReceiver {
        Application application;

        public SpeechBroadCast(Application application) {
            this.application = application;
        }

        private void speech(String numString) {
            List<String> list = new VoiceTemplate().prefix("success")
                    .numString(numString)
                    .suffix("yuan")
                    .gen();
            VoiceSpeaker.getInstance(application).speak(list);
        }

        private String getNum(String s) {
            Pattern compile = Pattern.compile("\\d+\\.\\d+");
            Matcher matcher = compile.matcher(s);
            if (!matcher.find()) {
                return "0.00";
            }
            return matcher.group();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Speech.Companion.getSpeechAction().equals(intent.getAction())) {
                XLog.e(intent.getAction());
                String text = intent.getStringExtra("text");
                if (Speech.Companion.handleSpeech(text)) {
                    return;
                }
                String a = "";
                if (text.contains("支付宝") && text.contains("元")) {
                    if (text.split("支付宝").length > 1) {
                        a = getNum(text);
                        speech(a);
                        return;
                    }
                } else if (text.contains("微信") && text.contains("元")) {
                    if (text.split("微信").length > 1) {
                        a = getNum(text);
                        speech(a);
                        return;
                    }
                } else if (text.contains("银联") && text.contains("元")) {
                    if (text.split("银联").length > 1) {
                        a = getNum(text);
                        speech(a);
                        return;
                    }
                } else if (text.contains("云闪付") && text.contains("元")) {
                    if (text.split("云闪付").length > 1) {
                        a = getNum(text);
                        speech(a);
                        return;
                    }
                }
                if (ttsSpeech == null) {
                    ttsSpeech = TtsSpeech.getInstance();
                }
//                int i = 0;
//                while (!ttsSpeech.isCanspeech()) {
//                    i++;
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (i == 10) {
//                        return;
//                    }
//                }
                if (ttsSpeech.isCanspeech()) {
                    ttsSpeech.addSpeechText(text);
                } else {
                    XLog.e("语音播报错误：没有可以播报的服务");
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(speechBroadCast);
        if (manager != null) {
            manager.cancel(1);
        }
        if (!stop) {
            Intent intent = new Intent(this, SpeechService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(intent);
            } else {
                // Pre-O behavior.
                startService(intent);
            }
            return;
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        XLog.e(SpeechService.ACTION + "----onLowMemory");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("11111", "语音播报运行中……",
                    NotificationManager.IMPORTANCE_HIGH);

            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            //android5.0加入了一种新的模式Notification的显示等级，共有三种：
            //VISIBILITY_PUBLIC  只有在没有锁屏时会显示通知
            //VISIBILITY_PRIVATE 任何情况都会显示通知
            //VISIBILITY_SECRET  在安全锁和没有锁屏的情况下显示通知
            Notification notification = new Notification.Builder(this, "11111")
                    .setContentTitle(Speech.Companion.getSpeechNotifTitle())
                    .setContentText(Speech.Companion.getSpeechNotifContent())
                    //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示
                    .setSmallIcon(Speech.Companion.getLogo())
                    //设置默认声音和震动
                    .setAutoCancel(false)//点击后取消
                    .setWhen(System.currentTimeMillis())//设置通知时间
                    .setPriority(Notification.PRIORITY_HIGH)//高优先级
                    .setVisibility(Notification.VISIBILITY_PRIVATE)
                    .build();
            startForeground(1, notification);
        } else {
            Notification.Builder builder = new Notification.Builder(this)
                    .setSmallIcon(Speech.Companion.getLogo())
                    .setContentTitle(Speech.Companion.getSpeechNotifTitle())
                    .setContentText(Speech.Companion.getSpeechNotifContent())
                    .setAutoCancel(false);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startForeground(1, builder.build());
            } else {
                startForeground(1, builder.getNotification());
//                startForeground(1, builder.build());
            }
        }
        ttsSpeech = TtsSpeech.getInstance();
        alive = true;
        XLog.e(SpeechService.ACTION + "----onStartCommand");
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
