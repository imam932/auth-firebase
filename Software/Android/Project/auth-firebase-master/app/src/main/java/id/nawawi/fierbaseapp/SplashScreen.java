package id.nawawi.fierbaseapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final Intent intentL = new Intent(this, LoginActivity.class);
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
}
