package cn.droidlover.xdroidmvp.net;


import androidx.annotation.NonNull;

/**
 * Created by wanglei on 2016/12/24.
 */

public class NetException extends Exception {
    private Throwable exception;
    private int type = NoConnectError;
    private String code;
    public static final int ParseError = 0;   //数据解析异常
    public static final int NoConnectError = 1;   //无连接异常
    public static final int AuthError = 2;   //用户验证异常
    public static final int NoDataError = 3;   //无数据返回异常
    public static final int BusinessError = 4;   //业务异常
    public static final int OtherError = 5;   //其他异常

    public NetException(Throwable exception, int type) {
        this.exception = exception;
        this.type = type;
    }

    public NetException(String detailMessage, int type) {
        super(detailMessage);
        this.type = type;
    }

    public NetException(Throwable exception, int type, String code) {
        this.exception = exception;
        this.type = type;
        this.code = code;
    }

    public NetException(String detailMessage, int type, String code) {
        super(detailMessage);
        this.type = type;
        this.code = code;
    }

    @Override
    public @NonNull
    String getMessage() {
        if (exception != null) {
            return exception.getMessage();
        }
        return super.getMessage();
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
