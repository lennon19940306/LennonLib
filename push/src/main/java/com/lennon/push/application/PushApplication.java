package com.lennon.push.application;

import com.lennon.cn.utill.base.BaseApplication;
import com.lennon.cn.utill.utill.rx.RxTool;


import com.lennon.push.conf.LennonPush;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by lennon on 2018/6/14.
 */

public class PushApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RxTool.makeFlowable(LennonPush.Companion.initPush()).compose(RxTool.getApiTransformer())
                .compose(RxTool.getScheduler())
                .subscribe(new ResourceSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        LennonPush.Companion.initPushComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        LennonPush.Companion.initPushError(t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
