package cn.droidlover.xdroidmvp.utill;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by lennon on 2017/8/23.
 */

public class Utill {
    public static void vibrator(Context context) {
//获取系统震动服务
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
//震动70毫秒
        vib.vibrate(70);
    }
}
