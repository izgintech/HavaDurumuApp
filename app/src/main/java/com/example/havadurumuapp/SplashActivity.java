package com.example.havadurumuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Handler yardımıyla belirli bir süre (3 saniye) bekleyip işlem yapacağız
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // SplashActivity'den MainActivity'ye geçiş başlatıyoruz
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, 3000); // 3 saniye demektir
    }
}