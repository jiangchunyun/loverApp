package com.example.myapplication;

import android.content.Context;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by MaiBenBen on 2016/10/19.
 */
public class Service {

    public void get(Context context, String url, RequestParams params, final Listener listener){
        HttpRequest.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                listener.Success(new String(bytes));
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                listener.Error();
            }
        });
    }

}
