package com.esprit.socialapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.esprit.socialapp.dao.UserDao;
import com.esprit.socialapp.database.MyDataBase;
import com.esprit.socialapp.entities.User;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModifuserActivity extends AppCompatActivity {
    TextView update_email,update_name,update_lastName,update_password;
    Button confirm_button;
    SharedPreferences sharedPreferences;
    CircleImageView logout;
    BottomNavigationMenuView bottomNavigationMenuView;
    private static final String SHARED_PREF_NAME ="mypref";
    private static final String KEY_ID ="id";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_NAME ="name";
    private static final String KEY_LAST ="lastName";
    private static final String KEY_PASS ="password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifuser);

        update_email=findViewById(R.id.update_email);
        update_name=findViewById(R.id.update_name);
        update_lastName=findViewById(R.id.update_lastName);
        update_password=findViewById(R.id.update_password);
        confirm_button=findViewById(R.id.confirm_button);
        logout = findViewById(R.id.logout);
bottomNavigationMenuView =findViewById(R.id.bottom_navigation);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        int id = sharedPreferences.getInt(KEY_ID,0);
        String email = sharedPreferences.getString(KEY_EMAIL,null);
        String name = sharedPreferences.getString(KEY_NAME,null);
        String lastName = sharedPreferences.getString(KEY_LAST,null);
        String password = sharedPreferences.getString(KEY_PASS,null);


        update_email.setText(email);
        update_name.setText(name);
        update_lastName.setText(lastName);
        update_password.setText(password);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating User
                final User userEntity = new User();
                userEntity.setId(getIntent().getIntExtra(KEY_ID,0));
                userEntity.setMail(update_email.getText().toString());
                userEntity.setName(update_name.getText().toString());
                userEntity.setLastName(update_lastName.getText().toString());
                userEntity.setPass(update_password.getText().toString());
                if(validateInput(userEntity) ){
                    //Insert Operation
                    MyDataBase myDataBase = MyDataBase.getDataBase(getApplicationContext());
                    UserDao userDao = myDataBase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Register User
                            userDao.updateUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences.Editor editor1 = sharedPreferences.edit();

                                    editor1.putString(KEY_EMAIL,update_email.getText().toString());
                                    editor1.putString(KEY_NAME,update_name.getText().toString());
                                    editor1.putString(KEY_LAST,update_lastName.getText().toString());
                                    editor1.putString(KEY_PASS,update_password.getText().toString());
                                    editor1.apply();

                                    Toast.makeText(getApplicationContext(),"User updated",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ModifuserActivity.this, NavigationActivity.class);
                                    startActivity(intent);


                                }
                            });

                        }
                    }).start();
                }else {
                    Toast.makeText(getApplicationContext(),"Fill all fields !!",Toast.LENGTH_SHORT).show();
                }

            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });


    }
    private  Boolean validateInput (User userEntity){
        if (userEntity.getName().isEmpty() ||
                userEntity.getLastName().isEmpty() ||
                userEntity.getMail().isEmpty() ||
                userEntity.getPass().isEmpty()){
            return false;
        }
        return true;
    }





    private void logoutUser() {
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent =new Intent(ModifuserActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        Toast.makeText(this,"you have been logged out",Toast.LENGTH_SHORT).show();
    }


}