package com.example.myimagegallery;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class FadeOutPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setAlpha(1 - Math.abs(position));
        page.setTranslationX(-position * page.getWidth());
        page.setTranslationZ(-Math.abs(position));
    }
}

