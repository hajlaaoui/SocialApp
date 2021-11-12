package com.esprit.socialapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.esprit.socialapp.entities.Comment;
import com.esprit.socialapp.entities.Post;

import java.util.List;

@Dao
public interface CommentDao {
    @Insert()
    void addComment(Comment comment);

    @Query("SELECT * FROM Comment WHERE idPost=(:id)")
    List<Comment> getComments(int id);

}
