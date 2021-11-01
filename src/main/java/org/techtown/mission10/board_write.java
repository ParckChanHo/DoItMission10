package org.techtown.mission10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class board_write extends AppCompatActivity {
    EditText ed_title, ed_content;
    CheckBox check;
    Button btn;
    String title, content, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        //툴바 설정
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); //왼쪽 버튼 사용 여부

        ed_title = findViewById(R.id.write_title);
        ed_content = findViewById(R.id.write_content);
        btn = findViewById(R.id.write_button);
        check = findViewById(R.id.checkBox);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = ed_title.getText().toString();
                content = ed_content.getText().toString();

                if (check.isChecked())
                    author = "익명";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://115.85.181.116:8080/android/webapp/write.jsp?title="+title+"&author="+author+"&content="+content;

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
                                        finish();
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
