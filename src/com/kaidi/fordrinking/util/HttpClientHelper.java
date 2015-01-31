package com.kaidi.fordrinking.util;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kaidi on 15-1-22.
 */
public class HttpClientHelper {

    private HttpClient httpClient;

    public void post(HashMap<String, Object> data, String url) {

/*
        HttpPost httpPost = new HttpPost(url);
        List<HashMap<String, Object>> params = new ArrayList<HashMap<String, Object>>();

        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("pass", pass));
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            String msg = EntityUtils.toString(httpResponse.getEntity());
            Looper.prepare();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }*/

    }
}
