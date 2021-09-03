package org.techtown.mission10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class JoinActivity extends AppCompatActivity{
    EditText id,pwd,pwd_check,nickname;
    String str_id,str_pwd,str_pwdCheck,str_nickname;
    Button join,id_check,check_nickname;
    boolean idCheck=false,nickNameCheck=false;

    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id = (EditText)findViewById(R.id.edit_id);
        pwd = (EditText)findViewById(R.id.edit_pwd);
        pwd_check = (EditText)findViewById(R.id.pwd_check);
        nickname = (EditText)findViewById(R.id.edit_nickname);
        join = (Button)findViewById(R.id.signup_btn);
        id_check = (Button)findViewById(R.id.check_id);
        check_nickname = (Button)findViewById(R.id.check_nickname);

        id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_id = id.getText().toString().trim();
                /*try {
                    str_id = URLEncoder.encode(str_id,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                if(str_id.equals("")){
                    Toast.makeText(getApplicationContext(),"아이디를 입력하세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    idCheck(str_id);
            }
        });

        check_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_nickname = nickname.getText().toString().trim();
                if(str_nickname.equals("")){
                    Toast.makeText(getApplicationContext(),"닉네임을 입력하세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    nickNameCheck(str_nickname);
            }
        });

        join.setOnClickListener(new View.OnClickListener() { // 회원가입 버튼을 눌렀을 떄!!!
            @Override
            public void onClick(View v) {
                str_id = id.getText().toString().trim();
                str_pwd = pwd.getText().toString().trim();
                str_pwdCheck = pwd_check.getText().toString().trim();
                str_nickname = nickname.getText().toString().trim();

                if(str_id.equals("")||str_pwd.equals("")||str_nickname.equals("")||str_pwdCheck.equals("")){
                    show();
                    return;
                } //end if문

                if(!str_pwd.equals(str_pwdCheck)){
                    // 비밀번호가 일치하지 않습니다.
                    showPwd();
                    return;
                }


                Toast.makeText(getApplicationContext(),str_id+str_pwd+str_pwdCheck+str_nickname,Toast.LENGTH_LONG).show();
                if(idCheck && nickNameCheck){
                    makeRequest();

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                }
            }
        }); //end join

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void makeRequest(){
        //String url= "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=98aadc5685c38c35f55fc0d41f61e983";
        //String url= "http://172.30.1.16:8080/AndroidTest/jsonPage.jsp";
        String url= "http://101.101.209.108:8080/AndroidTest/JoinDB.jsp?"+
                "userId="+str_id
                +"&userPasswd="+str_pwd
                +"&userNickName="+str_nickname;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        //서버의 응답처리
                        //tv.setText("Response is: "+response);//substring(0,500);
                        Toast.makeText(getApplicationContext(),response.trim(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 처리
                        //tv.setText(error.getMessage());
                        Toast.makeText(getApplicationContext(),"오류 내용: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
        }; //end new StringRequest
        // Add the request to the RequestQueue.
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    } // end makeRequest()

    public void idCheck(String userId){
        //String url= "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=98aadc5685c38c35f55fc0d41f61e983";
        //String url= "http://172.30.1.16:8080/AndroidTest/jsonPage.jsp";
        String url= "http://101.101.209.108:8080/AndroidTest/idCheck.jsp?"+
                "userId="+userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        //서버의 응답처리
                        //tv.setText("Response is: "+response);//substring(0,500);
                        String result=response.trim();
                        if(result.equals("true")){
                            Toast.makeText(getApplicationContext(),"사용할 수 있는 아이디입니다.",Toast.LENGTH_LONG).show();
                            idCheck=true;
                        }
                        else if(result.equals("false")) {
                            Toast.makeText(getApplicationContext(), "사용할 수 없는 아이디입니다.", Toast.LENGTH_LONG).show();
                            id.setText("");
                            return;
                        }
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 처리
                        //tv.setText(error.getMessage());
                        Toast.makeText(getApplicationContext(),"오류 내용: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
        }; //end new StringRequest
        // Add the request to the RequestQueue.
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    } // end makeRequest()

    public void nickNameCheck(String userNickName){
        //String url= "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=98aadc5685c38c35f55fc0d41f61e983";
        //String url= "http://172.30.1.16:8080/AndroidTest/jsonPage.jsp";
        String url= "http://101.101.209.108:8080/AndroidTest/nickNameCheck.jsp?"+
                    "userNickName="+userNickName;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        //서버의 응답처리
                        //tv.setText("Response is: "+response);//substring(0,500);
                        String result=response.trim();
                        if(result.equals("true")){
                            Toast.makeText(getApplicationContext(),"사용할 수 있는 닉네임입니다.",Toast.LENGTH_LONG).show();
                            nickNameCheck=true;
                        }
                        else if(result.equals("false")) {
                            Toast.makeText(getApplicationContext(), "사용할 수 없는 닉네임입니다.", Toast.LENGTH_LONG).show();
                            nickname.setText("");
                            return;
                        }
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 처리
                        //tv.setText(error.getMessage());
                        Toast.makeText(getApplicationContext(),"오류 내용: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
        }; //end new StringRequest
        // Add the request to the RequestQueue.
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    } // end makeRequest()


    void show()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원가입 오류");
        builder.setMessage("빈칸없이 입력해주세요");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        id.setText("");
                        pwd.setText("");
                        nickname.setText("");
                    }
                });
        builder.show();
    }

    void showPwd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("비밀번호 불일치");
        builder.setMessage("비밀번호가 일치하지 않습니다.");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        pwd.setText("");
                        pwd_check.setText("");
                    }
                });
        builder.show();
    }
}
