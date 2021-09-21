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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Test1 extends AppCompatActivity{
    ImageView img_home;
    TextView question;
    Button btn_test_next, btn_test_before;
    ProgressBar progressBar;
    RadioGroup rg;
    RadioButton rb;
    public int count=0, sum=0;
    ArrayList<Integer> nextScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        btn_test_next = (Button)findViewById(R.id.btn_test_next); // 다음
        btn_test_before = (Button)findViewById(R.id.btn_test_before); // 이전

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

        //변경 질문, 프로그래스 바, 레디오그룹 값
        question = (TextView) findViewById(R.id.tx_test_question);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.check(R.id.radio_btn1); // 디폴트 값으로 설정해줌. 즉 1번 라디오 버튼이 시작되면 체크가 됨!!
        nextScore = new ArrayList<>();

        btn_test_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++; // count = 1
                int id = rg.getCheckedRadioButtonId();
                if(id==-1){
                    Toast.makeText(getApplicationContext(),"선택되어 있지 않습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                rb = (RadioButton) findViewById(id); // 선택된 라디오 버튼을 연결시켜준다.

                // rg ==> RadioGroup
                // rb ==> RadioButton
                // public int getCheckedRadioButtonId () : 선택된 라디오 버튼의 ID 값을 반환한다.

                if(rb.getText().equals("매우 그렇다")){
                    sum += 7;
                    nextScore.add(7);
                }

                if (rb.getText().equals("그렇다")){
                    sum += 5;
                    nextScore.add(5);
                }

                if(rb.getText().equals("아니다")){
                    sum += 3;
                    nextScore.add(3);
                }

                if(rb.getText().equals("전혀 아니다")){
                    sum += 0;
                    nextScore.add(0);
                }


                if (count == 1){
                    progressBar.setProgress(25);
                    question.setText("최근 심리적인 이유로 체중에 큰 변화가 있었나요?");
                    System.out.println("count1-다음: "+sum);
                }else if(count == 2){
                    progressBar.setProgress(50);
                    question.setText("자신이 실패자라고 생각 되시나요?");
                    System.out.println("count2-다음: "+sum);
                }else if(count == 3){
                    progressBar.setProgress(75);
                    question.setText("일상에 불만족하시나요?");
                    System.out.println("count3-다음: "+sum);
                }else if(count == 4){
                    progressBar.setProgress(100);
                    question.setText("죄책감을 느낄 때가 있나요?");
                    System.out.println("count4-다음: "+sum);
                }
                if(count == 5){
                    Toast.makeText(getApplicationContext(), sum +" 입니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TestResult.class);
                    intent.putExtra("value", sum);
                    startActivity(intent);
                }

                //동작 확인 Toast.makeText(getApplicationContext(), sum +" 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //체크상태 해제 rg.clearCheck();
        btn_test_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count <= 0)
                    count=0;
                else
                    count--;

                int id = rg.getCheckedRadioButtonId();
                if(id==-1){
                    id = 0;
                }
                rb = (RadioButton) findViewById(id);


                if (count == 0){
                    progressBar.setProgress(0);
                    question.setText("요즘 슬픈 기분인가요?");
                    if(sum<=0){
                        System.out.println("점수: "+sum);
                        return;
                    }
                    else{
                        int minus = nextScore.get(count);
                        sum -= minus;
                        nextScore.remove(count);
                    }
                    System.out.println("count0-이전: "+sum);
                }else if (count == 1){
                    progressBar.setProgress(25);
                    question.setText("최근 심리적인 이유로 체중에 큰 변화가 있었나요?");
                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count1-이전: "+sum);
                }else if(count == 2){
                    progressBar.setProgress(50);
                    question.setText("자신이 실패자라고 생각 되시나요?");
                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count2-이전: "+sum);
                }else if(count == 3){
                    progressBar.setProgress(75);
                    question.setText("일상에 불만족하시나요?");
                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count3-이전: "+sum);
                }else if(count == 4){
                    progressBar.setProgress(100);
                    question.setText("죄책감을 느낄 때가 있나요?");
                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count4-이전: "+sum);
                }
            }
        });

    } // onCreate()

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
