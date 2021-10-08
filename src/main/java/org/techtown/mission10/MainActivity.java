package org.techtown.mission10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback, AutoPermissionsListener {

    Toolbar toolbar;

    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;

    /*
       1) fragment1 ===> 메인 페이지이다.
       2) fragment2 ===> 추천 채널이다.(1번째 탭)
       3) fragment3 ==> 자기 분석 테스트(2번째 탭)
       4) fragment4 ==> 게시판(3번째 탭)
     */


    ImageView home;
    BottomNavigationView bottomNavigation;
    NavigationView navigationView;
    DrawerLayout drawer;
    Boolean loginOK=false;

    // Toolbar textView
    TextView toolbartextView;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        toolbartextView = (TextView)findViewById(R.id.title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        toolbartextView.setText("마음의 비타민");

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent loginIntent = getIntent();
        loginOK = loginIntent.getBooleanExtra("LoginOk",false);

        if(loginOK){ //로그인이 성공했을 경우
            fragment1 = new Fragment1();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment1).addToBackStack(null).commit();
        }
        else{
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

        home = (ImageView)findViewById(R.id.home_btn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            toolbartextView.setText("마음의 비타민");
            fragment1 = new Fragment1();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment1).addToBackStack(null).commit();

            final Menu menu = bottomNavigation.getMenu();
            menu.findItem(R.id.null_btn).setChecked(true);
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        // fragment2 ==> 추천 채널이다.(1번째 탭)
                        Toast.makeText(getApplicationContext(), "첫 번째 탭 선택됨", Toast.LENGTH_LONG).show();

                        fragment2 = new Fragment2();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment2).addToBackStack(null).commit();
                        toolbartextView.setText("Healing Action");

                        return true;
                    case R.id.tab2:
                        // fragment3 ==> 자기 분석 테스트(2번째 탭)
                        Toast.makeText(getApplicationContext(), "두 번째 탭 선택됨", Toast.LENGTH_LONG).show();

                        fragment3 = new Fragment3();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment3).addToBackStack(null).commit();
                        toolbartextView.setText("자기분석 테스트");

                        return true;
                    case R.id.tab3:
                        // fragment4 ==> 게시판
                        Toast.makeText(getApplicationContext(), "세 번째 탭 선택됨", Toast.LENGTH_LONG).show();

                        /*Intent intent = new Intent(getApplicationContext(),boardActivity.class);
                        startActivity(intent);*/
                        fragment4 = new Fragment4();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment4).addToBackStack(null).commit();
                        toolbartextView.setText("게시판");

                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;
        if (position == 0) {
            curFragment = new Fragment1(); //Main화면
        } else if (position == 1) {
            curFragment = new Fragment2();
        } else if (position == 2) {
            curFragment = new Fragment3();
        }
        else if(position == 3){
            curFragment = new Fragment4();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_0) {
            // 로그아웃
            SharedPreferences auto = getSharedPreferences("login", Activity.MODE_PRIVATE);
            SharedPreferences.Editor autoLogin = auto.edit();
            autoLogin.remove("id");
            autoLogin.remove("passwd");
            autoLogin.remove("nickName");
            autoLogin.clear();
            autoLogin.commit();
            Toast.makeText(context,"로그아웃",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_1) {
            // 검진결과 조회
            Intent intent = new Intent(getApplicationContext(),TestGraph.class);
            startActivity(intent);
        } else if (id == R.id.nav_2) {
            // 상담센터 안내
            onFragmentSelected(2, null);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDenied(int i, String[] strings) {

    }

    @Override
    public void onGranted(int i, String[] strings) {

    }

    public void callFragment2(){
        Fragment2 fragment2 = new Fragment2();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment2).addToBackStack(null).commit();
    }

    public void callFragment4(){
        onFragmentSelected(3, null);

        /*fragment4 = new Fragment4();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment4).addToBackStack(null).commit();*/
    }


}
