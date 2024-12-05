package com.example.myimagegallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
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

        // Nhận danh sách Uri từ Intent
        Intent intent = getIntent();
        imageUris = intent.getParcelableArrayListExtra("imageUris");
        currentPosition = intent.getIntExtra("currentPosition", 0);

        // Thiết lập adapter cho ViewPager2
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, imageUris);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition, false);

        // Tắt vuốt 1 ngón tay mặc định của ViewPager2
        View recyclerView = viewPager.getChildAt(0);
        if (recyclerView instanceof RecyclerView) {
            recyclerView.setOnTouchListener((v, event) -> {
                // Chặn mọi sự kiện vuốt 1 ngón tay
                if (event.getPointerCount() == 1) {
                    return true; // Chặn thao tác mặc định
                }
                return false;
            });
        }

        // Áp dụng hiệu ứng chuyển động
        viewPager.setPageTransformer(new FadeOutPageTransformer());

        // Khởi tạo GestureDetector
        gestureDetector = new GestureDetector(this, new GestureListener());

        // Gắn sự kiện touch cho ViewPager2
        viewPager.setOnTouchListener((v, event) -> {
            if (event.getPointerCount() == 3) { // Xử lý khi có 3 ngón tay
                Toast.makeText(this, "Đã nhận diện 3 ngón tay. Vuốt để chuyển ảnh!", Toast.LENGTH_SHORT).show();
                gestureDetector.onTouchEvent(event);
                return true;
            }
            return false;
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private int swipeThreshold; // Khoảng cách vuốt
        private int swipeVelocityThreshold; // Tốc độ vuốt

        public GestureListener() {
            // Lấy thông tin mật độ màn hình
            DisplayMetrics metrics = getResources().getDisplayMetrics();

            // Điều chỉnh ngưỡng dựa trên mật độ màn hình
            swipeThreshold = (int) (50 * metrics.density); // Khoảng cách vuốt
            swipeVelocityThreshold = (int) (250 * metrics.density); // Tốc độ vuốt
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getPointerCount() == 3 && e2.getPointerCount() == 3) { // Chỉ xử lý khi có 3 ngón tay
                float diffX = e2.getX() - e1.getX();

                if (Math.abs(diffX) > swipeThreshold && Math.abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        // Vuốt phải -> chuyển sang ảnh trước đó
                        if (currentPosition > 0) {
                            currentPosition--;
                        }
                    } else {
                        // Vuốt trái -> ảnh tiếp theo
                        if (currentPosition < imageUris.size() - 1) {
                            currentPosition++;
                            viewPager.setCurrentItem(currentPosition, true);
                        }
                    }

                    // Cập nhật trang hiển thị
                    viewPager.setCurrentItem(currentPosition, false);
                    return true;
                }
            }
            return false; // Không xử lý nếu không đáp ứng điều kiện
        }
    }
}
