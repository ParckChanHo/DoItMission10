package org.techtown.mission10;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.WindowManager;
import android.os.Handler;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Handler hd = new Handler();

    //위젯 연결
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView =(ImageView)findViewById(R.id.pill);
        textView = (TextView)findViewById(R.id.pill_textView);
        //애니메이션 설정
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.spin);
        //애니메이션 세팅
        imageView.setAnimation(anim);
        textView.setAnimation(anim);

        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        },3000);

    }


    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
