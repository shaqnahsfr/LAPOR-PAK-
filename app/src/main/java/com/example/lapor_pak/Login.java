package com.example.lapor_pak;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText ETemail, ETpassword;
    Button login2, signup2, BTNfogetpass;
    Intent pindah1, pindah2;
    ProgressDialog progressDialog;
    String email, password;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        login2 = findViewById(R.id.login);
        signup2 = findViewById(R.id.signup);
        ETemail = findViewById(R.id.editText1);
        ETpassword = findViewById(R.id.editText2);
        BTNfogetpass = findViewById(R.id.katasandi);
        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(getApplicationContext());

        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                email = ETemail.getText().toString();
                password = ETpassword.getText().toString();

                validasiData();
            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                pindah2 = new Intent(Login.this, Register.class);
                startActivity(pindah2);
                finish();
            }
        });

        BTNfogetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
            }
        });
    }

    void validasiData(){
        if (!email.isEmpty() && !password.isEmpty()){
            SignIn();
        }else{
            ETemail.setError("Masukkan Email Anda!");
            ETpassword.setError("Masukkan password Anda!");
        }
    }

    void SignIn(){

        AndroidNetworking.post("https://tekajeapunya.com/kelompok_9/login.php")
                .addBodyParameter("email",""+email)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            String nama_lengkap = response.getString("nama_lengkap");
                            String username = response.getString("username");
                            String no_hp = response.getString("no_hp");
//                            String foto = response.getString("foto");
                            String nik = response.getString("nik");
                            Toast.makeText(Login.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                sessionManager.createSession(nama_lengkap);
                                sessionManager.createSession_username(username);
                                sessionManager.createSession_nohp(no_hp);
//                                sessionManager.createSession_foto(foto);
                                sessionManager.createSession_nik(nik);

                                new AlertDialog.Builder(Login.this)
                                        .setMessage("Selamat Datang di LAPOR-PAK!!")
                                        .setCancelable(false)
                                        .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Login.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .show();
                            } else {
                                new AlertDialog.Builder(Login.this)
                                        .setMessage("Periksa Kembali Email dan Password !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
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
                    public void onError(com.androidnetworking.error.ANError anError) {
                        Log.d("Error Login",""+anError.getErrorBody());
                    }

                });
    }
}