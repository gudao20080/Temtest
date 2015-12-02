package com.example.stechodebugdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String loginUrl = "http://labour.chinadeer.cn/api/user/index/login";
    private static final String account = "18601176297";
    private static final String pwd = "123456";

    private static final int NETWORK = 1;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
         findViewById(R.id.tv_debug).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getFromNetwork();
             }
         });

        findViewById(R.id.btn_asyncHttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }


    private void getFromNetwork() {
        OkHttpClient client = new OkHttpClient();
        //下面这句话显得尤为重要，加入后才能拦截到http请求。
        client.networkInterceptors().add(new StethoInterceptor());
        //构建请求
        Request request = new Request.Builder()
            .url("http://www.qq.com/")
            .build();
        Response response = null;

        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                //此方法在子线程中，所以通过发送至handler处理
                String body = response.body().string();
                Message message = handler.obtainMessage();
                message.what = NETWORK;
                message.obj = body;
                handler.sendMessage(message);
                response.body().close();
            }


        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
    }
}
