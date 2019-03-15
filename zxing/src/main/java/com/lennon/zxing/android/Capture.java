package com.lennon.zxing.android;

import android.content.Intent;
import android.os.Handler;
import com.google.zxing.Result;
import com.lennon.zxing.bean.ZxingConfig;
import com.lennon.zxing.camera.CameraManager;
import com.lennon.zxing.view.ViewfinderView;

/**
 * 作者：11361 on 2019/1/24 09:44
 * <p>
 * 邮箱：1136160757@qq.com
 */
public interface Capture {
    ViewfinderView getViewfinderView();

    Handler getHandler();

    CameraManager getCameraManager();

    void drawViewfinder();

    void setResult(int resultOk, Intent obj);

    void finish();

    void switchFlashImg(int flashOpen);

    /**
     * @param rawResult 返回的扫描结果
     */
    void handleDecode(Result rawResult);

    ZxingConfig getZxingConfig();
}
