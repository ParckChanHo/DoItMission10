package org.techtown.mission10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.pedro.library.AutoPermissions;

import java.util.ArrayList;

public class CallcenterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;

    ImageView home;

    NavigationView navigationView;
    DrawerLayout drawer;
    // Toolbar textView
    TextView toolbartextView;
    public static Context context;

    // Callcenter
    RecyclerView recyclerView;
    CallcenterAdapter callcenterAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callcenter);

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

        home = (ImageView)findViewById(R.id.home_btn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            toolbartextView.setText("마음의 비타민");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("LoginOk",true);
            startActivity(intent);

            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.callCenterRecyclerView);
        final ArrayList<Callcenter> list = new ArrayList<>();
        // 데이터 집어넣기
        list.add(new Callcenter("국립정신건강센터","02-2204-0114","http://www.ncmh.go.kr/ncmh/main.do",R.drawable.ic_action_phone_start,R.drawable.ic_action_home));
        list.add(new Callcenter("건강가정지원센터","1577-9337","https://www.familynet.or.kr/web/index.do",R.drawable.ic_action_phone_start,R.drawable.ic_action_home));
        list.add(new Callcenter("청소년사이버상담센터","1388","https://www.cyber1388.kr:447/",R.drawable.ic_action_phone_start,R.drawable.ic_action_home));
        list.add(new Callcenter("보건복지상담센터","129","http://www.129.go.kr/",R.drawable.ic_action_phone_start,R.drawable.ic_action_home));
        list.add(new Callcenter("자살예방상담전화","1393","https://kfsp.org/",R.drawable.ic_action_phone_start,R.drawable.ic_action_home));
        list.add(new Callcenter("한국생명의전화","1588-9191","https://www.lifeline.or.kr/index.php",R.drawable.ic_action_phone_start,R.drawable.ic_action_home));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        callcenterAdapter = new CallcenterAdapter(list,getApplicationContext());
        recyclerView.setAdapter(callcenterAdapter);


        // home 아이콘이다.
        callcenterAdapter.setCallcenterListener(new CallcenterAdapter.CallcenterListener() {
            @Override
            public void onTouch(View v, int pos) {
                String url = list.get(pos).getCallCenter_address();
                Uri uri = Uri.parse(url);
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    uri = Uri.parse("http://" + url);
                }

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        // 전화번호 아이콘이다.
        callcenterAdapter.setCallcenterListenerTwo(new CallcenterAdapter.CallcenterListenerTwo() {
            @Override
            public void onTouch(View v, int pos) {
                String str_tel = list.get(pos).getCallCenter_tel();

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+str_tel));
                startActivity(intent);
            }
        });

        /*for(int i=0; i<list.size();i++){
            // RecyclerView에 제대로 들어간것이 맞나?
            Callcenter callcenter = list.get(i);
            String title = callcenter.getCallCenter_title();
            String tel = callcenter.getCallCenter_tel();
            String address = callcenter.getCallCenter_address();
            int phone = callcenter.getTel_icon();
            int home = callcenter.getHome_icon();

            System.out.println("title: "+title+"tel: "+tel+"address: "+address+"phone: "+phone+"home: "+home+"\n");
        }*/
    } // end Oncreate()


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
            Intent intent = new Intent(getApplicationContext(),CallcenterActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
