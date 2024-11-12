package com.example.myimagegallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.bumptech.glide.Glide;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        PhotoView photoView = findViewById(R.id.photoView);

        // Nhận Uri từ Intent
        Uri imageUri = getIntent().getParcelableExtra("imageUri");
        if (imageUri != null) {
            // Sử dụng Glide để tải ảnh với ảnh thu nhỏ trước
            Glide.with(this)
                    .load(imageUri)
                    .thumbnail(0.1f) // Hiển thị ảnh thumbnail với kích thước 10% trước khi tải đầy đủ
                    .into(photoView);
        }
    }
}
