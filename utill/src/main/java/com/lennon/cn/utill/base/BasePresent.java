package com.lennon.cn.utill.base;

import cn.droidlover.xdroidmvp.mvp.IView;
import cn.droidlover.xdroidmvp.mvp.XPresent;

public abstract class BasePresent<V extends IView> extends XPresent<V> {

    public BasePresent(V v){
        attachV(v);
    }
}
