package com.example.Start.util.asyncTasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.Start.util.BasicUtil;
import com.example.Start.util.NetworkUtil;

import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    protected Bitmap doInBackground(String... urls) {
        try {
            Log.d(BasicUtil.LOG_TAG,"Start download image url = " + urls[0]);
            URL url = new URL(urls[0]);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Log.d(BasicUtil.LOG_TAG, "End download image");
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
