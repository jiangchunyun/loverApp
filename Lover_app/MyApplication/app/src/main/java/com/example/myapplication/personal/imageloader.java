package com.example.myapplication.personal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class imageloader {
    private ImageView mimageView;
    private String murl;
    private LruCache<String,Bitmap> mcaches;
    public imageloader()
    {
        int maxmemory=(int)Runtime.getRuntime().maxMemory();
        int cachesize=maxmemory/4;
        mcaches=new LruCache<String,Bitmap>(cachesize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    public  void addbitmaptocache(String url,Bitmap bitmap)
    {
          if(getbitmapformcache(url)==null)
          {
              mcaches.put(url,bitmap);
          }
    }
    public  Bitmap getbitmapformcache(String url)
    {
          return mcaches.get(url);
    }

    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mimageView.getTag().equals(murl)) {
                mimageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };
    public void showimagebythread(ImageView imageView, final String url)
    {
        if(url!=null&&!url.equals("")) {
            mimageView = imageView;
            murl = url;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                        Bitmap bitmap = getbitmaofromurl(url);
                        Message message = Message.obtain();
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
            }.start();
        }

    }
    public Bitmap getbitmaofromurl(String urlstring)
    {
        Bitmap bitmap;
        InputStream is=null;
        try {

                URL url = new URL(urlstring);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                is = new BufferedInputStream(connection.getInputStream());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap = BitmapFactory.decodeStream(is, null, options);
                connection.disconnect();

                return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return null;
    }
    public void showimagebyascynctask(ImageView imageView,String url)
    {
        Bitmap bitmap=getbitmapformcache(url);
        if(bitmap==null)
        {
            new newsasynctask(imageView,url).execute(url);
        }
        else{
            imageView.setImageBitmap(bitmap);
        }

    }
    private class newsasynctask extends AsyncTask<String,Void,Bitmap>
    {
        private ImageView mimageView;
        private String murl;
        public newsasynctask(ImageView imageView,String url)
        {
          mimageView=imageView;
            murl=url;
        }


        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=getbitmaofromurl(strings[0]);
            if(bitmap!=null)
            {
                addbitmaptocache(strings[0],bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mimageView.getTag().equals(murl)) {
                mimageView.setImageBitmap(bitmap);
            }
        }
    }
}
