package com.example.bulletinimage;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

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
        getFromApi();

        mImageView = findViewById(R.id.imageView);
        mViewPager = findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(this, filePaths);
        mViewPager.setAdapter(mViewPagerAdapter);
    }


    /**
     * Self explanatory. Get the images from the sd card.
     */
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

    /**
     * Needs to be rewritten, but I also need to create a better api.
     */
    public void getFromApi(){
        OkHttpClient client = new OkHttpClient();
        String url = "10.108.137.151/files";

        Request request = new Request.Builder()
                .url("http://192.168.1.90:8080/files/")
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {

            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                Log.d("onResponse API call", response.body().toString());

                try {
                    String apiImage = response.body().string();

                    //Spilt images into correct urls
                    String[] words = apiImage.split(",");
                    System.out.println(Arrays.toString(words));
                    Pattern pattern = Pattern.compile(",");
                    words = pattern.split(apiImage);
                    System.out.println(Arrays.toString(words));

                    //filePaths.add(

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}

