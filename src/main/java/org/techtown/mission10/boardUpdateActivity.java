package org.techtown.mission10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class boardUpdateActivity extends AppCompatActivity {
    int boardId;
    String boardNickname,boardDate,boardContent,boardTitle;

    TextView nickName,date;
    EditText content,title;
    FloatingActionButton update_btn;
    board board;
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_update);

        nickName = (TextView)findViewById(R.id.nickName_edit);
        date = (TextView)findViewById(R.id.date_edit);
        content = (EditText)findViewById(R.id.contents_edit);
        title = (EditText)findViewById(R.id.title_edit);
        update_btn = findViewById(R.id.update_btn);
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

        final boardDB helper = new boardDB(getApplicationContext());
        /*board = helper.getOneBoard(boardId);*/

        //Toast.makeText(getApplicationContext(),boardId+boardTitle,Toast.LENGTH_LONG).show();

        nickName.setText("닉네임: "+boardNickname); //board.getBoardNickname()
        date.setText("날짜: "+boardDate); // +board.getBoardDate()
        content.setText(boardContent); //board.getBoardContent()
        title.setText(boardTitle); // board.getBoardTitle()

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box=new AlertDialog.Builder(boardUpdateActivity.this);
                box.setMessage("수정하시겠습니까?");
                box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String updateTitle = title.getText().toString();
                        String updateContent = content.getText().toString();
                        helper.updateBoard(boardId,updateContent,updateTitle);


                        //Toast.makeText(UpdateActivity.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                box.setNegativeButton("닫기",null);
                box.show();
            }
        });
    }
}
