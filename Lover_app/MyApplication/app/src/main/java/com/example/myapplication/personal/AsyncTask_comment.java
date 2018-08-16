package com.example.myapplication.personal;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.myapplication.Listener;
import com.example.myapplication.R;
import com.example.myapplication.Service;
import com.example.myapplication.love_people;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 上官刀刀 on 2017/8/1.
 */

public class AsyncTask_comment extends AsyncTask<Context,Integer, List<comment>>{

    private ListView mlistview;
    private Context mcontext;
   private List<comment>   mcomment_list;
    private LayoutInflater minflater;
   private String mid;
    private View mview;
    public AsyncTask_comment(ListView listView,Context context,String id,View view)
    {
        mlistview= listView;
        mcontext=context;
        mid=id;
        mview=view;
        minflater=LayoutInflater.from(context);
    }

    @Override
    protected List<comment> doInBackground(Context... params) {
        SyncHttpClient  client = new SyncHttpClient(); // 创建异步请求的客户端对象
        String url = "http://123.207.141.93/lover_app/showcomment.php"; // 定义请求的地址
        // 创建请求参数的封装的对象
        RequestParams params1 = new RequestParams();
        params1.put("comment_id", mid); // 设置请求的参数名和参数值
        // 执行post方法
        client.post(url, params1, new AsyncHttpResponseHandler() {
            /**
             * 成功处理的方法
             * statusCode:响应的状态码; headers:相应的头信息 比如 响应的时间，响应的服务器 ;
             * responseBody:响应内容的字节
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                if (statusCode == 200) {
                    String str=new String(responseBody);
                    Log.i("message",str);
                    if (str.length()>5) {
                        mcomment_list=new ArrayList<comment>();
                        Gson gson = new Gson();
                        mcomment_list = gson.fromJson(str, new TypeToken<List<comment>>() {
                        }.getType());
                        mlistview.setAdapter(new MyAdapter_comment(mcontext,mcomment_list));

                    }
                }
            }

            /**
             * 失败处理的方法
             * error：响应失败的错误信息封装到这个异常对象中
             */
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                error.printStackTrace();// 把错误信息打印出轨迹来
            }
        });
        return mcomment_list;
    }

    @Override
    protected void onPostExecute(List<comment> comments) {
        super.onPostExecute(comments);
        setListViewHeight(mlistview);

    }

    public void setListViewHeight(ListView listView) {



        ListAdapter listAdapter = listView.getAdapter();



        if (listAdapter == null) {



            return;



        }



        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {



            View listItem = listAdapter.getView(i, null, listView);



            listItem.measure(0, 0);



            totalHeight += listItem.getMeasuredHeight();



        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
