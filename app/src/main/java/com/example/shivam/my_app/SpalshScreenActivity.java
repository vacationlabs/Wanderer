package com.example.shivam.my_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Shivam on 17-01-2016.
 */
public class SpalshScreenActivity extends Activity {

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
         TextView t  = (TextView)findViewById(R.id.textView);
         TextView t2 = (TextView)findViewById(R.id.textView2);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Humanistic.ttf");

        Typeface custom_font2 = Typeface.createFromAsset(getAssets(), "fonts/Wasted.otf");

        t.setTypeface(custom_font2);
        t2.setTypeface(custom_font);

        iv.clearAnimation();
        //t.clearAnimation();
        t2.clearAnimation();
        iv.startAnimation(anim);
        //t.startAnimation(anim);
        t2.startAnimation(anim);

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Intent newMenu = new Intent(SpalshScreenActivity.this, StartActicity.class);
                startActivity(newMenu);
            }
        }.start();
    }





}
