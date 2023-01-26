package com.example.lapor_pak;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CLV_DataMedis extends ArrayAdapter<String> {
    private final Activity context;
    private ArrayList<String> vHak;
    private ArrayList<String> vKewajiban;
    private ArrayList<String> vKewenangan;

    public CLV_DataMedis(Activity context, ArrayList<String> Hak, ArrayList<String> Kewajiban, ArrayList<String> Kewenangan)
    {
        super(context, R.layout.list_item_medis,Hak);
        this.context        = context;
        this.vHak           = Hak;
        this.vKewajiban     = Kewajiban;
        this.vKewenangan    = Kewenangan;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item_medis, null, true);

        //deklarasi
        TextView fungsi = rowView.findViewById(R.id.TVHak);
        TextView tugas = rowView.findViewById(R.id.TVKewajiban);
        TextView kewenangan = rowView.findViewById(R.id.TVKewenangan);

        //set parameter
        fungsi.setText(vHak.get(position));
        tugas.setText(vKewajiban.get(position));
        kewenangan.setText(vKewenangan.get(position));

        return rowView;
    }

}
