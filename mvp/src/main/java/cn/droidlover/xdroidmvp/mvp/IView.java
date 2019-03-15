package cn.droidlover.xdroidmvp.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by wanglei on 2016/12/29.
 */

public interface IView<P> {

    void bindEvent();

    int getOptionsMenuId();

    int getLayoutId();

    boolean useEventBus();

    P newP();

    void toast(String msg, int second);

    void toast(String msg);

    Activity getActivity();

    void showProgressDialog(String msg);

    void closeProgressDialog();

    void showLoading(int visibility);

    void showLoadingError(int errorType);

    Context getContext();

    void onRefresh(boolean bRefresh);

    /**
     * 初始化UI
     */
    void initUi();

    /**
     * 初始化数据
     */
    void initData();
}
