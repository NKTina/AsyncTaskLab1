package com.example.martinenezerwa.asynctasklab1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by martine.nezerwa on 10/15/14.
 */
public class async extends AsyncTask<String,Integer,Bitmap>
{
    private Activity activity;

    public async(FragmentActivity myActivity) {
        activity = myActivity;
    }

    @Override
    protected Bitmap doInBackground(String... urls)
    {
        Log.i("hi","hi");
        publishProgress(1);
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new Exception("Failed to connect");
            }
            InputStream input = con.getInputStream();
            publishProgress(0);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;
        } catch (Exception e)
        {
            Log.e("Image", "Failed to load image", e);
            Log.e("error", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap img)
    {
        Log.i("Hello", "Hello");
        ImageView iv = (ImageView) activity.findViewById(R.id.bitmapImage);
        Log.i("Got here","got here");
        if (iv != null && img!= null)
        {
            Log.i("inside","inside");
            iv.setImageBitmap(img);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        TextView dog = (TextView)activity.findViewById(R.id.textView);
        if ( values[0] == 1)
        {
            dog.setText(" Loading..");
        }
        else
        {
            dog.setText("");
        }
    }

}