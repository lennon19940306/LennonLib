package com.lennon.cn.utill.utill;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import cn.droidlover.xdroidmvp.log.XLog;
import com.lennon.cn.utill.conf.Lennon;
import com.lennon.cn.utill.dialog.CommonAlertDialog;
import com.lennon.cn.utill.dialog.OnAlertDialogListener;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context context;

    public CrashHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        showToast(t, e);
    }

    /**
     * 操作
     *
     * @param thread
     */
    private void showToast(Thread thread, Throwable t) {
        final CommonAlertDialog dialog = new CommonAlertDialog(context);
        dialog.setTitle("非常抱歉");
        dialog.setMsg("程序发生异常了   " + t.getMessage());
        XLog.e(t.getMessage());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setDialogListener(new OnAlertDialogListener() {
            @Override
            public void onSure() {
                super.onSure();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(context, "程序异常，重新启动", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }).start();

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                restartApp();
            }
        });
        dialog.show();

    }

    /**
     * 重启应用
     */
    private void restartApp() {
        Lennon.Companion.restartApp();
        android.os.Process.killProcess(android.os.Process.myPid());//再此之前可以做些退出等操作
    }
}


