package com.example.myimagegallery;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class ImageViewActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<Uri> imageUris;
    private int currentPosition;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        viewPager = findViewById(R.id.viewPager);

        // Nhận Uri từ Intent và danh sách ảnh
        Intent intent = getIntent();
        imageUris = intent.getParcelableArrayListExtra("imageUris");
        currentPosition = intent.getIntExtra("currentPosition", 0);

        // Thiết lập adapter cho ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUris);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition, false);

        // Thêm hiệu ứng chuyển đổi trang
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        // GestureDetector để phát hiện vuốt ba ngón tay
        gestureDetector = new GestureDetector(this, new GestureListener());

        // Gắn sự kiện touch cho ViewPager
        viewPager.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }


    // GestureListener để xử lý vuốt ba ngón tay
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Kiểm tra số ngón tay
            if (e1.getPointerCount() == 3) {
                if (velocityX > 0) {
                    // Vuốt sang phải -> chuyển ảnh trước đó
                    int previousPosition = currentPosition - 1;
                    if (previousPosition >= 0) {
                        viewPager.setCurrentItem(previousPosition, true);
                    }
                } else {
                    // Vuốt sang trái -> chuyển ảnh tiếp theo
                    int nextPosition = currentPosition + 1;
                    if (nextPosition < imageUris.size()) {
                        viewPager.setCurrentItem(nextPosition, true);
                    }
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Bắt sự kiện đa chạm để kiểm tra số ngón tay
        if (ev.getPointerCount() == 3) {
            gestureDetector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
