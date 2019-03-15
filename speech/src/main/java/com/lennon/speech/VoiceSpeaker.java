package com.lennon.speech;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import cn.droidlover.xdroidmvp.utill.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class VoiceSpeaker {

    private static VoiceSpeaker sInstance;

    private ExecutorService service;
    private Application application;

    private VoiceSpeaker(Application application) {
        this.application = application;
        service = Executors.newCachedThreadPool();
    }


    public static synchronized VoiceSpeaker getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new VoiceSpeaker(application);
        }
        return sInstance;
    }


    public void speak(final List<String> list) {
        if (service != null) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    start(list);
                }
            });
        }
    }

    private void start(final List<String> list) {
        synchronized (this) {
            final CountDownLatch latch = new CountDownLatch(1);
            MediaPlayer player = new MediaPlayer();
            if (list != null && list.size() > 0) {
                final int[] counter = {0};
                String path = String.format("sound/tts_%s.mp3", list.get(counter[0]));
                AssetFileDescriptor fd = null;
                try {
                    fd = FileUtils.getAssetFileDescription(application,path);
                    player.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
                            fd.getLength());
                    player.prepareAsync();
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            counter[0]++;
                            if (counter[0] < list.size()) {
                                try {
                                    AssetFileDescriptor fileDescriptor = FileUtils.getAssetFileDescription(application,String.format("sound/tts_%s.mp3", list.get(counter[0])));
                                    mp.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                                    mp.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    latch.countDown();
                                }
                            } else {
                                mp.release();
                                latch.countDown();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    latch.countDown();
                } finally {
                    if (fd != null) {
                        try {
                            fd.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            try {
                latch.await();
                this.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
