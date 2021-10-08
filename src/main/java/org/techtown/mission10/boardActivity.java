package org.techtown.mission10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class boardActivity extends AppCompatActivity {
    // 전체 내용 보여주기!!!

    int boardId;
    String boardNickname,boardDate,boardContent,boardTitle;

    TextView nickName,date;
    EditText content,title;
    board board;
    static RequestQueue requestQueue;

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
    }
}
