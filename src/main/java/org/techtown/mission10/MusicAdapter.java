package org.techtown.mission10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    ArrayList<Music> list;
    Context c;
    SQLiteDatabase db;
    Fragment2 fragment2;
    public static int imageReturn(){
        int value1 = R.drawable.music;
        int value2 = R.drawable.music2;
        int value3 = R.drawable.music3;

        Random random = new Random();
        int randomValue = random.nextInt(3); //0~2 까지의 숫자를 랜덤으로 뽑아준다.
        if(randomValue==0)
            return value1;
        else if(randomValue==1)
            return value2;

        return value3;
    }
    public MusicAdapter(ArrayList<Music> list, Context c) {
        this.c = c;
        this.list = list;
    }

    public OnMyTouchListener listener = null;
    public interface OnMyTouchListener{
        // 직접 리스너-인터페이스를 정의하기
        void onTouch(View v,int pos);
    }
    public void setOnMyTouchListener(OnMyTouchListener listener){
        this.listener = listener;
    }

    public OnMyTouchListenerTwo listenertwo = null;
    public interface OnMyTouchListenerTwo{
        // 직접 리스너-인터페이스를 정의하기
        void onTouch(View v,int pos);
    }
    public void setOnMyTouchListenerTwo( OnMyTouchListenerTwo listenertwo){
        this.listenertwo = listenertwo;
    }

    public OnMyTouchListenerThird listenerthree = null;
    public interface OnMyTouchListenerThird{
        // 직접 리스너-인터페이스를 정의하기
        void onTouch(View v,int pos);
    }
    public void setOnMyTouchListenerThird(OnMyTouchListenerThird listenerthree){
        this.listenerthree = listenerthree;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music item = list.get(position);
        holder.title.setText(item.getTitle());

        //holder.img.setImageResource(value1);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MusicViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView img,img_update,img_delete;
        int random_image;
        public MusicViewHolder(View itemView){
            super(itemView);

            fragment2 = new Fragment2();

            random_image = imageReturn();
            title = (TextView) itemView.findViewById(R.id.item_tx);
            img = (ImageView) itemView.findViewById(R.id.item_img);
            img_update = (ImageView) itemView.findViewById(R.id.img_update);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);

            img.setImageResource(random_image);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null)
                        listener.onTouch(v,position);
                }
            });

            img_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listenertwo != null)
                        listenertwo.onTouch(v,position);
                }
            });

            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listenerthree != null)
                        listenerthree.onTouch(v,position);
                }
            });
        }
    }
}
