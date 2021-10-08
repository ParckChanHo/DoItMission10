package org.techtown.mission10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class boardInsertActivity extends AppCompatActivity {
    EditText title_insert,content_insert;
    String title,content;
    Button insert_button;
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_insert);

        title_insert = (EditText)findViewById(R.id.title_insert);
        content_insert = (EditText)findViewById(R.id.content_insert);
        insert_button = (Button)findViewById(R.id.insert_button);

        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_insert.getText().toString();
                content = content_insert.getText().toString();

                boardDB helper = new boardDB(getApplicationContext());
                helper.insertBoard(title,content);
               // ((MainActivity)getApplicationContext()).callFragment4();
            }
        });

    }
}
