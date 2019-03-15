package cn.droidlover.xdroidmvp.utill

import android.app.Application
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.text.TextUtils
import java.io.File


object FileUtils {
    @JvmStatic
    fun getAssetFileDescription(application: Application, filename: String): AssetFileDescriptor {
        val manager = application.assets
        return manager.openFd(filename)
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    fun getFileName(filePath: String?): String {
        return if (TextUtils.isEmpty(filePath)) "" else filePath!!.substring(filePath.lastIndexOf(File.separator) + 1)
    }
}