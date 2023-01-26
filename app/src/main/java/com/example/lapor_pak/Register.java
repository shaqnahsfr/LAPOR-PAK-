package com.example.lapor_pak;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.common.api.Response;

import org.json.JSONObject;

public class Register extends AppCompatActivity{

    Button signup, login;
    ImageButton google, facebook, twitter;
    Intent pindah1, pindah2;
    EditText ETnama_lengkap, ETnik, ETno_hp, ETemail, ETusername, ETpassword, ETcpassword;
    ProgressBar loading;
    ProgressDialog progressDialog;
    String nama_lengkap, nik, no_hp, email, username, password, cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        google = findViewById(R.id.IBgoogle);
        facebook = findViewById(R.id.IBfacebook);
        twitter = findViewById(R.id.IBtwitter);
        ETnama_lengkap = findViewById(R.id.editText1);
        ETnik = findViewById(R.id.editText2);
        ETno_hp = findViewById(R.id.editText3);
        ETemail = findViewById(R.id.editText4);
        ETusername = findViewById(R.id.editText5);
        ETpassword = findViewById(R.id.editText6);
        ETcpassword = findViewById(R.id.editText7);
        loading = findViewById(R.id.progress_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                pindah1 = new Intent(Register.this, Login.class);
                startActivity(pindah1);
                finish();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                nama_lengkap = ETnama_lengkap.getText().toString().trim();
                nik = ETnik.getText().toString().trim();
                no_hp = ETno_hp.getText().toString().trim();
                email = ETemail.getText().toString().trim();
                username = ETusername.getText().toString().trim();
                password = ETpassword.getText().toString().trim();
                cpassword = ETcpassword.getText().toString().trim();

                validasiData();
            }
        });
    }

    void validasiData(){
        if (!nama_lengkap.isEmpty() && !nik.isEmpty() && !no_hp.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !cpassword.isEmpty()){
            if(password.equals(cpassword)){
                Register1();
            }
            else ETcpassword.setError("Konfirmasi password salah");
        }else{
            ETnama_lengkap.setError("Masukkan nama lengkap Anda!");
            ETnik.setError("Masukkan NIK Anda!");
            ETno_hp.setError("Masukkan No HP Anda!");
            ETemail.setError("Masukkan Email Anda!");
            ETusername.setError("Masukkan username Anda!");
            ETpassword.setError("Masukkan password Anda!");
            ETcpassword.setError("Masukkan konfirmasi password Anda!");
        }
    }

    void Register1(){
        AndroidNetworking.post("https://tekajeapunya.com/kelompok_9/register.php")
                .addBodyParameter("nama_lengkap",""+nama_lengkap)
                .addBodyParameter("nik",""+nik)
                .addBodyParameter("no_hp",""+no_hp)
                .addBodyParameter("email",""+email)
                .addBodyParameter("username",""+username)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cekRegister",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            Toast.makeText(Register.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(Register.this)
                                        .setMessage("Selamat Anda berhasil Registrasi!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                Intent intent = new Intent(Register.this, Login.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(Register.this)
                                        .setMessage("Gagal melakukan Registrasi!")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int i) {
                                                Intent intent = new Intent(Register.this, Register.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }
}