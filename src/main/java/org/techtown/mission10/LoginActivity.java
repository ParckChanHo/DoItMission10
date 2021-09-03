package org.techtown.mission10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class LoginActivity extends AppCompatActivity {
    EditText id,passwd;
    Button join,login;
    String str_id,str_passwd;
    String result;
    static RequestQueue requestQueue;

    CheckBox auto_CheckBox;
    // 자동 로그인 체크 박스,아이디 및 비밀번호 기억하기
    Boolean isChecked;
    String auto_id,auto_passwd,auto_nickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText)findViewById(R.id.editId);
        passwd = (EditText)findViewById(R.id.editPwd);
        join = (Button)findViewById(R.id.signup_btn);
        login = (Button)findViewById(R.id.login_btn);
        auto_CheckBox = (CheckBox)findViewById(R.id.auto_checkBox);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        // "login"이 SharedPreferences의 이름이다.
        // MODE_PRIVATE은 자기 앱에서만 사용하도록 설정하는 기본 값이다.
        SharedPreferences auto = getSharedPreferences("login", Activity.MODE_PRIVATE);
        isChecked = auto.getBoolean("isChecked",false);
        auto_CheckBox.setChecked(isChecked);

        if(isChecked){ // 체크박스를 체크했다면
            auto_id = auto.getString("id","");
            auto_passwd = auto.getString("passwd","");
            auto_nickName = auto.getString("nickName","");

            if(!(auto_id.equals("") || auto_passwd.equals(""))){ // id,passwd 모두 공백이 아니다.
                str_id = auto_id;
                str_passwd = auto_passwd;
                Toast.makeText(this, auto_nickName +"님 자동로그인 입니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("LoginOk",true);
                startActivity(intent);
            }
        }

        login.setOnClickListener(new View.OnClickListener() { // 로그인 버튼을 클릭했을 떄!!!
            @Override
            public void onClick(View v) {
                str_id = id.getText().toString().trim();
                str_passwd = passwd.getText().toString().trim();

                //Toast.makeText(getContext(),"아이디: "+str_id+"비밀번호: "+str_passwd,Toast.LENGTH_LONG).show();
                makeRequest();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //회원가입 글자를 클릭했을 떄!!!

            Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
            startActivity(intent);
            }
        });
    }

    public void makeRequest(){
        String url= "http://101.101.209.108:8080/AndroidTest/loginDB.jsp?"+
                "userId="+str_id
                +"&userPasswd="+str_passwd;

        //String url="http://172.30.1.46:8080/AndroidTest/loginDB.jsp?userId=박찬호&userPasswd=1004";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        //서버의 응답처리
                        //tv.setText("Response is: "+response);//substring(0,500);
                        try{
                            response=response.trim();
                            result = response;

                            JSONObject jsonResponse = new JSONObject(result);
                            String loginResult = jsonResponse.getString("login");

                            if(loginResult.equals("1")){
                                // 닉네임이 무엇인지 얻어와야 한다.!!!
                                String nickName_auto = jsonResponse.getString("userNickName");

                                SharedPreferences auto = getSharedPreferences("login", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLogin = auto.edit();
                                autoLogin.putBoolean("isChecked",auto_CheckBox.isChecked());
                                autoLogin.putString("id",str_id);
                                autoLogin.putString("passwd",str_passwd);
                                autoLogin.putString("nickName",nickName_auto);
                                autoLogin.commit();
                                Toast.makeText(getApplicationContext(),nickName_auto+"님 환영합니다.",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("LoginOk",true);
                                startActivity(intent);
                            }

                            else if(loginResult.equals("0")){
                                Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                            }

                            else if(loginResult.equals("2")){
                                Toast.makeText(getApplicationContext(),"아이디가 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    } // onResponse()
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
}
