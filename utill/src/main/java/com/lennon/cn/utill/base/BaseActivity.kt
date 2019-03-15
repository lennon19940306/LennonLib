package com.lennon.cn.utill.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import cn.droidlover.xdroidmvp.log.XLog
import cn.droidlover.xdroidmvp.mvp.XActivity
import com.lennon.cn.utill.conf.Lennon
import com.lennon.cn.utill.dialog.CustomProgressDialog
import com.lennon.cn.utill.utill.AppStatusManager
import com.lennon.cn.utill.utill.CrashHandler
import com.lennon.cn.utill.utill.StatusBarUtil
import com.lennon.cn.utill.utill.Utill
import java.lang.Exception

abstract class BaseActivity<P : BasePresent<*>> : XActivity<P>() {
    private var TAG = javaClass.simpleName
    private var dialog: CustomProgressDialog? = null

    override fun showProgressDialog(msg: String) {
        if (dialog != null) dialog!!.dismiss()
        dialog = CustomProgressDialog(getContext())
        dialog!!.setMessage(msg)
        dialog!!.show()
    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun closeProgressDialog() {
        if (null != dialog) dialog!!.dismiss()
    }

    override fun toast(msg: String, second: Int) {
        Toast.makeText(getContext(), msg, second)
            .show()
    }

    override fun toast(msg: String) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT)
            .show()
    }

    override fun showLoading(visibility: Int) {
    }

    override fun showLoadingError(errorType: Int) {

    }

    override fun getContext(): Context {
        return this
    }

    override fun onRefresh(bRefresh: Boolean) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        XLog.e("$TAG:onCreate")
//        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
        Lennon.useDensity(this)
//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        super.onCreate(savedInstanceState)
        if (checkAppStatus()) {
            BaseApplication.restart(this)
            finish()
        } else {
            BaseApplication.addActivity(this)
            try {
                if ((this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) != null) {
                    StatusBarUtil.setColorNoTranslucent(this, Utill.getColor(resources, Lennon.getTitleColor()))
                    //                StatusBarUtil.setImmersiveStatusBar(this, false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        XLog.e("$TAG:onStart()")
    }

    override fun onDestroy() {
        BaseApplication.exitActivity(this)
        XLog.e("$TAG:onDestroy()")
        super.onDestroy()
    }

    override fun onPause() {
        XLog.e("$TAG:onPause()")
        super.onPause()
    }

    override fun onResume() {
        XLog.e("$TAG:onResume()")
        super.onResume()
    }

    override fun onBackPressed() {
        XLog.e("$TAG:onBackPressed()")
        super.onBackPressed()
    }

    override fun onPostResume() {
        XLog.e("$TAG:onPostResume()")
        super.onPostResume()
    }

    override fun onStop() {
        XLog.e("$TAG:onStop()")
        super.onStop()
    }

    private fun checkAppStatus(): Boolean {
        XLog.e(TAG + ":" + AppStatusManager.getInstance().appStatus)
        return AppStatusManager.getInstance().appStatus == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED
    }

    override fun getActivity(): Activity {
        return this
    }
}
