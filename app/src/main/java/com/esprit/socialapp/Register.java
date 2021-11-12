package com.esprit.socialapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.esprit.socialapp.auth.AuthListener;
import com.esprit.socialapp.auth.GoogleAuth;
import com.esprit.socialapp.dao.UserDao;
import com.esprit.socialapp.database.MyDataBase;
import com.esprit.socialapp.entities.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText register_email,register_name,register_lastName,register_password ;
    Button register_button,btn_google;
    TextView tv_1,tv_2,tv_3,tv_4;
   // private  GoogleAuth googleAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register_email = findViewById(R.id.register_email);
        register_name = findViewById(R.id.register_name);
        register_lastName = findViewById(R.id.register_lastName);
        register_password = findViewById(R.id.register_password);

        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        tv_3 = findViewById(R.id.tv_3);
        tv_4 = findViewById(R.id.tv_4);

        register_button = findViewById(R.id.register_button);
        btn_google = findViewById(R.id.btn_google);

        /************************Google Register*************************/

      /*  btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth();

            }
        });*/





        /************************Normale Register*************************/
        register_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String pass=register_password.getText().toString();
                validatePassword(pass);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating User
                final User userEntity = new User();
                userEntity.setMail(register_email.getText().toString());
                userEntity.setName(register_name.getText().toString());
                userEntity.setLastName(register_lastName.getText().toString());
                userEntity.setPass(register_password.getText().toString());
                if(validateInput(userEntity) && okPass(register_password.getText().toString())==true && okEmail(register_email.getText().toString())==true){
                    //Insert Operation
                    MyDataBase myDataBase = MyDataBase.getDataBase(getApplicationContext());
                    UserDao userDao = myDataBase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Register User
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sendMail();
                                    Toast.makeText(getApplicationContext(),"User Registred",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }
                    }).start();
                }else if(okPass(register_password.getText().toString())!=true || okEmail(register_email.getText().toString())!=true) {
                    Toast.makeText(getApplicationContext(),"Password or Email is wrong !!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Fill all fields !!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void sendMail() {
        String mail =register_email.getText().toString();
        String subject = "Hello Mr "+register_name.getText().toString();
        String message="Your account created successfully " +
                "Email : "+mail+" " +
                "Password : "+register_password.getText().toString();

        //Send Mail
        JavaMailAPI javaMailAPI =new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
    }
    /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuth.activityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            auth();
        }
        super.onStart();
    }*/
    /************************Auth Method*************************/

   /* private  void auth(){
        googleAuth = new GoogleAuth(Register.this, new AuthListener() {

            @Override
            public void OnAuthentication(User user) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                intent.putExtra("user",new Gson().toJson(user));
                startActivity(intent);
                finish();
            }
        } );
    }*/

    private  Boolean validateInput (User userEntity){
        if (userEntity.getName().isEmpty() ||
                userEntity.getLastName().isEmpty() ||
                userEntity.getMail().isEmpty() ||
                userEntity.getPass().isEmpty()){
            return false;
        }
        return true;
    }


    public boolean okEmail(String email) {
        String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";

        Pattern pattern = Pattern.compile(masque);
        Matcher controler = pattern.matcher(email);
        if (controler.matches()){
            return  true;

        }else{
            return false;
        }
    }

    public boolean okPass(String password){
        Pattern upperCase= Pattern.compile("[A-Z]");
        Pattern lowerCase= Pattern.compile("[a-z]");
        Pattern digitCase= Pattern.compile("[0-9]");

        if (!lowerCase.matcher(password).find() || !upperCase.matcher(password).find() || !digitCase.matcher(password).find()
                || password.length()<8){
            return false;


        }else{
            return true;
        }
    }
    public  void  validatePassword (String password){
        Pattern upperCase= Pattern.compile("[A-Z]");
        Pattern lowerCase= Pattern.compile("[a-z]");
        Pattern digitCase= Pattern.compile("[0-9]");

        if (!lowerCase.matcher(password).find()){
            tv_1.setTextColor(Color.RED);

        }else{
            tv_1.setTextColor(Color.GREEN);
        }

        if (!upperCase.matcher(password).find()){
            tv_2.setTextColor(Color.RED);

        }else{
            tv_2.setTextColor(Color.GREEN);
        }

        if (!digitCase.matcher(password).find()){
            tv_3.setTextColor(Color.RED);

        }else{
            tv_3.setTextColor(Color.GREEN);
        }
        if (password.length()<8){
            tv_4.setTextColor(Color.RED);

        }else{
            tv_4.setTextColor(Color.GREEN);
        }

    }




}