package org.techtown.mission10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class childBoardAdapter extends RecyclerView.Adapter<childBoardAdapter.childBoardViewHoler> {
    ArrayList<childBoard> childBoardList;
    Context c;
    int position;

    public childBoardAdapter(ArrayList<childBoard> childBoardList, Context c) {
        this.childBoardList = childBoardList;
        this.c = c;
    }

    @NonNull
    @Override
    public childBoardViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_item,parent,false);
        return new childBoardViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull childBoardViewHoler holder, int position) {
        String dotTitle = childBoardList.get(position).getChildContent();
        if(dotTitle.length() > 25){
            dotTitle = dotTitle.substring(0,25) + "...."; // 0~24글자 까지만 내용에 표시해준다!!!
        }
        holder.title.setText(dotTitle); // 댓글 내용이다.

        String dotNickname = childBoardList.get(position).getChildNickname();
        if(dotNickname.length() > 15){
            dotNickname = dotNickname.substring(0,15) + "...."; // 0~14글자 까지만 제목에 표시해준다!!!
        }
        holder.nickName.setText("닉네임: "+dotNickname);

        holder.date.setText("날짜: "+childBoardList.get(position).getChildDate());
    }

    @Override
    public int getItemCount() {
        return childBoardList.size();
    }

    class childBoardViewHoler extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView nickName;
        public TextView date;
        public ImageView delete_img;
        public ImageView btnUpdate;

        public childBoardViewHoler(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            nickName = itemView.findViewById(R.id.nickName);
            date = itemView.findViewById(R.id.date);
            delete_img = itemView.findViewById(R.id.btndel);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 전체 내용 보여주기 + 수정하기
                    position = getAdapterPosition(); // list의 몇 번째 아이템인지를 알게 해준다.
                    Toast.makeText(c,"position: "+position,Toast.LENGTH_SHORT).show();
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition(); // list의 몇 번째 아이템인지를 알게 해준다.

                }
            });
            delete_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* AlertDialog.Builder box=new AlertDialog.Builder(c);
                    box.setMessage("삭제하시겠습니까?");
                    box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    box.setNegativeButton("닫기",null);
                    box.show();*/
                }
            });
        }

    }
}
