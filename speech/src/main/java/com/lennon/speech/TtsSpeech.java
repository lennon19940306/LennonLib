package com.lennon.speech;

import android.os.Bundle;
import android.text.TextUtils;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import cn.droidlover.xdroidmvp.log.XLog;

/**
 * Created by lennon on 2018/6/21.
 */

public class TtsSpeech {
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    private static TtsSpeech ttsSpeech;
    private String speech;
    private String nextSpeech;
    private boolean canspeech = false;

    private TtsSpeech() {
// 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(Speech.Companion.getContext(), mTtsInitListener);
    }

    public static TtsSpeech getInstance() {
        if (ttsSpeech == null) {
            ttsSpeech = new TtsSpeech();
        }
        return ttsSpeech;
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            XLog.d("InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                XLog.d("初始化失败,错误码：" + code);
                canspeech = false;
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
                canspeech = true;
            }
        }
    };

    public void addSpeechText(String text) {
        if (TextUtils.isEmpty(speech)) {
            speech = text;
            if (!TextUtils.isEmpty(speech)) {
                speech(speech);
            }
        } else {
            nextSpeech = text;
        }
    }

    public void speech(String text) {
        setParam();
        int code = mTts.startSpeaking(text, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.ico";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

        if (code != ErrorCode.SUCCESS) {
            XLog.e("语音合成失败,错误码: " + code);
        }
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            XLog.e("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            XLog.e("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            XLog.e("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
            XLog.d(String.format(Speech.Companion.getContext().getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
            XLog.d(String.format(Speech.Companion.getContext().getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            speech = nextSpeech;
            nextSpeech = "";
            if (!TextUtils.isEmpty(speech)) {
                speech(speech);
            }
            if (error == null) {
                XLog.e("播放完成");
            } else if (error != null) {
                XLog.e(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);

        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, Speech.Companion.getSpeaker().getValue());
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "100");

        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Speech.Companion.getDataFile() + "/msc/tts.wav");
    }

    public boolean isCanspeech() {
        return canspeech;
    }
}
