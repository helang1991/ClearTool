package com.clean;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.clean.service.CleanService;
import com.clean.service.SpeedService;
import com.clean.widget.CircleAnimationView;
import com.cleartool.R;

/**
 * 请在android 6.0以上使用，其他版本网上一大把方案，也请注意加上系统签名，才能正在使用
 */
public class MainActivity extends FragmentActivity {
    private static final int DELAY = 3500;
    private CircleAnimationView circleAnimationView;
    private Button bt_speed,bt_clean;
    private boolean isWork = false;//是否在工作中
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleAnimationView = findViewById(R.id.view);
        bt_speed = findViewById(R.id.button);
        bt_clean = findViewById(R.id.button3);

        //点击加速
        bt_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isWork){
                    return;
                }
                isWork = true;
                circleAnimationView.setCenterImageView(R.mipmap.ico_speed);
                circleAnimationView.startAnimation();
                enableButton(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isWork = false;
                        enableButton(true);
                        circleAnimationView.stopAnimation();
                        Toast.makeText(MainActivity.this,"加速完成",Toast.LENGTH_SHORT).show();
                    }
                },DELAY);
                startService(new Intent(MainActivity.this,SpeedService.class));
            }
        });
        //点击清理
        bt_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWork = true;
                circleAnimationView.setCenterImageView(R.mipmap.ico_clean);
                circleAnimationView.startAnimation();
                enableButton(false);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isWork = false;
                        enableButton(true);
                        circleAnimationView.stopAnimation();
                        Toast.makeText(MainActivity.this,"清理完成",Toast.LENGTH_SHORT).show();
                    }
                },DELAY);
                startService(new Intent(MainActivity.this,CleanService.class));
            }
        });
    }

    private void enableButton(boolean enable){
        bt_clean.setEnabled(enable);
        bt_speed.setEnabled(enable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        circleAnimationView.stopAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        circleAnimationView.clear();
    }
}
