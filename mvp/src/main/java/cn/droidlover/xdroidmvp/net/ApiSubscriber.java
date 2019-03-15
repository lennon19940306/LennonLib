package cn.droidlover.xdroidmvp.net;

import androidx.annotation.NonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.net.UnknownHostException;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by wanglei on 2016/12/26.
 */

public abstract class ApiSubscriber<T extends IModel> extends ResourceSubscriber<T> {


    @Override
    public abstract void onNext(@NonNull T t) ;

    @Override
    public void onError(@NonNull Throwable e) {
        NetException error = null;
        if (e != null) {
            if (!(e instanceof NetException)) {
                if (e instanceof UnknownHostException) {
                    error = new NetException(e, NetException.NoConnectError);
                } else if (e instanceof JSONException
                        || e instanceof JsonParseException
                        || e instanceof JsonSyntaxException) {
                    error = new NetException(e, NetException.ParseError);
                } else {
                    error = new NetException(e, NetException.OtherError);
                }
            } else {
                error = (NetException) e;
            }

            if (useCommonErrorHandler()
                    && XApi.getCommonProvider() != null) {
                if (XApi.getCommonProvider().handleError(error)) {        //使用通用异常处理
                    return;
                }
            }
            onFail(error);
        }

    }

    protected abstract void onFail(@NonNull NetException error);

    @Override
    public void onComplete() {

    }


    protected boolean useCommonErrorHandler() {
        return true;
    }


}
