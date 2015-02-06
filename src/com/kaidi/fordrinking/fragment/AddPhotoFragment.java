package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.app.backup.FileBackupHelper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.kaidi.fordrinking.AddContentActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.model.UserManager;
import com.kaidi.fordrinking.photopicker.ImageCallback;
import com.kaidi.fordrinking.photopicker.PhotoPickerActivity;
import com.kaidi.fordrinking.photopicker.PhotoPreviewAdapter;
import com.kaidi.fordrinking.util.DataShare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;

import com.kaidi.fordrinking.util.Misc;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.util.EntityUtils;

/**
 * Created by kaidi on 15-2-2.
 */
public class AddPhotoFragment extends Fragment implements AddContent {
    private AddContentActivity activity;
    private ArrayList<String> selectedURLs;
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
                selectedURLs).execute(getString(R.string.url_post_photos));
    }

    @SuppressWarnings("deprecation")
    private class UploadPhotosTask extends AsyncTask<String, Void, Void> {

        HttpClient httpClient;
        ArrayList<String> paths;

        public UploadPhotosTask(HttpClient httpClient, ArrayList<String> paths) {
            this.httpClient = httpClient;
            this.paths = paths;
        }
        protected Void doInBackground(String... params) {
            try {
                String uid     = String.valueOf(UserManager.getCurrentUser(activity).getUid());

                HttpPost httpPost = new HttpPost(params[0]);
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                multipartEntity.addPart("uid", new StringBody(uid.trim()));
                for (int i = 0; i < paths.size(); i++) {
                    Misc.compressImage(activity, paths.get(i));
                    File file = new File(activity.getFilesDir().toString() + "/tmp_upload.jpg");
                    FileBody fileBody = new FileBody(file);
                    multipartEntity.addPart("photo" + i, fileBody);
                }
                httpPost.setEntity(multipartEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);

                HttpEntity httpEntity = httpResponse.getEntity();
                Log.e("http-response", EntityUtils.toString(httpEntity));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void v) {

        }
    }
}








