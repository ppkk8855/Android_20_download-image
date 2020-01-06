package com.example.hw20;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new GetImage().execute("https://i.imgur.com/FmADg9T.png");
            }
        });

    }
    private class GetImage extends AsyncTask<String , Integer , Bitmap> {
        ProgressDialogUtil progressDialogUtil;
        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
            super.onPreExecute();
            progressDialogUtil = new ProgressDialogUtil();
            progressDialogUtil.showProgressDialog(MainActivity.this,"等等喔~");
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            //執行中 在背景做事情
            String urlStr = params[0];
            try {
                URL url = new URL(urlStr);
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            //執行中 可以在這邊告知使用者進度
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            //執行後 完成背景任務
            super.onPostExecute(bitmap);
            progressDialogUtil.dismiss();
            imageView.setImageBitmap(bitmap);
        }
    }

    }
