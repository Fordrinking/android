package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class HomeFragment extends Fragment {
    private MainActivity activity;
    private WebView blogWebView;
    private SwipeRefreshLayout swipeLayout;

    private String jsonStr;
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


        new DownloadNewestBlogs().execute(getString(R.string.url_newest_blog));
    }

    private class SwipeLayoutListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override public void run() {
                    swipeLayout.setRefreshing(false);
                }
            }, 5000);
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

    @JavascriptInterface
    public String getBlogsData() {
        return jsonStr;
    }
}
