package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import cn.droidlover.xdroidmvp.log.XLog

import cn.droidlover.xdroidmvp.mvp.XFragment
import lennon.com.utill.R
import com.lennon.cn.utill.dialog.CustomProgressDialog

abstract class BaseFragment<P : BasePresent<*>> : XFragment<P>() {
    private var dialog: CustomProgressDialog? = null
    override fun showLoading(visibility: Int) {
    }
    override fun useEventBus(): Boolean {
        return true
    }
    private var isFirst = true
    private var stop = false
    override fun showLoadingError(errorType: Int) {
    }

    override fun onRefresh(bRefresh: Boolean) {
    }

    override fun showProgressDialog(msg: String) {
        if (dialog != null)
            dialog!!.dismiss()
        dialog = CustomProgressDialog(getContext())
        dialog!!.setMessage(msg)
        dialog!!.show()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        XLog.e(getName() + "  setUserVisibleHint " + isVisibleToUser)
    }

    override fun onResume() {
        super.onResume()
        XLog.e(getName() + "  onResume")
        XLog.e(getName() + "  setUserVisibleHint " + userVisibleHint)
        if (isFirst && userVisibleHint) {
            onVisible()
        } else if (stop && userVisibleHint) {
            onVisible()
        }
        isFirst = false
        stop = false
    }
    /**
     * 可见时的回调方法
     */
    open fun onVisible() {
        XLog.e(getName() + "  onVisible")
    }

    protected fun getName(): String {
        return javaClass.simpleName
    }

    /**
     * 不可见时的回调方法
     */
    fun onInvisible() {
        XLog.e(getName() + "  onInvisible")
    }

    override fun closeProgressDialog() {
        if (null != dialog)
            dialog!!.dismiss()
    }

    override fun toast(msg: String, second: Int) {
        Toast.makeText(getContext(), msg, second).show()
    }

    override fun toast(msg: String) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        stop = true
    }
}
