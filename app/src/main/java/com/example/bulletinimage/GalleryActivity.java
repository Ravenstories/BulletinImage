package com.example.bulletinimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GalleryActivity extends AppCompatActivity {

    ArrayList<String> filePaths = new ArrayList<>();
    File[] listFile;

    ImageView mImageView;

    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getFromSdCard();





        mImageView = findViewById(R.id.imageView);
        mViewPager = findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(this, filePaths);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    public void getFromSdCard(){
        String folderName = "MyPhotoDir";
        File file = new File(getExternalFilesDir(folderName), "/");
        if (file.isDirectory()){
            Log.d("In directory", "Looking for file");
            listFile = file.listFiles();
            assert listFile != null;
            for (File value : listFile) {
                filePaths.add(value.getAbsolutePath());
                Log.d("Path", value.getAbsolutePath());

            }
        }
    }

    public void getFromApi(){
        OkHttpClient client = new OkHttpClient();
        String url = "10.108.137.151/files";
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    final String myResponse = response.body().string();
                    Log.d("onResponse", "myResponse");

                    GalleryActivity.this.runOnUiThread(() -> {
                        byte[] decodeString = Base64.decode(myResponse, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                        mImageView.setImageBitmap( picture);
                        Log.d("Incoming", myResponse);
                    });
                }
            }
        });
    }
}