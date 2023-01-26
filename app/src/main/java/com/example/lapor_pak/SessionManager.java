package com.example.lapor_pak;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;

    private static final String pref_name = "crudpref";
    private static final String is_login = "islogin";
    public static final String kunci_nama = "keynama";
    public static final String kunci_username = "keyusername";
    public static final String kunci_nohp = "keynohp";
    public static final String kunci_foto = "keyfoto";
    public static final String kunci_nik = "keynik";

    public SessionManager (Context context){
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession(String nama_lengkap){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_nama, nama_lengkap);
        editor.commit();
    }

    public void createSession_username(String username){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_username, username);
        editor.commit();
    }

    public void createSession_nohp(String no_hp){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_nohp, no_hp);
        editor.commit();
    }

    public void createSession_foto(String foto){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_foto, foto);
        editor.commit();
    }

    public void createSession_nik(String nik){
        editor.putBoolean(is_login, true);
        editor.putString(kunci_nik, nik);
        editor.commit();
    }

    public void logout(){
        editor.clear();
        editor.apply();
        Intent i = new Intent(context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(pref_name, pref.getString(pref_name, null));
        user.put(kunci_nama, pref.getString(kunci_nama, null));
        user.put(kunci_username, pref.getString(kunci_username, null));
        user.put(kunci_nohp, pref.getString(kunci_nohp, null));
        return user;
    }

    public boolean getSPSudahLogin(){
        return pref.getBoolean(is_login, false);
    }

}
