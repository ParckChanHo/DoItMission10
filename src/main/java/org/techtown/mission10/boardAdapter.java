package org.techtown.mission10;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class boardAdapter extends RecyclerView.Adapter<boardAdapter.boardViewHoler> {
    ArrayList<board> boardList;
    Context c; // Fragment4의 Context를 받아오기 위해서이다.
    int position;

    public boardAdapter(ArrayList<board> boardList,Context c){
        this.boardList = boardList;
        this.c = c;
    }

    public boardAdapter(){};

    @NonNull
    @Override
    public boardViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item,parent,false);
        return new boardViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull boardViewHoler holder, int position) {
        // 데이터를 어떻게 레이아웃에 집어 넣어줄지를 결정한다.
        //String toCut = "Hello developer. please check this message for long length. max length to 50 characters.";
        // if(toCut.length() > 50){toCut = toCut.substring(0, 50) + '…';}
        // textView.setText(toCut);
        String dotTitle = boardList.get(position).getBoardTitle();
        if(dotTitle.length() > 25){
            dotTitle = dotTitle.substring(0,25) + "...."; // 0~24글자 까지만 제목에 표시해준다!!!
        }
        holder.title.setText("제목: "+dotTitle);

        String dotNickname = boardList.get(position).getBoardNickname();
        if(dotNickname.length() > 15){
            dotNickname = dotNickname.substring(0,15) + "...."; // 0~14글자 까지만 제목에 표시해준다!!!
        }
        holder.nickName.setText("닉네임: "+dotNickname);

        holder.date.setText("날짜: "+boardList.get(position).getBoardDate());
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    class boardViewHoler extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView nickName;
        public TextView date;
        public ImageView delete_img;

        public boardViewHoler(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            nickName = itemView.findViewById(R.id.nickName);
            date = itemView.findViewById(R.id.date);
            delete_img = itemView.findViewById(R.id.btndel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 전체 내용 보여주기 + 수정하기
                    position = getAdapterPosition(); // list의 몇 번째 아이템인지를 알게 해준다.

                    Intent intent = new Intent(c, boardUpdateActivity.class);
                    intent.putExtra("boardId", boardList.get(position).getBoardId());
                    intent.putExtra("boardNickname", boardList.get(position).getBoardNickname());
                    intent.putExtra("boardDate", boardList.get(position).getBoardDate());
                    intent.putExtra("boardContent", boardList.get(position).getBoardContent());
                    intent.putExtra("boardTitle", boardList.get(position).getBoardTitle());
                    c.startActivity(intent);
                }
            });

            delete_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition(); // list의 몇 번째 아이템인지를 알게 해준다.
                    int boardId = boardList.get(position).getBoardId();

                    boardDB helper = new boardDB(c);
                    helper.deleteBoard(boardId);
                }
            });
        } // end boardViewHolder 클래스
    }
}

