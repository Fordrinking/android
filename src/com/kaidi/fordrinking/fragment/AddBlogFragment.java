package com.kaidi.fordrinking.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.kaidi.fordrinking.AddContentActivity;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.model.User;
import com.kaidi.fordrinking.model.UserManager;
import com.kaidi.fordrinking.util.DataShare;
import com.kaidi.fordrinking.util.Misc;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaidi on 15-2-2.
 */
public class AddBlogFragment extends Fragment implements AddContent {
    private AddContentActivity activity;
    private EditText titleView;
    private EditText bodyView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.frag_add_blog, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AddContentActivity) getActivity();

        titleView = (EditText) activity.findViewById(R.id.add_blog_title);
        bodyView  = (EditText) activity.findViewById(R.id.add_blog_content);

    }

    @Override
    public void send() {
        String content = bodyView.getText().toString().trim();
        if (content.equals("")) {
            new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
                    .setMessage("Please write your blog!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .show();
        } else {
            new SendBlogs().execute(Misc.getHttpURL(activity, R.string.url_post_blog));
        }
    }

    private class SendBlogs extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urlStr) {
            try {
                String uid     = String.valueOf(UserManager.getCurrentUser(activity).getUid());
                String content = bodyView.getText().toString();
                String title   = titleView.getText().toString();

                HttpClient httpClient = activity.getHttpClient();
                HttpPost httpPost     = new HttpPost(urlStr[0]);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("uid", uid));
                params.add(new BasicNameValuePair("content", content));
                params.add(new BasicNameValuePair("title", title));
                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String msg) {
            DataShare.getInstance().save("newPostBlog", msg);
            activity.finish();
        }
    }
}













