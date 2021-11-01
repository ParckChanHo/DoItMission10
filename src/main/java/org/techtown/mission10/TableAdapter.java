package org.techtown.mission10;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder>{
    ArrayList<Table> list;
    Context c;

    public TableAdapter(ArrayList<Table> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item,parent,false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        holder.author.setText(list.get(position).getAuthor());
        //holder.boardnum.setText(list.get(position).getBoardnum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TableViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        TextView author;
        //TextView boardnum;

        public TableViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            author = (TextView) itemView.findViewById(R.id.author);

            title.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    //Toast.makeText(c, list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(),board_notice.class);
                    intent.putExtra("boardnum", list.get(position).getBoardnum());
                    c.startActivity(intent);
                }
            });
        }
    }
}

