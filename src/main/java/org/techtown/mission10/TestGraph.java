package org.techtown.mission10;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestGraph extends AppCompatActivity {
    //진단 그래프 화면이다.
    // 4번째 수정하기 ===> 주석을 달았다!!
    ImageView img_home;
    Button test_graph,test_feeling; // 진단 그래프, 기분 그래프

    //https://javapapers.com/android/android-chart-example-app-using-mpandroidchart/
    // mp android barChartExample 이다.
    BarChart chart;
    ArrayList<BarEntry> NoOfEmp;
    ArrayList year;
    // year ==> x축 , NoOfEmp ==> y축

    Cursor cursor;
    SQLiteDatabase db;
    BarchartDB helper;
    ArrayList<BarScore> barCharts;

    BarEntry barEntry;
    TextView testGraph;
    ArrayList<String> dayArrayList;
    Boolean today=false;

    Map<String, Integer> day1; // 날짜
    Map<String, Integer> day2; // DB에 실제로 저장된 것들이다.
    ArrayList<Integer> dbScore; // DB에 저장됭어 있는 값들
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_graph);

        chart = findViewById(R.id.barchart);
        chart.getAxisRight().setAxisMaxValue(60);
        chart.getAxisLeft().setAxisMaxValue(60);
        NoOfEmp = new ArrayList<>();//y축
        helper = new BarchartDB(this);
        db = helper.getReadableDatabase();

        testGraph = (TextView)findViewById(R.id.testGraph);
        testGraph.setText("");
        dayArrayList = new ArrayList<>();
        //int count=0;  HashMap<String,Integer>에 값 저장하기
        day1 = new HashMap<>();

        for(int i=6; i>=0; i--){
            try {
                dayArrayList.add(MinusDate(i)); // 8-31 9-1 9-2 9-3 9-4 9-5 9-6
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<=6;i++){
            String tempDate = dayArrayList.get(i);
            day1.put(tempDate,i); // 값에 0~6까지의 값이 들어간다.
            System.out.println("tempDate: "+tempDate);
        }

        testGraph.append("\n");

        // db 테이블에서 레코드 읽기
        cursor = db.rawQuery("select value,date,_id from chart where date between date('now', 'start of day','-6 days')" +
                "and date('now', 'start of day')",null);
        barCharts = new ArrayList<>();

        int count2=0;
        dbScore = new ArrayList<>();
        day2 = new HashMap<>();
        while (cursor.moveToNext()){
            int value = cursor.getInt(0);
            dbScore.add(value); // 10 20 30 40 50
            String date = cursor.getString(1);
            day2.put(date,count2); // 0 1 2 3 4
            testGraph.append("count2: "+count2+"  ");
            count2++;
        }


        for(int i=0; i<dayArrayList.size();i++){ // 7번 돈다.
            String day = dayArrayList.get(i); //8-31 9-1 9-2 9-3 9-4 9-5 9-6
            System.out.println("day: "+day);

            if(day2.get(day) !=null){        //8-31 9-1 9-2 9-3 9-4 9-5 9-6
                int intDay1 = day1.get(day); // 0    1   2   3   4   5   6
                int intDay2 = day2.get(day); // 0    1   2   3       4
                System.out.println("day1: "+intDay1+" "+"day2: "+intDay2);

                //Toast.makeText(getApplicationContext(),"day1 : "+intDay1 +"day2: "+intDay2+"\n",Toast.LENGTH_LONG).show();
                if(!(intDay1 == intDay2)){ // 9-5
                    int value = day1.get(day);
                    System.out.println("value: "+value);
                    //Toast.makeText(getApplicationContext(),"value : "+value,Toast.LENGTH_LONG).show();
                    day2.replace(day,value);
                }
            }
        }

        Collection<Integer> day2Index = day2.values();
        ArrayList<Integer> finalIndex = new ArrayList<>();
        for (Integer value : day2Index) {
            finalIndex.add(value);
            System.out.println("day2Index: "+value);
        }

        for(int i=0; i<day2.size();i++){ //5번 0~4까지
            int value = dbScore.get(i); // db에 저장되어 있는 값들.
            int realIndex = finalIndex.get(i); // 0 1 2 3 5

            barEntry = new BarEntry((float)value,realIndex);
            NoOfEmp.add(barEntry); // 세로 값

            System.out.println("결과값(value): "+value+"  "+"결과값(realIndex): "+realIndex);
        }


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        chart.setDescription("오늘");
        year = new ArrayList(); //x축

        year.add(getCalculatedDate("MM/dd", -6));
        year.add(getCalculatedDate("MM/dd", -5));
        year.add(getCalculatedDate("MM/dd", -4));
        year.add(getCalculatedDate("MM/dd", -3));
        year.add(getCalculatedDate("MM/dd", -2));
        year.add(getCalculatedDate("MM/dd", -1));
        year.add(getCalculatedDate("MM/dd", 0));

        xAxis.setLabelsToSkip(0); // 모든 x축값을 화면에 표시해 주기

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "진단그래프");
        chart.animateXY(1000,1000);
        chart.setTouchEnabled(false); //확대하지못하게 막아버림! 별로 안좋은 기능인 것 같아~

        BarData data = new BarData(year, bardataset);

        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        chart.setData(data);
        chart.invalidate();

        test_graph = (Button)findViewById(R.id.btn_test_graph); // 진단 그래프
        test_feeling = (Button)findViewById(R.id.btn_test_feeling); // 기분 그래프

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

        // TestResult.java에서 점수 받아오기.
        Intent intent = getIntent();
        int score = intent.getIntExtra("value2",0);

        SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd");
       /* String date = fm1.format(new Date());*/
        db=helper.getWritableDatabase();

        /*if(today){ // 1번 이상 진행했기 때문에 update가 필요함!!!!
           Toast.makeText(getApplicationContext(),"1번이상 진행을 하셨습니다.",Toast.LENGTH_LONG).show();
        }

        else{
            String sql = "insert into chart(value,date) values('"
                    +score + "', '" +date + "')";
            db.execSQL(sql);
            barEntry = new BarEntry((float)score,6);
            NoOfEmp.add(barEntry); // 세로 값
            reOncreate();
        }*/
    } // onCreate() 함수의 끝이다.

    public void reOncreate(){

        NoOfEmp = new ArrayList<>();//y축
        helper = new BarchartDB(this);
        db = helper.getReadableDatabase();
        // db 테이블에서 레코드 읽기
        cursor = db.rawQuery("select value,date from chart where date between date('now', 'start of day','-6 days')" +
                "and date('now', 'start of day')",null);
        //cursor = db.rawQuery("select type, title, amount, strftime('%Y-%m-%d', updated) from accountBook;", null);

        barCharts = new ArrayList<>();

        while (cursor.moveToNext()){
            int value = cursor.getInt(0);
            String date = cursor.getString(1);

            //Toast.makeText(getApplicationContext(),"value: "+value+"\n"+"date: "+date,Toast.LENGTH_LONG).show();
            barCharts.add(new BarScore(value,date));
        }

        for(int i=0; i<barCharts.size();i++){
            BarScore barScore = barCharts.get(i);
            int value = barScore.getValue();

            //yValue.add(value);
            barEntry = new BarEntry((float)value,i);
            NoOfEmp.add(barEntry);
            //Toast.makeText(getApplicationContext(),"value: "+(float)value,Toast.LENGTH_LONG).show();
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        chart.setDescription("오늘");
        year = new ArrayList(); //x축

        year.add(getCalculatedDate("MM/dd", -6));
        year.add(getCalculatedDate("MM/dd", -5));
        year.add(getCalculatedDate("MM/dd", -4));
        year.add(getCalculatedDate("MM/dd", -3));
        year.add(getCalculatedDate("MM/dd", -2));
        year.add(getCalculatedDate("MM/dd", -1));
        year.add(getCalculatedDate("MM/dd", 0));

        xAxis.setLabelsToSkip(0); // 모든 x축값을 화면에 표시해 주기

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "진단그래프");
        chart.animateXY(1000,1000);
        chart.setTouchEnabled(false); //확대하지못하게 막아버림! 별로 안좋은 기능인 것 같아~
        BarData data = new BarData(year, bardataset);
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        chart.setData(data);
        chart.invalidate();
    }

    public static String MinusDate(int day) throws Exception
    {
        SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
        String changeToday = dtFormat.format(new Date()); // 오늘 날짜를 yyyy-MM-dd 형식으로 바꾼다.

        Calendar cal = Calendar.getInstance();
        Date dt = dtFormat.parse(changeToday);
        cal.setTime(dt);

        cal.add(Calendar.DATE, -day);
        return dtFormat.format(cal.getTime());
    }




    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

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
