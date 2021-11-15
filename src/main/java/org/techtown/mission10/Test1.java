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


    //RadioButton rb;
    RadioButton rb1, rb2, rb3, rb4;

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

        // 각각의 라디오 버튼을 연결시켜준다.
        rb1 = (RadioButton)findViewById(R.id.radio_btn1);
        rb2 = (RadioButton)findViewById(R.id.radio_btn2);
        rb3 = (RadioButton)findViewById(R.id.radio_btn3);
        rb4 = (RadioButton)findViewById(R.id.radio_btn4);


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

               /* if(rb.getText().equals("매우 그렇다")){
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
                }*/

                 /*if (count == 1){
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
                }*/

                if(rb1.isChecked()){
                    sum += 0;
                    nextScore.add(0);
                }
                if (rb2.isChecked()){
                    sum += 1;
                    nextScore.add(1);
                }

                if(rb3.isChecked()){
                    sum += 2;
                    nextScore.add(2);
                }

                if(rb4.isChecked()){
                    sum += 3;
                    nextScore.add(3);
                }

                if (count == 1){ // 2번째 질문이다.
                    progressBar.setProgress(5);
                    question.setText("미래에 대해 어떻게 생각하시나요?");
                    rb1.setText("낙심하지 않는 편이에요");
                    rb2.setText("별로 희망이 없는 것 같아요");
                    rb3.setText("기대할 것이 아무것도 없어요");
                    rb4.setText("절망적이고 나아질 가능성도 없어요");
                    System.out.println("count1-다음: "+sum);
                }else if(count == 2){
                    progressBar.setProgress(10);
                    question.setText("자신이 실패자라고 생각 되시나요?");
                    rb1.setText("그렇지 않아요");
                    rb2.setText("평균 이하라고 생각해요");
                    rb3.setText("과거를 돌아보니 실패 투성이 같아요");
                    rb4.setText("완전한 실패자인 것 같아요");
                    System.out.println("count2-다음: "+sum);
                }else if(count == 3){
                    progressBar.setProgress(15);
                    question.setText("일상에 만족하시나요?");
                    rb1.setText("네,언제나처럼 만족스러워요");
                    rb2.setText("예전처럼 즐겁지 않아요");
                    rb3.setText("어떤 것에도 만족하지 않아요");
                    rb4.setText("모든 것이 불만스럽고 지겨워요");
                    System.out.println("count3-다음: "+sum);
                }else if(count == 4){
                    progressBar.setProgress(20);
                    question.setText("죄책감을 느낄 때가 있나요?");
                    rb1.setText("아니요");
                    rb2.setText("가끔 그래요");
                    rb3.setText("대부분 그래요");
                    rb4.setText("항상 죄책감에 시달리고 있어요");
                    System.out.println("count4-다음: "+sum);
                }
                else if(count == 5){
                    progressBar.setProgress(24);
                    question.setText("벌을 받는 느낌이 드나요?");
                    rb1.setText("아니요");
                    rb2.setText("어쩌면 그럴지도 몰라요");
                    rb3.setText("곧 벌을 받을 것 같아요");
                    rb4.setText("요즘 벌을 받는 기분이에요");
                    System.out.println("count5-다음: "+sum);
                }
                else if(count == 6){
                    progressBar.setProgress(28);
                    question.setText("자신에게 실망하거나 싫은 기분인가요?");
                    rb1.setText("그렇지 않아요");
                    rb2.setText("실망하고 있어요");
                    rb3.setText("싫고 화가 나요");
                    rb4.setText("스스로가 정말 싫어요");
                    System.out.println("count6-다음: "+sum);
                }
                else if(count == 7){
                    progressBar.setProgress(33);
                    question.setText("안 좋은 일이 생기면 자신을 탓하는 편인가요?");
                    rb1.setText("누구나 그럴 수 있다고 생각해요");
                    rb2.setText("제 약점이나 실수에 대해 저를 탓해요");
                    rb3.setText("제 잘못에 대해 항상 저를 비난해요");
                    rb4.setText("모든 나쁜 일에 대해 저를 비난해요");
                    System.out.println("count7-다음: "+sum);
                }
                else if(count == 8){
                    progressBar.setProgress(37);
                    question.setText("자살에 대해서는 어떻게 생각하나요?");
                    rb1.setText("자살 같은 건 생각해본 적 없어요");
                    rb2.setText("생각은 해봤지만,행동하진 않을 거예요");
                    rb3.setText("죽고 싶다는 생각이 들어요");
                    rb4.setText("기회만 있다면 죽을 거예요");
                    System.out.println("count8-다음: "+sum);
                }
                else if(count == 9){
                    progressBar.setProgress(42);
                    question.setText("요즘 우는 날이 많아지진 않았나요?");
                    rb1.setText("그렇진 않아요");
                    rb2.setText("전보다 많이 울어요");
                    rb3.setText("요즘 항상 울어요");
                    rb4.setText("요즘은 울고 싶어도 눈물이 안 나와요");
                    System.out.println("count9-다음: "+sum);
                }
                else if(count == 10){
                    progressBar.setProgress(47);
                    question.setText("최근에 짜증이 늘었나요?");
                    rb1.setText("그렇진 않아요");
                    rb2.setText("평소보다 신경질적인 것 같아요");
                    rb3.setText("요즘은 꽤 짜증나 있는 것 같아요");
                    rb4.setText("항상 짜증이 나요");
                    System.out.println("count10-다음: "+sum);
                }
                else if(count == 11){
                    progressBar.setProgress(52);
                    question.setText("주위 또는 주변 다른 사람들에게 관심을 가지고 있나요?");
                    rb1.setText("네,관심이 있어요");
                    rb2.setText("전보다는 관심이 줄었어요");
                    rb3.setText("거의 관심이 없는 것 같아요");
                    rb4.setText("전혀 관심이 없어요");
                    System.out.println("count11-다음: "+sum);
                }
                else if(count == 12){
                    progressBar.setProgress(57);
                    question.setText("결정을 내릴 땐 어떤가요?");
                    rb1.setText("결정을 내리는데 큰 어려움은 없어요");
                    rb2.setText("결정을 미루는 때가 많아졌어요");
                    rb3.setText("전보다 결정을 내리기 어려워요");
                    rb4.setText("더는 어떤 결정도 내릴 수 없어요");
                    System.out.println("count12-다음: "+sum);
                }
                else if(count == 13){
                    progressBar.setProgress(62);
                    question.setText("전과 비교해 지금 자신의 외모에 대해 어떻게 생각해요?");
                    rb1.setText("과거나 지금이나 괜찮다고 생각해요");
                    rb2.setText("삭아 보이거나 매력 없어 보일까 걱정돼요");
                    rb3.setText("어떤 것에도 만족하지 않아요");
                    rb4.setText("모든 것이 불만스럽고 지겨워요");
                    System.out.println("count13-다음: "+sum);
                }
                else if(count == 14){
                    progressBar.setProgress(67);
                    question.setText("일(공부 등 개인이 수행하는 것)을 시작할 때의 의지는 어떤가요?");
                    rb1.setText("계속 잘하고 있어요");
                    rb2.setText("전보다 시작이 힘들어요");
                    rb3.setText("무슨 일을 하든 나 자신을 닦달해요");
                    rb4.setText("아무런 일도 할 수 없어요");
                    System.out.println("count14-다음: "+sum);
                }
                else if(count == 15){
                    progressBar.setProgress(72);
                    question.setText("최근 수면 패턴은 어떤가요?");
                    rb1.setText("평소와 같이 잘 자요");
                    rb2.setText("전보다 잘 못자요");
                    rb3.setText("한 두시간 일찍 깨고 다시 잠들기 어려워요");
                    rb4.setText("몇 시간이나 일찍 깨고 잠들지도 못해요");
                    System.out.println("count15-다음: "+sum);
                }
                else if(count == 16){
                    progressBar.setProgress(77);
                    question.setText("피로도는 어떤가요?");
                    rb1.setText("보통 수준이에요");
                    rb2.setText("전보다 피로를 잘 느껴요");
                    rb3.setText("대부분 피곤함을 느껴요");
                    rb4.setText("너무 피곤해서 아무것도 할 수 없어요");
                    System.out.println("count16-다음: "+sum);
                }
                else if(count == 17){
                    progressBar.setProgress(84);
                    question.setText("식욕은 괜찮으신가요?");
                    rb1.setText("네,괜찮아요");
                    rb2.setText("조금 나빠진 것 같아요");
                    rb3.setText("요즘 식욕이 많이 떨어졌어요");
                    rb4.setText("식욕이 전혀 없어요");
                    System.out.println("count17-다음: "+sum);
                }
                else if(count == 18){
                    progressBar.setProgress(92);
                    question.setText("최근 심리적인 이유로 체중에 큰 변화가 있었나요?");
                    rb1.setText("아니요,없었어요");
                    rb2.setText("조금요,2kg 가량 줄었어요");
                    rb3.setText("많아요,4kg 가량 줄었어요");
                    rb4.setText("너무 급격하게 많이 줄었어요");
                    System.out.println("count18-다음: "+sum);
                }
                else if(count == 19){
                    progressBar.setProgress(95);
                    question.setText("건강에 대해서는 어떻게 생각하나요?");
                    rb1.setText("크게 걱정하지 않아요");
                    rb2.setText("소화불량,변비 등을 걱정하고 있어요");
                    rb3.setText("건강이 염려되어 다른 생각이 힘들어요");
                    rb4.setText("건강이 걱정돼 아무 일도 못 하겠어요");
                    System.out.println("count19-다음: "+sum);
                }
                else if(count == 20){
                    progressBar.setProgress(100);
                    question.setText("성(sex)에 대한 관심이 어떤가요?");
                    rb1.setText("별다른 변화 없이 괜찮아요");
                    rb2.setText("전보다 관심이 줄었어요");
                    rb3.setText("상당히 줄어든 것 같아요");
                    rb4.setText("관심을 완전히 잃어버렸어요");
                    System.out.println("count20-다음: "+sum);
                }

                if(count == 21){
                    //Toast.makeText(getApplicationContext(), sum +" 입니다.", Toast.LENGTH_SHORT).show();
                    System.out.println("count21-다음: "+sum);
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
                    rb1.setText("전혀 슬프지 않아요");
                    rb2.setText("슬픈 편이에요");
                    rb3.setText("항상 슬프고 기운이 안 나요");
                    rb4.setText("너무 슬프고 불행해서 견딜 수 없어요");

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
                    progressBar.setProgress(5);
                    question.setText("미래에 대해 어떻게 생각하시나요?");
                    rb1.setText("낙심하지 않는 편이에요");
                    rb2.setText("별로 희망이 없는 것 같아요");
                    rb3.setText("기대할 것이 아무것도 없어요");
                    rb4.setText("절망적이고 나아질 가능성도 없어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count1-이전: "+sum);
                }else if(count == 2){
                    progressBar.setProgress(10);
                    question.setText("자신이 실패자라고 생각 되시나요?");
                    rb1.setText("그렇지 않아요");
                    rb2.setText("평균 이하라고 생각해요");
                    rb3.setText("과거를 돌아보니 실패 투성이 같아요");
                    rb4.setText("완전한 실패자인 것 같아요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count2-이전: "+sum);
                }else if(count == 3){
                    progressBar.setProgress(15);
                    question.setText("일상에 만족하시나요?");
                    rb1.setText("네,언제나처럼 만족스러워요");
                    rb2.setText("예전처럼 즐겁지 않아요");
                    rb3.setText("어떤 것에도 만족하지 않아요");
                    rb4.setText("모든 것이 불만스럽고 지겨워요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count3-이전: "+sum);
                }else if(count == 4){
                    progressBar.setProgress(20);
                    question.setText("죄책감을 느낄 때가 있나요?");
                    rb1.setText("아니요");
                    rb2.setText("가끔 그래요");
                    rb3.setText("대부분 그래요");
                    rb4.setText("항상 죄책감에 시달리고 있어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count4-이전: "+sum);
                }
                else if(count == 5){
                    progressBar.setProgress(24);
                    question.setText("벌을 받는 느낌이 드나요?");
                    rb1.setText("아니요");
                    rb2.setText("어쩌면 그럴지도 몰라요");
                    rb3.setText("곧 벌을 받을 것 같아요");
                    rb4.setText("요즘 벌을 받는 기분이에요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count5-이전: "+sum);
                }
                else if(count == 6){
                    progressBar.setProgress(28);
                    question.setText("자신에게 실망하거나 싫은 기분인가요?");
                    rb1.setText("그렇지 않아요");
                    rb2.setText("실망하고 있어요");
                    rb3.setText("싫고 화가 나요");
                    rb4.setText("스스로가 정말 싫어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count6-이전: "+sum);
                }
                else if(count == 7){
                    progressBar.setProgress(33);
                    question.setText("안 좋은 일이 생기면 자신을 탓하는 편인가요?");
                    rb1.setText("누구나 그럴 수 있다고 생각해요");
                    rb2.setText("제 약점이나 실수에 대해 저를 탓해요");
                    rb3.setText("제 잘못에 대해 항상 저를 비난해요");
                    rb4.setText("모든 나쁜 일에 대해 저를 비난해요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count7-이전: "+sum);
                }
                else if(count == 8){
                    progressBar.setProgress(37);
                    question.setText("자살에 대해서는 어떻게 생각하나요?");
                    rb1.setText("자살 같은 건 생각해본 적 없어요");
                    rb2.setText("생각은 해봤지만,행동하진 않을 거예요");
                    rb3.setText("죽고 싶다는 생각이 들어요");
                    rb4.setText("기회만 있다면 죽을 거예요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count8-이전: "+sum);
                }
                else if(count == 9){
                    progressBar.setProgress(42);
                    question.setText("요즘 우는 날이 많아지진 않았나요?");
                    rb1.setText("그렇진 않아요");
                    rb2.setText("전보다 많이 울어요");
                    rb3.setText("요즘 항상 울어요");
                    rb4.setText("요즘은 울고 싶어도 눈물이 안 나와요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count9-이전: "+sum);
                }
                else if(count == 10){
                    progressBar.setProgress(47);
                    question.setText("최근에 짜증이 늘었나요?");
                    rb1.setText("그렇진 않아요");
                    rb2.setText("평소보다 신경질적인 것 같아요");
                    rb3.setText("요즘은 꽤 짜증나 있는 것 같아요");
                    rb4.setText("항상 짜증이 나요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count10-이전: "+sum);
                }
                else if(count == 11){
                    progressBar.setProgress(52);
                    question.setText("주위 또는 주변 다른 사람들에게 관심을 가지고 있나요?");
                    rb1.setText("네,관심이 있어요");
                    rb2.setText("전보다는 관심이 줄었어요");
                    rb3.setText("거의 관심이 없는 것 같아요");
                    rb4.setText("전혀 관심이 없어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count11-이전: "+sum);
                }
                else if(count == 12){
                    progressBar.setProgress(57);
                    question.setText("결정을 내릴 땐 어떤가요?");
                    rb1.setText("결정을 내리는데 큰 어려움은 없어요");
                    rb2.setText("결정을 미루는 때가 많아졌어요");
                    rb3.setText("전보다 결정을 내리기 어려워요");
                    rb4.setText("더는 어떤 결정도 내릴 수 없어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count12-이전: "+sum);
                }
                else if(count == 13){
                    progressBar.setProgress(62);
                    question.setText("전과 비교해 지금 자신의 외모에 대해 어떻게 생각해요?");
                    rb1.setText("과거나 지금이나 괜찮다고 생각해요");
                    rb2.setText("삭아 보이거나 매력 없어 보일까 걱정돼요");
                    rb3.setText("어떤 것에도 만족하지 않아요");
                    rb4.setText("모든 것이 불만스럽고 지겨워요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count13-이전: "+sum);
                }
                else if(count == 14){
                    progressBar.setProgress(67);
                    question.setText("일(공부 등 개인이 수행하는 것)을 시작할 때의 의지는 어떤가요?");
                    rb1.setText("계속 잘하고 있어요");
                    rb2.setText("전보다 시작이 힘들어요");
                    rb3.setText("무슨 일을 하든 나 자신을 닦달해요");
                    rb4.setText("아무런 일도 할 수 없어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count14-이전: "+sum);
                }
                else if(count == 15){
                    progressBar.setProgress(72);
                    question.setText("최근 수면 패턴은 어떤가요?");
                    rb1.setText("평소와 같이 잘 자요");
                    rb2.setText("전보다 잘 못자요");
                    rb3.setText("한 두시간 일찍 깨고 다시 잠들기 어려워요");
                    rb4.setText("몇 시간이나 일찍 깨고 잠들지도 못해요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count15-이전: "+sum);
                }
                else if(count == 16){
                    progressBar.setProgress(77);
                    question.setText("피로도는 어떤가요?");
                    rb1.setText("보통 수준이에요");
                    rb2.setText("전보다 피로를 잘 느껴요");
                    rb3.setText("대부분 피곤함을 느껴요");
                    rb4.setText("너무 피곤해서 아무것도 할 수 없어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count16-이전: "+sum);
                }
                else if(count == 17){
                    progressBar.setProgress(84);
                    question.setText("식욕은 괜찮으신가요?");
                    rb1.setText("네,괜찮아요");
                    rb2.setText("조금 나빠진 것 같아요");
                    rb3.setText("요즘 식욕이 많이 떨어졌어요");
                    rb4.setText("식욕이 전혀 없어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count17-이전: "+sum);
                }
                else if(count == 18){
                    progressBar.setProgress(92);
                    question.setText("최근 심리적인 이유로 체중에 큰 변화가 있었나요?");
                    rb1.setText("아니요,없었어요");
                    rb2.setText("조금요,2kg 가량 줄었어요");
                    rb3.setText("많아요,4kg 가량 줄었어요");
                    rb4.setText("너무 급격하게 많이 줄었어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count18-이전: "+sum);
                }
                else if(count == 19){ // 20번째 질문이다.(xml 질문까지 포함해서.. xml이 1번째 질문임)
                    progressBar.setProgress(95);
                    question.setText("건강에 대해서는 어떻게 생각하나요?");
                    rb1.setText("크게 걱정하지 않아요");
                    rb2.setText("소화불량,변비 등을 걱정하고 있어요");
                    rb3.setText("건강이 염려되어 다른 생각이 힘들어요");
                    rb4.setText("건강이 걱정돼 아무 일도 못 하겠어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count19-이전: "+sum);
                }
                else if(count == 20){ // 21 마지막
                    progressBar.setProgress(100);
                    question.setText("성(sex)에 대한 관심이 어떤가요?");
                    rb1.setText("별다른 변화 없이 괜찮아요");
                    rb2.setText("전보다 관심이 줄었어요");
                    rb3.setText("상당히 줄어든 것 같아요");
                    rb4.setText("관심을 완전히 잃어버렸어요");

                    int minus = nextScore.get(count);
                    sum -= minus;
                    nextScore.remove(count);
                    System.out.println("count20-이전: "+sum);
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
