package com.example.lapor_pak;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CLV_DataInfoAPK extends ArrayAdapter<String> {
    private final Activity context;
    private ArrayList<String> vNama;
    private ArrayList<String> vInfo;

    public CLV_DataInfoAPK(Activity context, ArrayList<String> Nama, ArrayList<String> Info)
    {
        super(context, R.layout.list_item_infoapk,Nama);
        this.context        = context;
        this.vNama        = Nama;
        this.vInfo        = Info;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_infoapk, null, true);

        //deklarasi
        TextView nama = rowView.findViewById(R.id.TVnama_pencipta);
        TextView info = rowView.findViewById(R.id.TVinfo_apk);

        //set parameter
        nama.setText(vNama.get(position));
        info.setText(vInfo.get(position));

        return rowView;
    }

}
