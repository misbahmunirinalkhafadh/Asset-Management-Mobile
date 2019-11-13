package com.mii.assetmanagement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private LinearLayout appLogo;
    private static int splashTimeOut = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appLogo = (LinearLayout) findViewById(R.id.logo_app);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(SplashActivity.this, InformasiActivity.class);

                //Animate swipe logo app
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair(appLogo, "logoTransition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, pairs);

                startActivity(start, options.toBundle());
                finish();
            }
        }, splashTimeOut);

        Animation splashtitle = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        appLogo.startAnimation(splashtitle);
    }
}
