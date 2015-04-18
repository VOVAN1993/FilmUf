package com.example.Start.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.Start.R;
import com.example.Start.util.asyncTasks.DownloadImageTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class NetworkUtil {

    public static String connectToServer(String s) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(s);
        HttpResponse response = client.execute(request);

// Get the response
        BufferedReader rd = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent()));

        String line = "";
        StringBuilder builder = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    public static Bitmap getImage(String url, Context ctx){
        DownloadImageTask task = new DownloadImageTask();
        Bitmap result;
        task.execute(url);
        try {
            result = task.get();
            Log.d(BasicUtil.LOG_TAG, "Download image :OK " );
        } catch (ExecutionException | InterruptedException e) {
            Log.e(BasicUtil.LOG_TAG, "Error when download image. " + e.toString());
            result = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_launcher);
        }
        return result ;
    }
}
