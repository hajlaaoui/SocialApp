package com.esprit.socialapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esprit.socialapp.database.MyDataBase;
import com.esprit.socialapp.entities.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireAdapter.ViewHolder> {
    private List<Comment> comments; //= new ArrayList<>();
    private Activity context;
    private MyDataBase database;

    public CommentaireAdapter(Activity context, List<Comment> comments){
        this.comments = comments;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_row_main,parent,false);

        return new CommentaireAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment donnees = comments.get(position);
        database = database.getDataBase(context);
        holder.commentaire.setText(donnees.getContenu());
        holder.nom.setText(donnees.getUsername());

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nom;
        TextView commentaire;

        public  ViewHolder(@NonNull View itemView){
            super(itemView);
            nom = itemView.findViewById(R.id.nom);
            commentaire = itemView.findViewById(R.id.commentaire);
        }
    }
}
