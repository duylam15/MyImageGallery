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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        viewPager = findViewById(R.id.viewPager);

        // Nhận danh sách Uri từ Intent
        Intent intent = getIntent();
        imageUris = intent.getParcelableArrayListExtra("imageUris");
        currentPosition = intent.getIntExtra("currentPosition", 0);

        // Thiết lập adapter cho ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUris);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition, false);

        // Gắn sự kiện touch cho ViewPager2
        viewPager.setOnTouchListener((v, event) -> handleTouchEvent(event));
    }

    // Hàm xử lý sự kiện touch
    private boolean handleTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount(); // Số lượng ngón tay đang chạm

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_MOVE: // Khi ngón tay vuốt
                if (pointerCount == 3) {
                    // Chỉ xử lý vuốt với đúng 3 ngón tay
                    float deltaX = event.getX(0) - event.getX(1); // Khoảng cách giữa ngón tay
                    if (Math.abs(deltaX) > 200) { // Kiểm tra vuốt ngang
                        if (event.getX(0) < event.getX(1)) {
                            // Vuốt sang phải -> chuyển ảnh trước đó
                            int previousPosition = currentPosition - 1;
                            if (previousPosition >= 0) {
                                viewPager.setCurrentItem(previousPosition, true);
                                currentPosition = previousPosition;
                            }
                        } else {
                            // Vuốt sang trái -> chuyển ảnh tiếp theo
                            int nextPosition = currentPosition + 1;
                            if (nextPosition < imageUris.size()) {
                                viewPager.setCurrentItem(nextPosition, true);
                                currentPosition = nextPosition;
                            }
                        }
                        return true; // Đã xử lý sự kiện vuốt
                    }
                }
                break;

            case MotionEvent.ACTION_UP: // Khi nhấc ngón tay
            case MotionEvent.ACTION_CANCEL:
                return false;
        }
        return false; // Không xử lý nếu không đúng 3 ngón
    }
}
