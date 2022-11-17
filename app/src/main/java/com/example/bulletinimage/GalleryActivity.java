package com.example.bulletinimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ArrayList<String> filePaths = new ArrayList<>();
    File[] listFile;

    private String folderName = "MyPhotoDir";
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        // getFromSdCard();
        mViewPager = findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(this, filePaths);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    public void getFromSdCard(){
        File file = new File(getExternalFilesDir(folderName), "/");
        if (file.isDirectory()){
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++){
                filePaths.add(listFile[i].getAbsolutePath());
            }
        }
    }
}