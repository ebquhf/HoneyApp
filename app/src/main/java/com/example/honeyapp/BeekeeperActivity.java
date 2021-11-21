package com.example.honeyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import java.io.InputStream;

public class BeekeeperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beekeeper);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MapsActivity.NAME);

        TextView text = (TextView) findViewById(R.id.beekeeperName);
        text.setText(name);

        setHoney(intent.getBooleanExtra(MapsActivity.CHECKED_MESSAGE,false));
        setDescription(intent.getStringExtra(MapsActivity.DESC_MESSAGE));
        setAvatar(intent.getStringExtra(MapsActivity.LINK));

    }

    private void setDescription(String stringExtra) {
        TextView text = (TextView) findViewById(R.id.descriptionBeekeeper);
        text.setText(stringExtra);
    }

    public void setHoney(boolean hasHoney){
        CheckBox honeyBox = (CheckBox) findViewById(R.id.hasHoneyBox);
        honeyBox.setChecked(hasHoney);
    }

    public void setAvatar(String url) {

        new DownloadImageTask((ImageView) findViewById(R.id.imageBeekeeper))
                .execute(url);

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }




}