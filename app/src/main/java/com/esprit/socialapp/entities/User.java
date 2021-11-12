package com.esprit.socialapp.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String lastName;
    @ColumnInfo
    private String mail;
    @ColumnInfo
    private String pass;



    public User( String mail, String Name, String lastName, String pass) {

        this.mail = mail;
        this.name = name;
        this.lastName = lastName;
        this.pass = pass;
    }

    public User() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + mail + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
