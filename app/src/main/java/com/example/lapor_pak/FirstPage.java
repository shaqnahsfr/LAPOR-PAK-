package com.example.lapor_pak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class FirstPage extends AppCompatActivity {

    Button register, login;
    Intent pindah, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);

                signup = new Intent(FirstPage.this, Register.class);
                startActivity(signup);
                finish();
            }});
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                pindah = new Intent(FirstPage.this, Login.class);
                startActivity(pindah);
                finish();
            }
        });
    }


}