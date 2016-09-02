package com.example.news;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chen on 16-8-27.
 */
public class NewsContentActivity extends AppCompatActivity {

    private WebView webView;
    private Toolbar toolbar;
    private TextView gonggao, shengao, sheying, subject_textView, newscome;
    private TextView txt_back;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content_activity);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        gonggao = (TextView) findViewById(R.id.gonggao);
        shengao = (TextView) findViewById(R.id.shengao);
        sheying = (TextView) findViewById(R.id.sheying);
        newscome = (TextView) findViewById(R.id.newscome_content);
        subject_textView = (TextView) findViewById(R.id.subject_content);
        subject_textView.setText(bundle.getString("subject"));
        webView = (WebView) findViewById(R.id.webview);
        txt_back = (TextView) findViewById(R.id.txt_back);
        //返回事件
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<String,Integer, NewsContentBean> {

        @Override
        protected NewsContentBean doInBackground(String... params) {
            String contentUrl = "http://open.twtstudio.com/api/v1/news/" + bundle.getString("index");
            return getJsonData(contentUrl);
        }

        @Override
        protected void onPostExecute(NewsContentBean newsContentBean) {
            super.onPostExecute(newsContentBean);
            subject_textView.setText(newsContentBean.getData().getSubject());
            newscome.setText(getString(R.string.news_come) + newsContentBean.getData().getNewscome());
            webView.loadData(newsContentBean.getData().getContent(),"text/html;charset=utf-8",null);
            sheying.setText(getString(R.string.sheying) + newsContentBean.getData().getSheying());
            gonggao.setText(getString(R.string.gonggao) + newsContentBean.getData().getGonggao());
            shengao.setText(getString(R.string.shengao) + newsContentBean.getData().getShengao());
        }
    }

    public static String URL2String(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    @Nullable
    private NewsContentBean getJsonData(String url) {
        String jsonString = null;
        try {
            jsonString = URL2String(new URL(url).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        NewsContentBean result = gson.fromJson(jsonString, NewsContentBean.class);
        if (result.getError_code() == -1) {
            return result;
        } else {
            return null;
        }
    }
}
