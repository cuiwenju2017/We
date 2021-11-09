package com.cwj.we.base;

import android.util.Log;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Description : BaseObserver
 *
 * @author XuCanyou666
 * @date 2020/2/7
 */


public abstract class BaseObserver<T> extends DisposableObserver<T> {

    @Override
    protected void onStart() {

    }

    @Override
    public void onNext(T o) {
        onSuccess(o);
    }

    @Override
    public void onError(Throwable e) {
        BaseException be;

        Log.i("onError", "onError: " + e);

        if (e != null) {
            //自定义异常
            if (e instanceof BaseException) {
                be = (BaseException) e;
                //系统异常
            } else {
                if (e instanceof HttpException) {
                    //HTTP错误
                    be = new BaseException(BaseException.BAD_NETWORK_MSG, e);
                } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
                    //连接错误
                    be = new BaseException(BaseException.CONNECT_ERROR_MSG, e);
                } else if (e instanceof InterruptedIOException) {
                    //连接超时
                    be = new BaseException(BaseException.CONNECT_TIMEOUT_MSG, e);
                } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                    //解析错误
                    be = new BaseException(BaseException.PARSE_ERROR_MSG, e);
                } else {
                    be = new BaseException(BaseException.OTHER_MSG, e);
                }
            }
        } else {
            be = new BaseException(BaseException.OTHER_MSG);
        }
        onError(be.getErrorMsg());
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T o);

    public abstract void onError(String msg);

}
