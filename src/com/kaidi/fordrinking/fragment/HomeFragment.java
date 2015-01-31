package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.util.WebAppInterface;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class HomeFragment extends Fragment {
    private MainActivity activity;
    private WebView blogWebView;
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

        blogWebView = (WebView) getActivity().findViewById(R.id.home_blog_webview);

        blogWebView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        blogWebView.loadUrl("file:///android_asset/home.html");
        blogWebView.setWebViewClient(new HelloWebViewClient ());


        blogWebView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");


        new DownloadNewestBlogs().execute(getString(R.string.url_newest_blog));
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class DownloadNewestBlogs extends AsyncTask<String, Void, List<Map<String, Object>>> {
        protected List<Map<String, Object>> doInBackground(String... urlStr) {
            try {
                HttpGet httpGet = new HttpGet(urlStr[0]);
                HttpResponse httpResponse = activity.getHttpClient().execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    String msg = EntityUtils.toString(httpResponse.getEntity());
                    JSONArray jsonArray = new JSONArray(msg);
                    List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Map<String, Object> listItem = new HashMap<String, Object>();
                        listItem.put("avatar", jsonArray.getJSONObject(i).getString("avatar"));
                        listItem.put("username", jsonArray.getJSONObject(i).getString("username"));
                        listItem.put("postdate", jsonArray.getJSONObject(i).getString("postdate"));
                        listItem.put("content", jsonArray.getJSONObject(i).getString("content"));
                        listItems.add(listItem);
                    }
                    return listItems;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(List<Map<String, Object>> listItems) {
           // Toast.makeText(getActivity(), "Downloaded!", Toast.LENGTH_SHORT).show();
            String a = "helefdsa";
            blogWebView.loadUrl("javascript:appinterface.callFromActivity(\"" + a + "\")");
        }
    }
}
