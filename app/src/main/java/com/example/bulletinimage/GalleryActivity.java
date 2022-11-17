package com.example.bulletinimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ArrayList<String> filePaths = new ArrayList<>();
    File[] listFile;

    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getFromSdCard();
        mViewPager = findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(this, filePaths);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    public void getFromSdCard(){
        String folderName = "MyPhotoDir";
        File file = new File(getExternalFilesDir(folderName), "/");
        if (file.isDirectory()){
            listFile = file.listFiles();
            assert listFile != null;
            for (File value : listFile) {
                filePaths.add(value.getAbsolutePath());
            }
        }
    }
}