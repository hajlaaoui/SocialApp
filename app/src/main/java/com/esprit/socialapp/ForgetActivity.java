package com.esprit.socialapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esprit.socialapp.dao.UserDao;
import com.esprit.socialapp.database.MyDataBase;

public class ForgetActivity extends AppCompatActivity {

    EditText reset_password_email;
    Button reset_password_email_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        reset_password_email=(EditText) findViewById(R.id.reset_password_email);
        reset_password_email_button = (Button) findViewById(R.id.reset_password_email_button);



        reset_password_email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBase myDataBase = MyDataBase.getDataBase(getApplicationContext());
                UserDao userr = myDataBase.userDao();
                String passs = userr.frgt(reset_password_email.getText().toString()).getPass();
                sendMail(passs);
                Toast.makeText(getApplicationContext(),"Check your email",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgetActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendMail(String password) {
        String mail =reset_password_email.getText().toString();
        String subject = "Forget Password ";
        String message="Your password is : "+password;

        //Send Mail
        JavaMailAPI javaMailAPI =new JavaMailAPI(this,mail,subject,message);
        javaMailAPI.execute();
    }
}
