package com.example.lapor_pak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class History extends AppCompatActivity {

    SwipeRefreshLayout srl_main;
    ArrayList<String> array_jenisLaporan,array_namaLengkap,array_tanngal,array_maps,array_statusLaporan,array_foto;
    ListView listProses;
    ProgressDialog progressDialog;
    TextView nameuser;
    String username;
    private Context mContext;
    Toolbar toolbar;
    Intent toolbar1;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.IDtoolbar);
        nameuser = findViewById(R.id.username);

        //set variable sesuai dengan widget yang digunakan
        listProses = findViewById(R.id.LV);
        srl_main = findViewById(R.id.swipe);
        progressDialog = new ProgressDialog(this);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String user_name = user.get(SessionManager.kunci_username);
        nameuser.setText(Html.fromHtml(user_name));

        toolbar.setNavigationIcon(R.drawable.back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar1 = new Intent(History.this, MainActivity.class);
                startActivity(toolbar1);
                finish();
            }
        });

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srl_main.setRefreshing(false);
            }
        });
        // Scheme colors for animation
        srl_main.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );

        scrollRefresh();
    }

    public void scrollRefresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getData();
            }
        },1000);
    }

    void initializeArray(){
        array_jenisLaporan    = new ArrayList<String>();
        array_namaLengkap     = new ArrayList<String>();
        array_tanngal         = new ArrayList<String>();
        array_maps            = new ArrayList<String>();
        array_statusLaporan   = new ArrayList<String>();
        array_foto            = new ArrayList<String>();

        //clear ini untuk memastikan data empty
        array_jenisLaporan.clear();
        array_namaLengkap.clear();
        array_tanngal.clear();
        array_maps.clear();
        array_statusLaporan.clear();
        array_foto.clear();

        username = nameuser.getText().toString();
    }

    public void getData(){
        initializeArray();
        //URL API
        AndroidNetworking.post("https://tekajeapunya.com/kelompok_9/laporpak/getLaporan.php")
                .addBodyParameter("username",""+username)
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean("status");
                            if (status){
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    array_jenisLaporan.add(jo.getString("jenis_laporan"));
                                    array_namaLengkap.add(jo.getString("nama_lengkap"));
                                    array_tanngal.add(jo.getString("tgl_kejadian"));
                                    array_maps.add(jo.getString("maps"));
                                    array_statusLaporan.add(jo.getString("status_laporan"));
                                    //add this code
                                    array_foto.add(jo.getString("foto"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_History adapter = new CLV_History(History.this,array_jenisLaporan,array_namaLengkap,array_tanngal,array_maps,array_statusLaporan,array_foto);

                                //Set adapter to list
                                listProses.setAdapter(adapter);

                            }else{
                                Toast.makeText(History.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
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