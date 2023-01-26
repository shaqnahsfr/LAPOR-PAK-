package com.example.lapor_pak;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

public class Pengaturan extends AppCompatActivity {

    LinearLayout pengaturanAkun, kontakKami, infoAplikasi;
    ImageButton profile;
    Button logout;
    Toolbar toolbar;
    Intent toolbar1, logout2, info, profile2;
    TextView nama_lengkap;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        toolbar = findViewById(R.id.IDtoolbar);
        profile = findViewById(R.id.imageButton);
        pengaturanAkun = findViewById(R.id.pengaturan_akun);
        kontakKami = findViewById(R.id.kontak);
        infoAplikasi = findViewById(R.id.LLinfo);
        logout = findViewById(R.id.BTNlogout);
        nama_lengkap = findViewById(R.id.text3);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name= user.get(SessionManager.kunci_nama);
        nama_lengkap.setText(Html.fromHtml(name));

        toolbar.setNavigationIcon(R.drawable.back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar1 = new Intent(Pengaturan.this, MainActivity.class);
                startActivity(toolbar1);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                profile2 = new Intent(Pengaturan.this, Profile.class);
                startActivity(profile2);
                finish();
            }
        });

        pengaturanAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });

        kontakKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });

        infoAplikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                info = new Intent(Pengaturan.this, Info_Aplikasi.class);
                startActivity(info);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                sessionManager.logout();

                //logout2 = new Intent(Pengaturan.this, Login.class);
                //startActivity(logout2);
                //finish();
            }
        });
    }
}