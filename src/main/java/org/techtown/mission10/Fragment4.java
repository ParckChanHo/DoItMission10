package org.techtown.mission10;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment4 extends Fragment {
    RecyclerView recyclerView;
    public static ArrayList<board> boardList = new ArrayList<>();;
    boardAdapter boardAdapter;

    static RequestQueue requestQueue;
    public static Context c; // context 변수 선언

    // 게시판
    public Fragment4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.fragment4, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.board_recyclerView);
        c = getContext(); // onCreate에서 this 할당
        //textView = (TextView)findViewById(R.id.TextView);
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(c);
        }

        callVolley("http://101.101.209.108:8080/AndroidTest/Allboard.jsp");
        boardAdapter = new boardAdapter(boardList,c);
        boardAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(boardAdapter);

        return v;

    }

    public void callVolley(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수

                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Allboard"); // Allboard Array
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject Object = jsonArray.getJSONObject(i);

                                int boardId = Object.getInt("boardId");
                                String boardTitle = Object.getString("boardTitle");
                                String boardNickname = Object.getString("boardNickname");
                                String boardDate = Object.getString("boardDate");
                                String boardContent = Object.getString("boardContent");
                                int boardAvailable = Object.getInt("boardAvailable");

                                boardList.add(new board(boardId,boardTitle,boardNickname,boardDate,boardContent,boardAvailable));
                                System.out.println("boardId: "+boardId+"boardTitle: "+boardTitle+"boardNickname: "+boardNickname+"boardDate: "+boardDate+
                                        "boardContent: "+boardContent+"boardAvailable: "+boardAvailable+"\n");
                                //textView.append(i+"번째 "+"제목: "+boardTitle+" 닉네임: "+boardNickname+" 날짜: "+boardDate+" 내용: "+boardContent + "\n");
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                }
        ) {
        }; //end new StringRequest
        // Add the request to the RequestQueue.

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    } // end callVolley()

}
