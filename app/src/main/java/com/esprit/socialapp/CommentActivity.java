package com.esprit.socialapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.esprit.socialapp.dao.CommentDao;
import com.esprit.socialapp.database.MyDataBase;
import com.esprit.socialapp.entities.Comment;
import com.esprit.socialapp.entities.Post;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    EditText comment_input;
    ImageButton post_comment_btn;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME ="mypref";
    private static final String KEY_ID ="id";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_NAME ="name";
    private static final String KEY_LAST ="lastName";
    private static final String KEY_PASS ="password";

    TextView nom;
    TextView commentaire;
    RecyclerView comments_list;
    List<Comment> dataList;
    MyDataBase myDatabase;
    LinearLayoutManager linearLayoutManager;
    //MainAdapter mainAdapter;
    CommentaireAdapter commentaireAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        comment_input = findViewById(R.id.comment_input);
        post_comment_btn = findViewById(R.id.post_comment_btn);
        nom = findViewById(R.id.nom);
        commentaire = findViewById(R.id.commentaire);
        comments_list = findViewById(R.id.comments_list);
        myDatabase = myDatabase.getDataBase(getApplicationContext());
        dataList = myDatabase.commentDao().getComments(getIntent().getIntExtra("idPost",0));
        linearLayoutManager = new LinearLayoutManager(this);
        comments_list.setLayoutManager(linearLayoutManager);
        commentaireAdapter = new CommentaireAdapter(CommentActivity.this,dataList);
        comments_list.setAdapter(commentaireAdapter);
        post_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBase myDataBase = MyDataBase.getDataBase(getApplicationContext());
                Comment c = new Comment();
                c.setContenu(comment_input.getText().toString());
                c.setUsername(sharedPreferences.getString(KEY_NAME, null));
                c.setIdPost(getIntent().getIntExtra("idPost",0));
                if(validateInput(c)){
                    CommentDao commentDao = myDataBase.commentDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            commentDao.addComment(c);
                        }
                    }).start();
                }



            }
        });

    }

    private Boolean validateInput(Comment c){
        if(c.getContenu().isEmpty()){
            return false;
        }
        return true;
    }
}