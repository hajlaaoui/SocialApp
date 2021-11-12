package com.esprit.socialapp.database;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.esprit.socialapp.dao.CommentDao;
import com.esprit.socialapp.dao.PostDao;
import com.esprit.socialapp.dao.UserDao;
import com.esprit.socialapp.entities.Comment;
import com.esprit.socialapp.entities.Post;
import com.esprit.socialapp.entities.User;

@Database(entities = {User.class, Post.class, Comment.class},version = 4,exportSchema = false)
public abstract class MyDataBase  extends RoomDatabase {
    private static String dbName="user";
    private static MyDataBase instance;




    public static synchronized MyDataBase getDataBase(Context context){
        if (instance == null){
           instance = Room.databaseBuilder(context,MyDataBase.class,dbName)
                   .allowMainThreadQueries()
                   .fallbackToDestructiveMigration()
                   .build();
        }
        return  instance;
    }


    public abstract UserDao userDao();
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();
}
