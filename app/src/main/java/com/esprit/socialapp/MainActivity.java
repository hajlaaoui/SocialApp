package com.esprit.socialapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.socialapp.dao.UserDao;
import com.esprit.socialapp.database.MyDataBase;
import com.esprit.socialapp.entities.User;

public class MainActivity extends AppCompatActivity {
    private Button LoginButton;
    EditText login_email;
    EditText login_password;
    TextView forget_password_link;
    SharedPreferences  sharedPreferences;
    private static final String SHARED_PREF_NAME ="mypref";
    private static final String KEY_ID ="id";
    private static final String KEY_EMAIL ="email";
    private static final String KEY_NAME ="name";
    private static final String KEY_LAST ="lastName";
    private static final String KEY_PASS ="password";
    Button register_account_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_email = findViewById(R.id.register_email);
        login_password = findViewById(R.id.register_password);
        LoginButton = (Button) findViewById(R.id.login_button);
        register_account_link = (Button) findViewById(R.id.register_account_link);

        forget_password_link=(TextView)findViewById(R.id.forget_password_link);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);




        LoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String emailText = login_email.getText().toString();
                String passwordText = login_password.getText().toString();

                if (emailText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill all Fields!",Toast.LENGTH_SHORT).show();
                }else{
                    //Perform Query
                    MyDataBase myDataBase = MyDataBase.getDataBase(getApplicationContext());
                    UserDao userDao = myDataBase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User userEntity = userDao.login(emailText,passwordText);
                            if (userEntity==null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"Invalid Credentials !!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                int id = userEntity.getId();
                                String name = userEntity.getName();
                                String lastName =userEntity.getLastName();
                                String email= userEntity.getMail();
                                String password= userEntity.getPass();


                                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                editor1.putInt(KEY_ID,id);
                                editor1.putString(KEY_EMAIL,email);
                                editor1.putString(KEY_NAME,name);
                                editor1.putString(KEY_LAST,lastName);
                                editor1.putString(KEY_PASS,password);
                                editor1.apply();
                                Intent intent = new Intent(MainActivity.this,NavigationActivity.class);
                                startActivity(intent);



                            }
                        }
                    }).start();
                }

            }
        });

        register_account_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });


        forget_password_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });

    }

}
