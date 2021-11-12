package com.esprit.socialapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.esprit.socialapp.entities.Post;

import java.util.List;

@Dao
public interface PostDao {

    @Insert()
    void addPost(Post post);

    @Query("SELECT * From Post")
    List<Post> getAllPosts();
}
