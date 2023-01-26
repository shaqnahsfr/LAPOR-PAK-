package com.example.lapor_pak;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ImageButton profile1, polri, damkar, medis, add_polri, add_damkar, add_medis, riwayat;
    Intent profile2, polri2, damkar2, medis2, riwayat2, tP, tD, tM;
    TextView nama_lengkap;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        profile1 = findViewById(R.id.imageButton);
        polri = findViewById(R.id.imageButton2);
        damkar = findViewById(R.id.imageButton3);
        medis = findViewById(R.id.imageButton4);
        add_polri = findViewById(R.id.imageButton5);
        add_damkar = findViewById(R.id.imageButton6);
        add_medis = findViewById(R.id.imageButton7);
        riwayat = findViewById(R.id.imageButton8);
        nama_lengkap = findViewById(R.id.text3);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name = user.get(SessionManager.kunci_nama);
        nama_lengkap.setText(Html.fromHtml(name));

        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                profile2 = new Intent(MainActivity.this, Pengaturan.class);
                startActivity(profile2);
                finish();
            }
        });

        polri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                tP = new Intent(MainActivity.this, Tentang_polri.class);
                startActivity(tP);
                finish();
            }
        });

        damkar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                tD = new Intent(MainActivity.this, Tentang_damkar.class);
                startActivity(tD);
                finish();
            }
        });

        medis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                tM = new Intent(MainActivity.this, Tentang_medis.class);
                startActivity(tM);
                finish();
            }
        });

        add_polri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                polri2 = new Intent(MainActivity.this, Report_Police.class);
                startActivity(polri2);
                finish();
            }
        });

        add_damkar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                damkar2 = new Intent(MainActivity.this, ReportDamkar.class);
                startActivity(damkar2);
                finish();
            }
        });

        add_medis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                medis2 = new Intent(MainActivity.this, Report_Medis.class);
                startActivity(medis2);
                finish();
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                riwayat2 = new Intent(MainActivity.this, History.class);
                startActivity(riwayat2);
                finish();
            }
        });
    }
}