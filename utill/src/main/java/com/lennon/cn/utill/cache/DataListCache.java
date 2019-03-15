package com.lennon.cn.utill.cache;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lennon.cn.utill.base.BaseApplication;
import com.lennon.cn.utill.bean.HttpEntity;
import com.lennon.cn.utill.conf.Lennon;
import com.trello.rxlifecycle3.LifecycleProvider;

import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.mvp.IView;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetException;
import cn.droidlover.xdroidmvp.net.XApi;
import io.reactivex.Flowable;

/**
 * Created by lennon on 2017/10/18.
 */

public class DataListCache<T extends HttpEntity> {
    IView activity;
    String dataKey;
    LifecycleProvider lifecycleProvider;
    DataCallBack<T> dataCallBack;
    ACache maCache;
    int startPage = 1;
    int page = 0;
    protected Type tClass;
    private boolean refresh = false;

    public static void initDataCache(String DataCacheKey) {
        SharedPref.getInstance(BaseApplication.Companion.context()).putString("DataCacheKey", DataCacheKey);
    }

    public DataListCache(IView activity, String dataKey, int startPage,
                         LifecycleProvider lifecycleProvider, DataCallBack<T> dataCallBack, Type tClass) {
        this.tClass = tClass;
        this.activity = activity;
        this.dataKey = dataKey;
        this.startPage = startPage;
        this.lifecycleProvider = lifecycleProvider;
        this.dataCallBack = dataCallBack;
        this.maCache = ACache.getInstance(BaseApplication.Companion.context());
    }

    private void getData(int page) {
        T t = getLocalData(page);
        if (t == null) {
            activity.showProgressDialog("加载中……");
        } else {
            if (dataCallBack != null) {
                dataCallBack.upView(t, page);
            }
        }
        getDataFromNet(page);
    }

    public void getData() {
        getData(startPage);
    }

    public void refresh() {
        refresh = true;
        getDataFromNet(startPage);
    }

    String getKey(String a) {
        return a + SharedPref.getInstance(BaseApplication.Companion.context()).getString("DataCacheKey", "");
    }

    public void loadMore() {
        getData(page + 1);
    }

    void saveData(T t, int page) {
        maCache.put(getKey(dataKey) + "_" + page, new Gson().toJson(t));
    }

    T getLocalData(int page) {
        if (TextUtils.isEmpty(maCache.get(getKey(dataKey) + "_" + page))) {
            return null;
        }
        T t = new Gson().fromJson(maCache.get(getKey(dataKey) + "_" + page), tClass);
        return t;
    }

    void getDataFromNet(final int page) {
        this.page = page;
        dataCallBack.getFlowable(page).compose(XApi.<T>getApiTransformer())
                .compose(XApi.<T>getScheduler())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .subscribe(new ApiSubscriber<T>() {
                    @Override
                    protected void onFail(NetException error) {
                        if (getLocalData(page) != null) {
                            dataCallBack.netError(error);
                        } else {
                            dataCallBack.netErrorForNoData(error);
                        }
                        activity.closeProgressDialog();
                        if (error.getType() == NetException.AuthError) {
                            activity.toast("登陆失效");
                            Lennon.Companion.requserLogin();
                            return;
                        }
                    }

                    @Override
                    public void onNext(T o) {
                        if (getLocalData(page) != null) {
                            String a = new Gson().toJson(getLocalData(page));
                            String b = new Gson().toJson(o);
                            if (a.equals(b) && refresh) {
                                dataCallBack.upView(o, page);
                            } else if (a.equals(b) && !refresh) {

                            } else {
                                dataCallBack.upView(o, page);
                                saveData(o, page);
                            }
                        } else {
                            dataCallBack.upView(o, page);
                            saveData(o, page);
                        }
                        activity.closeProgressDialog();
                    }
                });
    }

    public interface DataCallBack<T> {
        void upView(@NonNull T t, int page);

        void netErrorForNoData(@NonNull NetException error);

        void netError(@NonNull NetException error);

        Flowable<T> getFlowable(int page);
    }
}
