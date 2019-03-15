package com.lennon.record.video.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lennon.cn.utill.base.BaseActivity
import com.lennon.record.R
import com.lennon.record.video.present.VideoRecordPresent
import kotlinx.android.synthetic.main.activity_video_record.*
import android.view.SurfaceHolder
import android.view.WindowManager


@Route(path = "/record/vider")
class VideoRecordActivity : BaseActivity<VideoRecordPresent>() {
    private lateinit var mSurfaceHolder: SurfaceHolder
    override fun getLayoutId(): Int {
        return R.layout.activity_video_record
    }

    override fun newP(): VideoRecordPresent {
        return VideoRecordPresent(this)
    }

    override fun initUi() {
        mSurfaceHolder = record_surfaceView.holder

        record_time.setOnClickListener {
            record_time.setBackgroundResource(R.drawable.record_end)
        }
    }

    override fun initData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 保持Activity处于唤醒状态
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.BLACK
        }
        super.onCreate(savedInstanceState)
    }
}
