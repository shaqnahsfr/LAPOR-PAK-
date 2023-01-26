package com.example.lapor_pak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Info_Aplikasi extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<String> array_nama, array_info;
    ListView listProses;
    Intent toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_aplikasi);

        toolbar = findViewById(R.id.IDtoolbar);
        listProses = findViewById(R.id.LV);

        toolbar.setNavigationIcon(R.drawable.back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar1 = new Intent(Info_Aplikasi.this, Pengaturan.class);
                startActivity(toolbar1);
                finish();
            }
        });

        getData();
    }

    void initializeArray(){
        array_nama = new ArrayList<String>();
        array_info = new ArrayList<String>();

        //clear
        array_nama.clear();
        array_info.clear();
    }

    public void getData(){
        initializeArray();
        //URL
        AndroidNetworking.get("https://tekajeapunya.com/kelompok_9/laporpak/getData_infoAPK.php")
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Boolean status = response.getBoolean("status");
                            if(status){
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    array_nama.add(jo.getString("pencipta"));
                                    array_info.add(jo.getString("info"));
                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_DataInfoAPK adapter = new CLV_DataInfoAPK(Info_Aplikasi.this,array_nama,array_info);

                                //Set adapter to list
                                listProses.setAdapter(adapter);

                            }else{
                                Toast.makeText(Info_Aplikasi.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}