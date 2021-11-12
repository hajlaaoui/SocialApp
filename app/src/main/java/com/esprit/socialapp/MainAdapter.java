package com.esprit.socialapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.socialapp.entities.Comment;
import com.esprit.socialapp.entities.Post;
import com.esprit.socialapp.database.MyDataBase;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private List<Post> posts;
    //private List<Comment> comments = new ArrayList<>();
    private Activity context;
    private MyDataBase database;
    //private int i;

    public MainAdapter(Activity context, List<Post> posts){
        this.context = context;
        this.posts = posts;
        notifyDataSetChanged();
    }

   /* public MainAdapter(Activity context, List<Comment> comments, int i){
        this.context = context;
        this.comments = comments;
        this.i = i;
        notifyDataSetChanged();
    }*/
    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post data = posts.get(position);
        database = database.getDataBase(context);
        holder.post_description.setText(data.getContent());
        holder.user_name.setText(data.getUsername());
        int id = data.getId();
        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, CommentActivity.class).putExtra("idPost", id));

            }
        });
/*
        Comment donnees = comments.get(position);
        database = database.getDataBase(context);
        holder.commentaire.setText(donnees.getContenu());
        holder.nom.setText(donnees.getUsername());
       holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //context.startActivity(new Intent(context, *.class).putExtra("id",id);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView user_name;
        TextView post_description;
        TextView likes;
        ImageButton comment_button;
        /*TextView nom;
        TextView commentaire;*/


        public  ViewHolder(@NonNull View itemView){
            super(itemView);
            //rootView = itemView;
            user_name = itemView.findViewById(R.id.user_name);
            post_description = itemView.findViewById(R.id.post_description);
            likes = itemView.findViewById(R.id.likes);
            comment_button = itemView.findViewById(R.id.comment_button);
            /*nom = itemView.findViewById(R.id.nom);
            commentaire = itemView.findViewById(R.id.commentaire);
           /* comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Post p = posts.get(holder.getAdapterPosition());
                    int idPost = p.getId();

                    Intent intent = new Intent(comment_button.getContext(),CommentActivity.class).putExtra("idPost", idPost);
                    comment_button.getContext().startActivity(intent);
                    //(MainAdapter.class, CommentActivity.class).putExtra("idPost", idPost);
                    //startActivity(intent);

                }
            });*/

           // bt = itemView.findViewById(R.id.bt);
        }
    }
}
