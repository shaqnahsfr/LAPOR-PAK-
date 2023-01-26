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

public class Tentang_polri extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<String> array_fungsi, array_tugas, array_kewenangan;
    ListView listProses;
    Intent toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_polri);

        toolbar = findViewById(R.id.IDtoolbar);
        listProses = findViewById(R.id.LV);

        toolbar.setNavigationIcon(R.drawable.back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar1 = new Intent(Tentang_polri.this, MainActivity.class);
                startActivity(toolbar1);
                finish();
            }
        });

        getData();
    }

    void initializeArray(){
        array_fungsi = new ArrayList<String>();
        array_tugas = new ArrayList<String>();
        array_kewenangan = new ArrayList<String>();

        //clear
        array_fungsi.clear();
        array_tugas.clear();
        array_kewenangan.clear();
    }

    public void getData(){
        initializeArray();
        //URL
        AndroidNetworking.get("https://tekajeapunya.com/kelompok_9/laporpak/getData_polri.php")
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

                                    array_fungsi.add(jo.getString("fungsi"));
                                    array_tugas.add(jo.getString("tugas"));
                                    array_kewenangan.add(jo.getString("kewenangan"));
                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_DataPolri adapter = new CLV_DataPolri(Tentang_polri.this,array_fungsi,array_tugas,array_kewenangan);

                                //Set adapter to list
                                listProses.setAdapter(adapter);

                            }else{
                                Toast.makeText(Tentang_polri.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
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