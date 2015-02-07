package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.kaidi.fordrinking.AddContentActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.model.UserManager;
import com.kaidi.fordrinking.photopicker.PhotoPickerActivity;
import com.kaidi.fordrinking.photopicker.PhotoPreviewAdapter;
import com.kaidi.fordrinking.util.AndroidMultiPartEntity;
import com.kaidi.fordrinking.util.DataShare;

import java.util.ArrayList;

import com.kaidi.fordrinking.util.Misc;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

/**
 * Created by kaidi on 15-2-2.
 */
public class AddPhotoFragment extends Fragment implements AddContent {
    private AddContentActivity activity;
    private ArrayList<String> selectedURLs;
    private long entitySize = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.frag_add_photo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (AddContentActivity) getActivity();

        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
        startActivity(intent);


    }

    @Override
    public void onResume() {
        super.onResume();
        selectedURLs = (ArrayList<String>) DataShare.getInstance().retrieve("selectedURLs");
        if (selectedURLs != null && selectedURLs.size() != 0) {
            previewPhotos(selectedURLs);
        }
    }

    private void previewPhotos(ArrayList<String> urls) {
        PhotoPreviewAdapter photoPreviewAdapter =
                new PhotoPreviewAdapter(getActivity(), urls);
        GridView gridView = (GridView) getActivity().findViewById(R.id.photo_preview_grid_view);
        gridView.setAdapter(photoPreviewAdapter);


    }

    @Override
    public void send() {
        if (selectedURLs == null || selectedURLs.size() == 0) {
            return;
        }
        new UploadPhotosTask(activity.getHttpClient(),
                selectedURLs).execute(Misc.getHttpURL(activity, R.string.url_post_photos));
    }


    private class UploadPhotosTask extends AsyncTask<String, Integer, String> {

        HttpClient httpClient;
        ArrayList<String> paths;
        ProgressDialog dialog;

        public UploadPhotosTask(HttpClient httpClient, ArrayList<String> paths) {
            this.httpClient = httpClient;
            this.paths = paths;
            dialog = new ProgressDialog(activity);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMessage("Sending 0%, please wait");
            dialog.setMax(100);
            dialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String uid     = String.valueOf(UserManager.getCurrentUser(activity).getUid());

                HttpPost httpPost = new HttpPost(params[0]);
                AndroidMultiPartEntity multipartEntity =
                        new AndroidMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,
                                new AndroidMultiPartEntity.ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) entitySize) * 100));
                            }
                        });
                multipartEntity.addPart("uid", new StringBody(uid.trim()));
                for (int i = 0; i < paths.size(); i++) {
                    String[] arrs = paths.get(i).split("/");
                    String filename = arrs[arrs.length - 1];
                    byte[] imageBytes = Misc.compressImage(paths.get(i));
                    ByteArrayBody byteArrayBody = new ByteArrayBody(imageBytes,
                            "multipart/form-data", filename);
                    multipartEntity.addPart("photo" + i, byteArrayBody);
                }
                entitySize = multipartEntity.getContentLength();
                httpPost.setEntity(multipartEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    //Log.e("http-response", EntityUtils.toString(httpEntity));
                    return EntityUtils.toString(httpResponse.getEntity());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
            //dialog.setOnDismissListener();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            dialog.setProgress(progress[0]);
            dialog.setMessage("Sending " + String.valueOf(progress[0]) + "%, please wait.");
        }

        @Override
        protected void onPostExecute(String msg){
            try {
                // prevents crash in rare case where activity finishes before dialog
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                DataShare.getInstance().save("newPostBlog", msg);
                activity.finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}








