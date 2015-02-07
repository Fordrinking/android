package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.*;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.kaidi.fordrinking.AddActivity;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.util.DataShare;
import com.kaidi.fordrinking.util.Misc;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import static com.kaidi.fordrinking.util.Constants.*;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class HomeFragment extends Fragment {
    private MainActivity activity;
    private WebView blogWebView;
    private ObservableScrollView blogContainerView;
    private SwipeRefreshLayout swipeLayout;
    private FloatingActionButton addFabBtn;

    private String jsonStr;
    private String newPostBlog;
    private String morePostBlog;

    private int currentBlogIndex;

    private Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_main_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        DataShare.getInstance().save("HomeFragment", this);
        DataShare.getInstance().save("isMoreBlogLoaded", false);

        currentBlogIndex = 0;

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    swipeLayout.setRefreshing(false);
                }
            }
        };

        blogContainerView = (ObservableScrollView)getActivity().findViewById(R.id.home_blog_container);
        addFabBtn = (FloatingActionButton)getActivity().findViewById(R.id.home_add_btn);
        addFabBtn.attachToScrollView(blogContainerView);
        addFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                // create the transition animation - the images in the layouts
                // of both activities are defined with android:transitionName="robot"
                /*ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(getActivity(), addFabBtn, "robot");
                startActivity(intent, options.toBundle());*/
                startActivity(intent);
            }
        });


        swipeLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeLayoutListener());
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        blogWebView = (WebView) getActivity().findViewById(R.id.home_blog_webview);
        blogWebView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        blogWebView.loadUrl("file:///android_asset/home.html");
        blogWebView.setWebViewClient(new HelloWebViewClient ());


        blogWebView.addJavascriptInterface(this, "HomeFragment");
        blogWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });


        new DownloadNewestBlogs().execute(Misc.getHttpURL(activity, R.string.url_newest_blog));
    }

    @Override
    public void onResume() {
        super.onResume();
        newPostBlog = (String)DataShare.getInstance().retrieve("newPostBlog");
        if (newPostBlog != null) {
            blogWebView.loadUrl("javascript:appinterface.addNewPostBlog()");
        }
    }

    public void loadMoreBlogs() {
        Log.e("HomeFragment", "loadMoreBlogs run");
        new DownloadMoreBlogs().execute(Misc.getHttpURL(activity, R.string.url_more_blog));
    }

    private class SwipeLayoutListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            new DownloadNewestBlogs().execute(Misc.getHttpURL(activity, R.string.url_newest_blog));

            swipeLayout.setRefreshing(true);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeLayout.setRefreshing(false);

                }
            }, 3000);
        }
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class DownloadNewestBlogs extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... urlStr) {
            try {
                HttpGet httpGet = new HttpGet(urlStr[0]);
                HttpResponse httpResponse = activity.getHttpClient().execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    jsonStr = EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void v) {
            blogWebView.loadUrl("javascript:appinterface.getNewestBlog(HomeFragment.getBlogsData())");
        }
    }

    private class DownloadMoreBlogs extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... urlStr) {
            try {
                HttpPost httpPost = new HttpPost(urlStr[0]);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("blogIndex", String.valueOf(currentBlogIndex)));
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = activity.getHttpClient().execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    morePostBlog = EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void v) {
            blogWebView.loadUrl("javascript:appinterface.addMorePostBlog()");
        }
    }



    @JavascriptInterface
    public String getBlogsData() {
        return jsonStr;
    }

    @JavascriptInterface
    public String getNewPostBlog() {
        return newPostBlog;
    }

    @JavascriptInterface
    public String getMorePostBlog() {
        return morePostBlog;
    }

    @JavascriptInterface
    public void setLoadMoreBlogState() {
        DataShare.getInstance().save("isMoreBlogLoaded", false);
        currentBlogIndex += MORE_BLOG_NUMS;
    }

    @JavascriptInterface
    public void sendRefreshStopCode() {
        handler.sendEmptyMessage(0x123);
        currentBlogIndex = 0;
        currentBlogIndex += MORE_BLOG_NUMS;
    }

    @JavascriptInterface
    public void clearNewPostBlog() {
        newPostBlog = "";
        DataShare.getInstance().save("newPostBlog", newPostBlog);
        currentBlogIndex += 1;
    }

    public SwipeRefreshLayout getSwipeLayout() {
        return swipeLayout;
    }

    public void setSwipeLayout(SwipeRefreshLayout swipeLayout) {
        this.swipeLayout = swipeLayout;
    }
}
