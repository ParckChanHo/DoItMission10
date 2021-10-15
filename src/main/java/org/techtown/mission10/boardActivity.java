package org.techtown.mission10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class boardActivity extends AppCompatActivity {
    // 전체 내용 보여주기!!!
    int boardId;
    String boardNickname,boardDate,boardContent,boardTitle;

    TextView nickName,date;
    EditText content,title;
    board board;
    static RequestQueue requestQueue;

    // 댓글 내용 보여주기!!
    RecyclerView recyclerView;
    childBoardAdapter childBoardAdapter;
    // 전송 버튼
    Button child_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        nickName = (TextView)findViewById(R.id.nickName_edit);
        date = (TextView)findViewById(R.id.date_edit);
        content = (EditText)findViewById(R.id.contents_edit);
        title = (EditText)findViewById(R.id.title_edit);
        board = new board();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.Childboard_recyclerView);
        child_button = (Button)findViewById(R.id.child_button);

        Intent intent=getIntent();
        boardId = intent.getIntExtra("boardId",-1);
        boardNickname = intent.getStringExtra("boardNickname");
        boardDate = intent.getStringExtra("boardDate");
        boardContent = intent.getStringExtra("boardContent");
        boardTitle = intent.getStringExtra("boardTitle");

        System.out.println("boardId: "+boardId);
        System.out.println("boardNickname: "+boardNickname);
        System.out.println("boardDate: "+boardDate);
        System.out.println("boardContent: "+boardContent);
        System.out.println("boardTitle: "+boardTitle);

        nickName.setText("닉네임: "+boardNickname); //board.getBoardNickname()
        date.setText("날짜: "+boardDate); // +board.getBoardDate()
        content.setText(boardContent); //board.getBoardContent()
        title.setText(boardTitle); // board.getBoardTitle()

        callVolley("http://101.101.209.108:8080/AndroidTest/childAllboard.jsp",boardId);
    }

    public void callVolley(String url, final int parentId){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, //POST 방식이다.
                new Response.Listener<String>() { //3번째 매개변수

                    @Override
                    public void onResponse(String response) {

                        try{
                            ArrayList<childBoard> childBoardList = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Allboard"); // Allboard Array
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject Object = jsonArray.getJSONObject(i);

                                int childId = Object.getInt("childId");
                                String childNickname = Object.getString("childNickname");
                                String childDate = Object.getString("childDate");
                                String childContent = Object.getString("childContent");
                                int childAvailable = Object.getInt("childAvailable");
                                int parentId = Object.getInt("parentId");

                                childBoardList.add(new childBoard(childId,childNickname,childDate,childContent,childAvailable,parentId));
                                System.out.println("childId: "+childId+"childNickname: "+childNickname+"childDate: "+childDate+"childContent: "+childContent+
                                        "childAvailable: "+childAvailable+"parentId: "+parentId+"\n");
                                //textView.append(i+"번째 "+"제목: "+boardTitle+" 닉네임: "+boardNickname+" 날짜: "+boardDate+" 내용: "+boardContent + "\n");
                            }

                            childBoardAdapter = new childBoardAdapter(childBoardList,getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(childBoardAdapter);
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
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("parentId",parentId+"");

                return params;
            }
        };

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    } // end callVolley()
}
