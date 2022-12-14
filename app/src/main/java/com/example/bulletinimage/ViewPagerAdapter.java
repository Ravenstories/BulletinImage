package com.example.bulletinimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> imagePaths;
    LayoutInflater mLayoutInflater;

    public  ViewPagerAdapter(Context context, ArrayList<String> imagePaths ){
        this.context = context;
        this.imagePaths = imagePaths;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return imagePaths.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position){
        View itemView = mLayoutInflater.inflate(R.layout.galleryitem, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageViewMain);

        Bitmap myBitMap = BitmapFactory.decodeFile(imagePaths.get(position));
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(myBitMap, 0 , 0, myBitMap.getWidth(), myBitMap.getHeight(), matrix, true);
        imageView.setImageBitmap(rotatedBitmap);

        Objects.requireNonNull(container).addView(itemView);

        Log.d("imageView", "Returning ImageView");
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object){
        container.removeView((LinearLayout) object);
    }
}
