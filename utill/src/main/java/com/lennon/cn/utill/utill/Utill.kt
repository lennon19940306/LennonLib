package com.lennon.cn.utill.utill

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import com.lennon.cn.utill.dialog.CommonAlertDialog
import com.lennon.cn.utill.dialog.OnAlertDialogListener
import java.io.File
import java.io.UnsupportedEncodingException
import java.util.ArrayList

object Utill {
    fun makeDir(dirPath: String) {
        val file = File(dirPath)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    /**
     * 判断是否为平板
     *
     * @return
     */
    fun isPad(context: Context): Boolean {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        // 屏幕宽度
        val screenWidth = display.width.toFloat()
        // 屏幕高度
        val screenHeight = display.height.toFloat()
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        val x = Math.pow((dm.widthPixels / dm.xdpi).toDouble(), 2.0)
        val y = Math.pow((dm.heightPixels / dm.ydpi).toDouble(), 2.0)
        // 屏幕尺寸
        val screenInches = Math.sqrt(x + y)
        // 大于6尺寸则为Pad
        return screenInches >= 6.0
    }

    fun toUtf8(str: String): String? {
        var result: String? = null
        try {
            result = String(str.toByteArray(charset("UTF-8")), charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return result
    }

    fun getFileName(url: String): String {
        val s = url.split("/".toRegex())
            .dropLastWhile { it.isEmpty() }
            .toTypedArray()
        return s[s.size - 1]
    }

    fun StringToStringArray(random: String): List<String> {
        val l = ArrayList<String>()
        val chars = random.toCharArray()
        for (i in chars.indices) {
            l.add(chars[i] + "")
        }
        return l
    }

    @Suppress("DEPRECATION")
    fun getColor(res: Resources, color: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return res.getColor(color, res.newTheme())
        }
        return res.getColor(color)
    }

    fun showTel(context: Context, t: String) {
        var title = t
        val mTel = "4001006501"
        val mAlertDialog = CommonAlertDialog(context)
        mAlertDialog.setDialogListener(object : OnAlertDialogListener() {
            override fun onSure() {
                super.onSure()
                mAlertDialog.dismiss()
                val intent = Intent()
                intent.action = Intent.ACTION_CALL
                intent.data = Uri.parse("tel:$mTel")
                context.startActivity(intent)
            }

            override fun onCancle() {
                super.onCancle()
                mAlertDialog.dismiss()
            }
        })
        if (TextUtils.isEmpty(title)) {
            title = "联系客服"
        }
        mAlertDialog.setTitle(title)
        mAlertDialog.setSureMsg("立即拨号")
        mAlertDialog.setMsg("请拨打：$mTel")
        mAlertDialog.show()
    }

    fun isMobileNO(mobiles: String): Boolean {
        //        mobiles = mobiles.replace(" ", "");
        val telRegex = "[1]\\d{10}" //"[1]"代表第1位为数字1，"\\d{9}"代表后面是可以是0～9的数字，有10位。
        return if (TextUtils.isEmpty(mobiles)) false
        else mobiles.matches(telRegex.toRegex())
    }

}
