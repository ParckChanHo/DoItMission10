package org.techtown.mission10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class board_notice extends AppCompatActivity {
    RecyclerView recyclerView;
    //게시글
    TextView no_title, no_author, no_content, re_size;
    String boardnum;
    //댓글
    CheckBox re_ch;
    EditText re_write;
    Button re_btn;
    String re_author, re_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_notice);

        //툴바 설정
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); //왼쪽 버튼 사용 여부

        //fragment3에서 전달해준 게시글번호 받기
        Intent intent = new Intent(this.getIntent());
        boardnum = intent.getStringExtra("boardnum");
        //Toast.makeText(getApplicationContext(), boardnum, Toast.LENGTH_SHORT).show();

        //게시글 제목, 내용, 작성자 가져오기
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://118.67.131.202:8080/androidproject/notice.jsp?boardnum="+boardnum;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String xml = response.trim();

                            no_title = (TextView) findViewById(R.id.notice_title);
                            no_content = (TextView) findViewById(R.id.notice_content);
                            no_author = (TextView) findViewById(R.id.notice_author);

                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(true);
                            XmlPullParser parser = factory.newPullParser();
                            parser.setInput(new StringReader(xml));

                            String title="", content="", author=""; //제목, 내용, 작성자, 게시글번호
                            int evenType = parser.getEventType();
                            boolean a = false, b = false, c = false, d = false;
                            while (evenType != XmlPullParser.END_DOCUMENT){
                                if (evenType == XmlPullParser.START_TAG){
                                    if (parser.getName().equals("title")) a = true;
                                    if (parser.getName().equals("content")) b = true;
                                    if (parser.getName().equals("author")) c = true;
                                }else if(evenType == XmlPullParser.TEXT){
                                    if (a){
                                        title = parser.getText();
                                        no_title.setText(title);
                                        a = false;
                                    }
                                    if (b){
                                        content = parser.getText();
                                        no_content.setText(content);
                                        b = false;
                                    }
                                    if (c){
                                        author = parser.getText();
                                        no_author.setText(author);
                                        c = false;
                                    }
                                }
                                evenType = parser.next();
                            }
                        } catch (XmlPullParserException | IOException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);

        //댓글 읽기 기능
        re_size = (TextView) findViewById(R.id.reply_size);
        //댓글 가져오기
        RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        String url2 = "http://118.67.131.202:8080/androidproject/reply.jsp?boardnum="+boardnum;
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String xml = response.trim();

                            recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
                            ArrayList<Reply> list = new ArrayList<>();

                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(true);
                            XmlPullParser parser = factory.newPullParser();
                            parser.setInput(new StringReader(xml));

                            String content="", author=""; //내용, 작성자
                            int count = 0;
                            int evenType = parser.getEventType();
                            boolean a = false, b = false;
                            while (evenType != XmlPullParser.END_DOCUMENT){
                                if (evenType == XmlPullParser.START_TAG){
                                    if (parser.getName().equals("content")) a = true;
                                    if (parser.getName().equals("author")) b = true;
                                }else if(evenType == XmlPullParser.TEXT){
                                    if (a){
                                        content = parser.getText();
                                        count++;
                                        a = false;
                                    }
                                    if (b){
                                        author = parser.getText();
                                        count++;
                                        b = false;
                                    }
                                }
                                if (count == 2){
                                    list.add(new Reply(content, author));
                                    count = 0;
                                }
                                evenType = parser.next();
                            }
                            re_size.setText("["+list.size()+"]");
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new ReplyAdapter(list));
                        } catch (XmlPullParserException | IOException e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue2.add(stringRequest2);

        //댓글 쓰기
        re_btn = (Button) findViewById(R.id.reply_button);
        re_ch = (CheckBox) findViewById(R.id.reply_check);
        re_write = (EditText) findViewById(R.id.reply_write);
        //버튼 클릭시 댓글 db에 저장
        re_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //입력 값 변수에 저장
                re_content = re_write.getText().toString();
                if(re_ch.isChecked())
                    re_author = "익명";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://118.67.131.202:8080/androidproject/reply_write.jsp?boardnum="+boardnum+"&author="+re_author+"&content="+re_content;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonresponse = new JSONObject(response);
                                    String jres = jsonresponse.getString("success");
                                    if (jres.equals("success")) {  //글 쓰기 성공
                                        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        //startActivity(intent);
                                        //새로고침
                                        finish();//인텐트 종료
                                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                                        Intent intent = getIntent(); //인텐트
                                        startActivity(intent); //액티비티 열기
                                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                                    }
                                }catch (Exception e){
                                    /*tx.append("\n\n error is:"+e.getMessage());*/
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                requestQueue.add(stringRequest);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}