package com.esprit.socialapp.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Post")
public class Post {

    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo
    private String date;
    @ColumnInfo
    private String content;
    @ColumnInfo
    private int like;
    @ColumnInfo
    String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", like=" + like +
                ", username='" + username + '\'' +
                '}';
    }
}
