package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16-8-22.
 */
public class TabFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private HomeAdapter mAdapter;
    private List<String> mNewsTitle;
    private List<String> mRead;
    private List<String> mCommented;
    private List<NewsBean.DataBean> newsBeanList = new ArrayList<>();
    private boolean loading = false;
    private Integer NEWS_LIST_ID = 1;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static final int TYPE_FOOTER = 2;
    public static final int TYPE_NORMAL = 1;
    private LayoutInflater mLayoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_item_list, container, false);
        Bundle bundle = getArguments();
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        //设置滚动事件
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int total = layoutManager.getItemCount();
                int lastViewableItem = layoutManager.findLastVisibleItemPosition();
                if (!loading && total < (lastViewableItem) + 2) {
                    NEWS_LIST_ID++;
                    new MyAsyncTask().execute(NEWS_LIST_ID);
                }
            }
        });

        //初始化
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        assert mSwipeRefreshLayout != null;
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                NEWS_LIST_ID = 1;
                mAdapter = new HomeAdapter(newsBeanList, getContext());
                new MyAsyncTask().execute(NEWS_LIST_ID);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        //刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                if (loading) {
                    newsBeanList.clear();
                }
                NEWS_LIST_ID = 1;
                new MyAsyncTask().execute(NEWS_LIST_ID);

            }
        });
        return view;
    }

    public String readStream(InputStream is) {
        InputStreamReader isr;
        String result = " ";
        try {
            String line = " ";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Nullable
    private List<NewsBean.DataBean> getJsonData(String url) {
        String jsonString = null;
        try {
            jsonString = readStream(new URL(url).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        NewsBean result = gson.fromJson(jsonString, NewsBean.class);
        if (result.getError_code() == -1) {
            return result.getData();
        } else {
            return null;
        }
    }

    public Fragment putIndex(int index) {
        Bundle args = new Bundle();
        args.putString("index", String.valueOf(index));
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    class MyAsyncTask extends AsyncTask<Integer, Void, List<NewsBean.DataBean>> {

        @Override
        protected List<NewsBean.DataBean> doInBackground(Integer... params) {
            loading = true;
            Bundle bundle = getArguments();
            String type = bundle.getString("index");
            String url = "http://open.twtstudio.com/api/v1/news/" + type + "/page/" + params[0];
            List<NewsBean.DataBean> newsBeanList = getJsonData(url);
            return newsBeanList;
        }

        @Override
        protected void onPostExecute(List<NewsBean.DataBean> newsBean) {
            super.onPostExecute(newsBean);
            newsBeanList.addAll(newsBeanList.size(), newsBean);
            mAdapter.notifyDataSetChanged();
            loading = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        List<NewsBean.DataBean> newsesList;

        public HomeAdapter(List<NewsBean.DataBean> newsesList, Context context) {
            this.context = context;
            this.newsesList = newsesList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv, read, commented;
            ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.item_title);
                read = (TextView) itemView.findViewById(R.id.news_read);
                commented = (TextView) itemView.findViewById(R.id.news_commented);
                imageView = (ImageView) itemView.findViewById(R.id.pic);
            }


        }

        class FooterViewHolder extends RecyclerView.ViewHolder {
            ProgressBar progressBar;

            public FooterViewHolder(View itemView) {
                super(itemView);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case TYPE_NORMAL:
                    return new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item_normal, parent, false));
                case TYPE_FOOTER:
                    view = LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
                    return new FooterViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
            if (viewHolder instanceof MyViewHolder) {
                MyViewHolder holder = (MyViewHolder) viewHolder;
                final String subject = newsesList.get(position).subject;
                holder.tv.setText(subject);
                holder.read.setText(newsBeanList.get(position).visitcount + getString(R.string.visitcount));
                holder.commented.setText(newsBeanList.get(position).comments + getString(R.string.comments));
                if (!newsBeanList.get(position).pic.equals("")) {
                    Glide.with(getContext()).load(newsBeanList.get(position).pic).into(holder.imageView);
                } else {
                    //无图重新定义ImageView为0
                    RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) holder.imageView.getLayoutParams();
                    relativeParams.width = 0;
                    holder.imageView.setLayoutParams(relativeParams);
                }
                //设置点击事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, NewsContentActivity.class);
                        Bundle intentBundle = new Bundle();
                        intentBundle.putString("index", String.valueOf(newsBeanList.get(position).index));
                        intentBundle.putString("subject", subject);
                        intent.putExtras(intentBundle);
                        context.startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position + 1 >= getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        }

        @Override
        public int getItemCount() {
            return newsesList.size();
        }

    }

}
