package id.nawawi.fierbaseapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ObjectAnimator anim1,anim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ImageView iv = ((ImageView)findViewById(R.id.imageView));
        TextView tv = ((TextView)findViewById(R.id.textView));
        anim1 = ObjectAnimator.ofFloat(iv,"alpha",0f,1f).setDuration(2000);
        anim2 = ObjectAnimator.ofFloat(tv,"alpha",0f,1f).setDuration(2000);
        AnimatorSet as = new AnimatorSet();
        as.play(anim1).with(anim2);
        as.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                final Intent intentL = new Intent(SplashScreen.this, LoginActivity.class);
                new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long l) {

                    }
                    public void onFinish() {
                        startActivity(intentL);
                        finish();
                    }
                }.start();
            }
        });
        as.start();

    }
}
