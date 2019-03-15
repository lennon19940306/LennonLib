package com.lennon.push.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.lennon.cn.utill.base.BaseApplication;
import com.lennon.cn.utill.dialog.CommonAlertDialog;
import com.lennon.cn.utill.dialog.OnAlertDialogListener;
import com.lennon.cn.utill.utill.NetUtil;

/**
 * Created by Administrator on 2016/11/3.
 */
public class NetState extends BroadcastReceiver {
    private static CommonAlertDialog ab;

    @Override
    public void onReceive(final Context context, Intent arg1) {
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (NetUtil.INSTANCE.getNetWorkState(context) == NetUtil.INSTANCE.getNETWORK_NONE()) {
            if (BaseApplication.Companion.getCuttureActivity() != null) {
                ab = new CommonAlertDialog(BaseApplication.Companion.getCuttureActivity());
                ab.setMsg("网络连接断开，请检查网络");
                ab.setSureMsg("设置网络");
                ab.setDialogListener(new OnAlertDialogListener() {
                    @Override
                    public void onCancle() {
                        super.onCancle();
                        ab.dismiss();
                    }

                    @Override
                    public void onSure() {
                        super.onSure();
                        Intent intent = null;
                        //判断手机系统的版本  即API大于10 就是3.0或以上版本
                        if (android.os.Build.VERSION.SDK_INT > 10) {
                            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        } else {
                            intent = new Intent();
                            ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                            intent.setComponent(component);
                            intent.setAction("android.intent.action.VIEW");
                        }
                        BaseApplication.Companion.getCuttureActivity().startActivity(intent);
                    }
                });
                ab.show();
            }
        } else {
            if (ab != null) {
                ab.dismiss();
            }
        }
    }

}
