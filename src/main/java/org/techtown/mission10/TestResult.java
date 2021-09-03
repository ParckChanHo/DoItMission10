package org.techtown.mission10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TestResult extends AppCompatActivity {
    ImageView img_home;
    Button btn_result;
    TextView tx_sum, tx_t1, tx_t2;
    int sum=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        btn_result = (Button) findViewById(R.id.btn_result);
        tx_sum = (TextView) findViewById(R.id.tx_sum);
        tx_t1=(TextView)findViewById(R.id.tx_t1);
        tx_t2=(TextView)findViewById(R.id.tx_t2);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼
        actionBar.setHomeAsUpIndicator(R.drawable.back_img);

        img_home = (ImageView)findViewById(R.id.home_btn);
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("LoginOk",true);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        sum = intent.getIntExtra("value",0);
        tx_sum.setText(sum+" 점");

        // 0~9 까지는 Xml에 기록이 되어있다.!!!
        if (sum>9 && sum<=15){
            tx_t1.setText("10~15점 : 가벼운 우울 상태");
            tx_t2.setText("나도 모르게 요즘 좀 우울...");
        }else if(sum>=16 && sum<=23){
            tx_t1.setText("16~23점 : 중간 우울 상태");
            tx_t2.setText("괜찮은 듯 그러나 괜찮지 않은...");
        }else if(sum>=24){
            tx_t1.setText("24점 이상 : 심한 우울 상태");
            tx_t2.setText("누군가의 전문적인 도움이 필요해요!");
        }

        //검진결과 그래프 화면으로 이동
        btn_result = (Button) findViewById(R.id.btn_result);
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TestGraph.class);
                intent.putExtra("value2",sum);
                startActivity(intent);
            }
        });
    } // OnCreate()

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
