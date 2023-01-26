package com.example.lapor_pak;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CLV_DataPolri extends ArrayAdapter<String> {
    private final Activity context;
    private ArrayList<String> vFungsi;
    private ArrayList<String> vTugas;
    private ArrayList<String> vKewenangan;

    public CLV_DataPolri(Activity context, ArrayList<String> Fungsi, ArrayList<String> Tugas, ArrayList<String> Kewenangan)
    {
        super(context, R.layout.list_item_polri,Fungsi);
        this.context        = context;
        this.vFungsi        = Fungsi;
        this.vTugas         = Tugas;
        this.vKewenangan    = Kewenangan;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_polri, null, true);

        //deklarasi
        TextView fungsi = rowView.findViewById(R.id.TVfungsi);
        TextView tugas = rowView.findViewById(R.id.TVtugas);
        TextView kewenangan = rowView.findViewById(R.id.TVKewenangan);

        //set parameter
        fungsi.setText(vFungsi.get(position));
        tugas.setText(vTugas.get(position));
        kewenangan.setText(vKewenangan.get(position));

        return rowView;
    }

}
