package com.bimmr.classtracker.tasks;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.activites.SettingsActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Randy on 2018-01-03.
 */

public class DownloadTask extends AsyncTask<String, String, String> {


    private final String FILENAME = "timeManage.pdf";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Starting download");
        ActivityCompat.requestPermissions(SettingsActivity.settingsActivity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        Toast.makeText(Manager.getContext(), "Downloading...", Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... urlString) {

        try {
            URL url = new URL("http://spock.fcs.uga.edu/ext/pubs/time_management.pdf");
            String root = Environment.getExternalStorageDirectory().toString();

            URLConnection connection = url.openConnection();
            connection.connect();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            OutputStream output = new FileOutputStream(root+"/"+FILENAME);
            byte data[] = new byte[1024];

            int count;
            while ((count = input.read(data)) != -1)
                output.write(data, 0, count);

            output.close();
            input.close();

        } catch (FileNotFoundException e) {
            Log.e("Download: ", "Unable to locate file");
        } catch (MalformedURLException e) {
            Log.e("Download: ", "Invalid file URL");
        } catch (IOException e) {
            Log.e("Download: ", "Unable to download file");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        if (file.exists()) {
            Toast.makeText(Manager.getContext(), "Opening File", Toast.LENGTH_SHORT).show();

            if(Build.VERSION.SDK_INT>=24){
                try{
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Manager.getContext().startActivity(intent);
        } else
            Toast.makeText(Manager.getContext(), "Unable to open file", Toast.LENGTH_SHORT).show();
    }


}
