package org.techtown.mission10;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    Button board_write;
    SwipeRefreshLayout swipe;

    public Fragment4() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment4, container, false);

        //글 쓰기 화면이동
        board_write=(Button) v.findViewById(R.id.write);
        board_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), board_write.class);
                startActivity(intent);
            }
        });


        swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe);

        requestQueue = Volley.newRequestQueue(getActivity());

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //게시글 보여주기
                String url = "http://118.67.131.202:8080/androidproject/board.jsp";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    String xml = response.trim();

                                    //try안에 view와 list 생성 후 사용
                                    recyclerView = (RecyclerView)v.findViewById(R.id.RecyclerView);
                                    ArrayList<Table> list = new ArrayList<>();

                                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                                    factory.setNamespaceAware(true);
                                    XmlPullParser parser = factory.newPullParser();
                                    parser.setInput(new StringReader(xml));

                                    //Toast.makeText(getActivity(), xml, Toast.LENGTH_SHORT).show();

                                    String title="", content="", author="", boardnum=""; //제목, 내용, 작성자, 게시글번호
                                    int count = 0;
                                    int evenType = parser.getEventType();
                                    boolean a = false, b = false, c = false, d = false;
                                    while (evenType != XmlPullParser.END_DOCUMENT){
                                        if (evenType == XmlPullParser.START_TAG){
                                            if (parser.getName().equals("title")) a = true;
                                            if (parser.getName().equals("content")) b = true;
                                            if (parser.getName().equals("author")) c = true;
                                            if (parser.getName().equals("boardnum")) d = true;
                                        }else if(evenType == XmlPullParser.TEXT){
                                            if (a){
                                                title = parser.getText();
                                                count++;
                                                a = false;
                                            }
                                            if (b){
                                                content = parser.getText();
                                                count++;
                                                b = false;
                                            }
                                            if (c){
                                                author = parser.getText();
                                                count++;
                                                c = false;
                                            }
                                            if (d){
                                                boardnum = parser.getText();
                                                count++;
                                                d = false;
                                            }
                                        }
                                        if (count == 4){
                                            list.add(new Table(title, content, author, boardnum));
                                            count = 0;
                                        }
                                        evenType = parser.next();
                                    }
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerView.setAdapter(new TableAdapter(list, getContext()));
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

                swipe.setRefreshing(false);
            }
        });

        //게시글 보여주기
        String url = "http://118.67.131.202:8080/androidproject/board.jsp";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String xml = response.trim();

                            //try안에 view와 list 생성 후 사용
                            recyclerView = (RecyclerView)v.findViewById(R.id.RecyclerView);
                            ArrayList<Table> list = new ArrayList<>();

                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(true);
                            XmlPullParser parser = factory.newPullParser();
                            parser.setInput(new StringReader(xml));

                            //Toast.makeText(getActivity(), xml, Toast.LENGTH_SHORT).show();

                            String title="", content="", author="", boardnum=""; //제목, 내용, 작성자, 게시글번호
                            int count = 0;
                            int evenType = parser.getEventType();
                            boolean a = false, b = false, c = false, d = false;
                            while (evenType != XmlPullParser.END_DOCUMENT){
                                if (evenType == XmlPullParser.START_TAG){
                                    if (parser.getName().equals("title")) a = true;
                                    if (parser.getName().equals("content")) b = true;
                                    if (parser.getName().equals("author")) c = true;
                                    if (parser.getName().equals("boardnum")) d = true;
                                }else if(evenType == XmlPullParser.TEXT){
                                    if (a){
                                        title = parser.getText();
                                        count++;
                                        a = false;
                                    }
                                    if (b){
                                        content = parser.getText();
                                        count++;
                                        b = false;
                                    }
                                    if (c){
                                        author = parser.getText();
                                        count++;
                                        c = false;
                                    }
                                    if (d){
                                        boardnum = parser.getText();
                                        count++;
                                        d = false;
                                    }
                                }
                                if (count == 4){
                                    list.add(new Table(title, content, author, boardnum));
                                    count = 0;
                                }
                                evenType = parser.next();
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(new TableAdapter(list, getContext()));
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

        return v;
    }

}


