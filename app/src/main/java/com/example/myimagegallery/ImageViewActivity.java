package com.example.myimagegallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class ImageViewActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<Uri> imageUris;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        viewPager = findViewById(R.id.viewPager);

        // Nhận Uri từ Intent và danh sách ảnh
        Intent intent = getIntent();
        imageUris = intent.getParcelableArrayListExtra("imageUris"); // Nhận danh sách Uri
        currentPosition = intent.getIntExtra("currentPosition", 0); // Vị trí bắt đầu

        // Thiết lập adapter cho ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUris);
        viewPager.setAdapter(adapter);

        // Thiết lập vị trí ban đầu của ViewPager
        viewPager.setCurrentItem(currentPosition, false);

        // Thiết lập hiệu ứng chuyển đổi trang
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
    }
}
