package com.lennon.zxing.utill;

import android.graphics.*;
import android.text.TextUtils;
import cn.droidlover.xdroidmvp.net.NetException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.lennon.cn.utill.base.BaseApplication;
import com.lennon.cn.utill.utill.ZipUtil;
import com.lennon.zxing.R;
import com.lennon.zxing.bean.QRCodeBean;
import io.reactivex.*;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


/**
 * Created by dingyi on 2016/12/1.
 */

public class QRCodeTool {
    /**
     * 线程切换
     *
     * @return
     */
    public static <T> FlowableTransformer<T, T> getScheduler() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 异常处理变换
     *
     * @return
     */
    public static <T> FlowableTransformer<T, T> getApiTransformer() {

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.flatMap(new Function<T, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(T model) throws Exception {
                        if (model == null) {
                            return Flowable.error(new NetException("无数据", NetException.NoDataError, ""));
                        } else {
                            return Flowable.just(model);
                        }
                    }
                });
            }
        };
    }

    public Flowable<Object> saveQrCode(final String content, final int widthPix, final int heightPix, final Bitmap logoBm, final String filePath) {
        return makeFlowable(callableSaveQrCode(content, widthPix, heightPix, logoBm, filePath))
                .subscribeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
    }

    public Flowable<List<Bitmap>> saveQrCodes(List<QRCodeBean> contents, final int widthPix, final int heightPix, final String filePath) {
        return makeFlowable(callableSaveQrCodes(contents, widthPix, heightPix, filePath));
    }

    public Flowable<Bitmap> getLogoQrCode(final Bitmap src, final Bitmap logo) {
        return makeFlowable(callableGetLogoQrCode(src, logo))
                .subscribeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
    }

    public Flowable<Bitmap> getQrCode(final String qrCode, final int widthPix, final int heightPix) {
        return makeFlowable(callableGetQrCode(qrCode, widthPix, heightPix))
                .subscribeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
    }

    public Flowable<List<Bitmap>> getQrCodes(final List<String> qrCodes, final int widthPix, final int heightPix) {
        return makeFlowable(callableGetQrCodes(qrCodes, widthPix, heightPix))
                .subscribeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
    }

    public Flowable<File> saveQrCodesZip(final List<QRCodeBean> contents, final int widthPix, final int heightPix, final String filePath, final String zipPath) {
        return makeFlowable(callableSaveQrCodesZip(contents, widthPix, heightPix, filePath, zipPath));
    }

    public Flowable<File> saveQrCodesZip(final List<QRCodeBean> contents, final int widthPix, final int heightPix, final String filePath, final String zipPath, Bitmap bitmap) {
        return makeFlowable(callableSaveQrCodesZip(contents, widthPix, heightPix, filePath, zipPath, bitmap));
    }

    public Flowable<List<Bitmap>> getQrCodesforShop(String memberId, final List<String> shops, final int widthPix, final int heightPix) {
        List<String> qrCodes = new ArrayList<>();
        for (String shop : shops) {
            qrCodes.add(shop + "&staffMId=" + memberId);
        }
        return makeFlowable(callableGetQrCodes(qrCodes, widthPix, heightPix))
                .subscribeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
    }

    public Flowable<List<Bitmap>> getQrCodesforShop(final String shop, final int widthPix, final int heightPix, boolean flag) {
        List<String> qrCodes = new ArrayList<>();
        qrCodes.add(shop);
        return makeFlowable(callableGetQrCodes(qrCodes, widthPix, heightPix, flag))
                .subscribeOn(Schedulers.io()).delay(1, TimeUnit.SECONDS);
    }

    private Callable<Object> callableSaveQrCode(final String content, final int widthPix, final int heightPix, final Bitmap logoBm, final String filePath) {
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return createQRImage(content, widthPix, heightPix, logoBm, filePath);
            }
        };
    }

    private Callable<List<Bitmap>> callableSaveQrCodes(final List<QRCodeBean> contents, final int widthPix, final int heightPix, final String filePath) {
        return new Callable<List<Bitmap>>() {
            @Override
            public List<Bitmap> call() throws Exception {
                List<Bitmap> list = new ArrayList<Bitmap>();
                for (int i = 0; i < contents.size(); i++) {
                    list.add(createQRImage(contents.get(i).getQRCodeUrl(), widthPix, heightPix, null, filePath + "/" + contents.get(i).getFileName() + ".jpg"));
                }
                return list;
            }
        };
    }

    //flie：要删除的文件夹的所在位置
    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    private Callable<File> callableSaveQrCodesZip(final List<QRCodeBean> contents, final int widthPix, final int heightPix, final String filePath, final String zipPath, final Bitmap bitmap) {
        return new Callable<File>() {
            @Override
            public File call() throws Exception {
                File f = new File(filePath);
                if (f.listFiles() != null && f.listFiles().length > 0) {
                    deleteFile(f);
                }
                f.mkdirs();
                f = new File(filePath + "/back");
                if (!f.exists()) {
                    f.mkdirs();
                }
                List<Bitmap> list = new ArrayList<>();
                for (int i = 0; i < contents.size(); i++) {
                    Bitmap b = createQRImage(contents.get(i).getQRCodeUrl(), widthPix, heightPix, null, filePath + "/" + contents.get(i).getFileName() + ".jpg", contents.get(i).getFileName());
                    b = addBitmap(bitmap, b);
                    b.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath + "/back/" + contents.get(i).getFileName() + ".jpg"));
                    list.add(b);
                }
                return ZipUtil.zip(filePath + "/back", zipPath);
            }
        };
    }

    private Callable<File> callableSaveQrCodesZip(final List<QRCodeBean> contents, final int widthPix, final int heightPix, final String filePath, final String zipPath) {
        return new Callable<File>() {
            @Override
            public File call() throws Exception {
                File f = new File(filePath);
                if (f.listFiles() != null && f.listFiles().length > 0) {
                    deleteFile(f);
                }
                f.mkdirs();
                List<Bitmap> list = new ArrayList<>();
                for (int i = 0; i < contents.size(); i++) {
                    list.add(createQRImage(contents.get(i).getQRCodeUrl(), widthPix, heightPix, null, filePath + "/" + contents.get(i).getFileName() + ".jpg", contents.get(i).getFileName()));
                }
                return ZipUtil.zip(filePath, zipPath);
            }
        };
    }

    private Callable<Bitmap> callableGetLogoQrCode(final Bitmap src, final Bitmap logo) {
        return new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return addLogo(src, logo);
            }
        };
    }

    private Callable<Bitmap> callableGetQrCode(final String qrCode, final int widthPix, final int heightPix) {
        return new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return createQRImage(qrCode, widthPix, heightPix);
            }
        };
    }

    private Callable<List<Bitmap>> callableGetQrCodes(final List<String> qrCodes, final int widthPix, final int heightPix) {
        return new Callable<List<Bitmap>>() {
            @Override
            public List<Bitmap> call() throws Exception {
                List<Bitmap> bitmaps = new ArrayList<>();
                for (String qrCode : qrCodes) {
                    bitmaps.add(addLogo(createQRImage(qrCode, widthPix, heightPix), null));
                }
                return bitmaps;
            }
        };
    }

    private Callable<List<Bitmap>> callableGetQrCodes(final List<String> qrCodes, final int widthPix, final int heightPix, final boolean flag) {
        return new Callable<List<Bitmap>>() {
            @Override
            public List<Bitmap> call() throws Exception {
                List<Bitmap> bitmaps = new ArrayList<>();
                for (String qrCode : qrCodes) {
                    if (flag) {
                        bitmaps.add(addLogo(createQRImage(qrCode, widthPix, heightPix), null));
                    } else {
                        bitmaps.add(createQRImage(qrCode, widthPix, heightPix));
                    }
                }
                return bitmaps;
            }
        };
    }

    /**
     * 生成二维码Bitmap
     *
     * @param content   内容
     * @param widthPix  图片宽度
     * @param heightPix 图片高度
     * @param logoBm    二维码中心的Logo图标（可以为null）
     * @param filePath  用于存储二维码图片的文件路径
     * @return 生成二维码及保存文件是否成功
     */
    private Bitmap createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm, String filePath, String name) {
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
//            hints.put(EncodeHintType.MARGIN, 2); //default is 4

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
//                        pixels[y * widthPix + x] = 0xFFFF0000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            if (bitmap == null) {
                return null;
            }
            if (!TextUtils.isEmpty(name)) {
                bitmap = addTextToBitmap(bitmap, name);
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
            return bitmap;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap addTextToBitmap(Bitmap bmpSrc, String text) {
        int srcWidth = bmpSrc.getWidth();
        int srcHeight = bmpSrc.getHeight();

        // 先计算text所需要的height
        int textSize = srcWidth / 20;
        int padding = srcWidth / 200;
        int textLinePadding = 1;
        // 每行的文字
        int perLineWords = (srcWidth - 2 * padding) / textSize;
        int lineNum = text.length() / perLineWords;
        lineNum = text.length() % perLineWords == 0 ? lineNum : lineNum + 1;
        int textTotalHeight = lineNum * (textSize + textLinePadding) + 2 * padding;

        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight + textTotalHeight + 10,
                Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bmpSrc, 0, 0, null);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setTextSize(textSize);
            String lineText;
            for (int i = 0, startY = srcHeight + textSize, start, end; i < lineNum; i++) {
                start = i * perLineWords;
                end = start + perLineWords;
                lineText = text.substring(start, end > text.length() ? text.length() : end);
                canvas.drawText(lineText, (srcWidth - textSize * lineText.length() + 2 * padding) / 2, startY, paint);
                startY += textSize + textLinePadding;
            }
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.printStackTrace();
            e.getStackTrace();
        }
        return bitmap;
    }

    /**
     * 生成二维码Bitmap
     *
     * @param content   内容
     * @param widthPix  图片宽度
     * @param heightPix 图片高度
     * @param logoBm    二维码中心的Logo图标（可以为null）
     * @param filePath  用于存储二维码图片的文件路径
     * @return 生成二维码及保存文件是否成功
     */
    private Bitmap createQRImage(String content, int widthPix, int heightPix, Bitmap logoBm, String filePath) {
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
//            hints.put(EncodeHintType.MARGIN, 2); //default is 4

            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
//                        pixels[y * widthPix + x] = 0xFFFF0000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);

            if (logoBm != null) {
                bitmap = addLogo(bitmap, logoBm);
            }

            //必须使用compress方法将bitmap保存到文件中再进行读取。直接返回的bitmap是没有任何压缩的，内存消耗巨大！
            if (bitmap == null) {
                return null;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(filePath));
            return bitmap;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private Bitmap addBitmap(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
//        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
//            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save();
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    private Bitmap createQRImage(String qrCode, int widthPix, int heightPix) {
        Bitmap bitmap = null;
        try {
            //判断URL合法性
            if (qrCode == null || "".equals(qrCode) || qrCode.length() < 1) {
                return bitmap;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(qrCode, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
//                        pixels[y * widthPix + x] = 0xfffd0000;
//                        pixels[y * widthPix + x] = 0xff3399ff;
                    } else {
                        pixels[y * widthPix + x] = 0x00000000;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);
            //显示到一个ImageView上面
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private <T> Flowable<T> makeFlowable(final Callable<T> func) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> e) throws Exception {
                try {
                    e.onNext(func.call());
                } catch (Exception e1) {
                }
            }
        }, BackpressureStrategy.ERROR);
    }

    private <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(new ObservableOnSubscribe<T>() {

            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                try {
                    subscriber.onNext(func.call());
                } catch (Exception e) {
                }
            }
        });
    }
}
