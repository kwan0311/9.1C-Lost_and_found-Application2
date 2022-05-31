package com.example.lostandfoundapplication2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.MyViewHolder> {
    private Context context;
    private ArrayList good_id, post_type, description, user_name, Phone_no_, date, location ;


    Recycler_Adapter(Context context, ArrayList Good_Id,ArrayList username, ArrayList Post_type, ArrayList Phone, ArrayList Description, ArrayList Date, ArrayList Location_)
    {

        this.context = context;
        this.good_id = Good_Id;
        this.post_type = Post_type;
        this.description = Description;
        this.user_name = username;
        this.Phone_no_ = Phone;
        this.date = Date;
        this.location = Location_;
    }



    @NonNull
    @Override
    public Recycler_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_cardview, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recycler_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        holder.posttype_txt.setText(String.valueOf(post_type.get(position)) );
        holder.description_txt.setText(String.valueOf(description.get(position)) );
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DeleteActivity.class);

                intent.putExtra("good_id", String.valueOf(good_id.get(position)));
                intent.putExtra("name", String.valueOf(user_name.get(position)));
                intent.putExtra("post_type", String.valueOf(post_type.get(position)));
                intent.putExtra("phone", String.valueOf(Phone_no_.get(position)));
                intent.putExtra("description", String.valueOf(description.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));
                intent.putExtra("location", String.valueOf(location.get(position)));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return post_type.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView posttype_txt, description_txt;

        LinearLayout cardview;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            posttype_txt = itemView.findViewById(R.id.post_type_print);
            description_txt = itemView.findViewById(R.id.description_print);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }

}
