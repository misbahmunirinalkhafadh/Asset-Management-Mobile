package com.mii.assetmanagement.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.mii.assetmanagement.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    private LinearLayout appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();

        appLogo = findViewById(R.id.logo_app);

        int splashTimeOut = 5000;
        new Handler().postDelayed(() -> {
            Intent start = new Intent(SplashActivity.this, LoginActivity.class);
            //Animate swipe logo app
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair(appLogo, "logoTransition");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);

            startActivity(start, options.toBundle());
            finish();
        }, splashTimeOut);

        Animation splashing = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        appLogo.startAnimation(splashing);
    }
}
