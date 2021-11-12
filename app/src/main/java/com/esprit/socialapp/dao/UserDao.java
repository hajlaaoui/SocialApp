package com.esprit.socialapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.esprit.socialapp.entities.User;

@Dao
public interface UserDao {

    @Insert
    void registerUser (User userEntity);

    @Query("SELECT * From  users where mail=(:email)")
    User frgt(String email);

    @Query("SELECT * From  users where mail=(:email) and pass=(:password)")
    User login(String email,String password);

    @Update
    void updateUser(User user);



}
