package org.techtown.mission10;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class boardDB {

    static RequestQueue requestQueue;
    board board;
    Context c;

    public boardDB(Context c){
        // Context c ==> MainActivity의 Context를 받아오기 위해서이다.
        this.c =c;
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(c);
        }

    }

    public board getOneBoard(int boardId){
        String url = "http://172.30.1.37:8080/CapstoneDesign/Oneboard.jsp?boardId=3";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(c,response,Toast.LENGTH_LONG).show();
                        board = new board();

                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            int boardId = jsonObject.getInt("boardId");
                            String boardTitle = jsonObject.getString("boardTitle");
                            String boardNickname = jsonObject.getString("boardNickname");
                            String boardDate = jsonObject.getString("boardDate");
                            String boardContent = jsonObject.getString("boardContent");
                            int boardAvailable = jsonObject.getInt("boardAvailable");

                            board.setBoardId(boardId);
                            board.setBoardTitle(boardTitle);
                            board.setBoardNickname(boardNickname);
                            board.setBoardDate(boardDate);
                            board.setBoardContent(boardContent);
                            board.setBoardAvailable(boardAvailable);

                            //Toast.makeText(c,boardId+boardTitle+boardNickname+boardDate+boardContent+boardAvailable,Toast.LENGTH_LONG).show();
                            //board = new board(boardId,boardTitle,boardNickname,boardDate,boardContent,boardAvailable);
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
        return board;
    } // end callVolley()

    public ArrayList<board> callVolley(String url, final ArrayList<board> boardList){
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
        return boardList;
    } // end callVolley()

    public void updateBoard(final int boardId, final String content, final String title){
        String url = "http://101.101.209.108:8080/AndroidTest/UpdateBoard.jsp";

        //Toast.makeText(c,url,Toast.LENGTH_SHORT).show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c,response.trim(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("boardId",boardId+"");
                params.put("content",content);
                params.put("title", title);

                return params;
            }
        }; //end new StringRequest
        // Add the request to the RequestQueue.

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public void deleteBoard(final int boardId){
        String url = "http://101.101.209.108:8080/AndroidTest/DeleteBoard.jsp";

        //Toast.makeText(c,url,Toast.LENGTH_SHORT).show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c,response.trim(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("boardId",boardId+"");

                return params;
            }
        }; //end new StringRequest
        // Add the request to the RequestQueue.

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public void insertBoard(final String title, final String content){
        String url = "http://101.101.209.108:8080/AndroidTest/InsertBoard.jsp";

        //Toast.makeText(c,url,Toast.LENGTH_SHORT).show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, //GET 방식이다.
                new Response.Listener<String>() { //3번째 매개변수
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(c,response.trim(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() { //4번째 매개변수
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getMessage();
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("boardTitle",title);
                params.put("boardContent",content);

                return params;
            }
        }; //end new StringRequest
        // Add the request to the RequestQueue.

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}
