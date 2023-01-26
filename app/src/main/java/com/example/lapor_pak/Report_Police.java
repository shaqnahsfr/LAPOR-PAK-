package com.example.lapor_pak;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Report_Police extends AppCompatActivity implements OnMapReadyCallback {

    //image
    ImageButton BTNImage;
    //private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    static final int REQUEST_TAKE_PHOTO = 1;
    public final String APP_TAG = "MyApp";
    Bitmap bitMap = null;
    public String photoFileName = "photo.jpg";
    File photoFile;

    String jenis_laporan, username, nama_lengkap, no_hp, maps, ltd, lng, lokasi_detail, tgl_kejadian, keterangan, status_laporan;

    Toolbar toolbar;
    Intent toolbar1;
    EditText inputKet, tanggal, inputNama, inputUsername, inputTelepon, lokasiDetail, inputLaporan, statusLaporan;
    LinearLayout layoutImage;

    ProgressDialog progressDialog;

    com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton BTNSubmit;

    private GoogleMap mMap;
    private Boolean oke = false;
    TextView LokasiMaps, latitude, longitude;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_police);

        toolbar = findViewById(R.id.toolbar);
        tanggal = findViewById(R.id.inputTanggal);
        BTNImage = findViewById(R.id.imageLaporan);
        BTNSubmit = findViewById(R.id.fab_btn);
        layoutImage = findViewById(R.id.layoutImage);
        LokasiMaps = findViewById(R.id.lokasiMaps);
        latitude = findViewById(R.id.tvLTD);
        longitude = findViewById(R.id.tvLNG);
        inputNama = findViewById(R.id.inputNama);
        inputUsername = findViewById(R.id.inputUsername);
        inputKet = findViewById(R.id.inputKet);
        inputTelepon = findViewById(R.id.inputTelepon);
        lokasiDetail = findViewById(R.id.inputLokasi);
        inputLaporan = findViewById(R.id.inputLaporan);
        statusLaporan = findViewById(R.id.statusLaporan);
        progressDialog = new ProgressDialog(this);


        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        String name= user.get(SessionManager.kunci_nama);
        String user_name = user.get(SessionManager.kunci_username);
        String nohp= user.get(SessionManager.kunci_nohp);
        inputNama.setText(Html.fromHtml(name));
        inputUsername.setText(Html.fromHtml(user_name));
        inputTelepon.setText(Html.fromHtml(nohp));

        Geocoder geocoder = new Geocoder(Report_Police.this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(Report_Police.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Report_Police.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions((Activity) Report_Police.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, new LocationListener() {
            List<Address> addressList = null;
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addressList != null){
                        Address returnAdd = addressList.get(0);
                        StringBuilder stringBuilder = new StringBuilder("");
                        for (int i=0; i<returnAdd.getMaxAddressLineIndex(); i++){
                            stringBuilder.append(returnAdd.getAddressLine(i)).append("\n");
                        }
                        Log.w("My location Address", stringBuilder.toString());
                    }else {
                        Log.w("My Locatioan Address", "no address");
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
                if (oke) {
                    String addressLines = addressList.get(0).getAddressLine(0);

                    LatLng lokasisekarang = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(lokasisekarang).title("Lokasi Sekarang"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasisekarang));
                    latitude.setText(String.valueOf(location.getLatitude()));
                    longitude.setText(String.valueOf(location.getLongitude()));
                    LokasiMaps.setText(addressLines);

                }
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toolbar1 = new Intent(Report_Police.this, MainActivity.class);
                startActivity(toolbar1);
                finish();
            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        String formattedData = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        tanggal.setText(formattedData);

        BTNImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bitMap != null) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Report_Police.this);
                    alertDialogBuilder.setMessage("Do yo want to take photo again?");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();
                            //call fuction of TakePhoto
                            TakePhoto();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                } else {

                    TakePhoto();
                }
            }
        });

        BTNSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                jenis_laporan       = inputKet.getText().toString();
                username            = inputUsername.getText().toString();
                nama_lengkap        = inputNama.getText().toString();
                no_hp               = inputTelepon.getText().toString();
                maps                = LokasiMaps.getText().toString();
                ltd                 = latitude.getText().toString();
                lng                 = longitude.getText().toString();
                lokasi_detail       = lokasiDetail.getText().toString();
                tgl_kejadian        = tanggal.getText().toString();
                keterangan          = inputLaporan.getText().toString();
                status_laporan      = statusLaporan.getText().toString();

                if (bitMap == null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(Report_Police.this);
                    builder.setMessage("Mohon masukkan foto");
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    progressDialog.dismiss();


                }
                else {

                    validasiData();

                }

            }
        });

    }

    void validasiData(){
        if (!jenis_laporan.isEmpty() && !username.isEmpty() && !nama_lengkap.isEmpty() && !no_hp.isEmpty() && !maps.isEmpty() && !ltd.isEmpty() && !lng.isEmpty() && !tgl_kejadian.isEmpty() && !keterangan.isEmpty()){
            kirimData();
        }else{
            LokasiMaps.setError("Masukkan Lokasi Maps!");
            inputLaporan.setError("Masukkan Keterangan Laporan!");
        }
    }

    // taking image
    public  void TakePhoto(){
                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Create a File reference to access to future access
                    photoFile = getPhotoFileUri(photoFileName);
                    //startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    String authorities = getPackageName() + ".fileprovider";
                    Uri fileProvider = FileProvider.getUriForFile(Report_Police.this, authorities, photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);

                    }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //set photo size
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                // by this point we have the camera photo on disk
                //Bitmap takenImage = BitmapFactory.decodeFile(String.valueOf(photoFile));
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                bitMap = decodeSampledBitmapFromFile(String.valueOf(photoFile), 1000, 700);
                BTNImage.setImageBitmap(bitMap);
            } else { // Result was a failure
                Toast.makeText(Report_Police.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //get data photo
    public File getPhotoFileUri(String fileName)  {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use getExternalFilesDir on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

            return file;

        }
        return null;
    }

    //set photo
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    // get encode image to minimize image
    public String getStringImage(Bitmap bmp){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    void kirimData(){
        progressDialog.setMessage("Mengirim Laporan...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String foto = getStringImage(bitMap);
        AndroidNetworking.post("https://tekajeapunya.com/kelompok_9/addLaporan.php")
                .addBodyParameter("jenis_laporan",""+jenis_laporan)
                .addBodyParameter("username",""+username)
                .addBodyParameter("nama_lengkap",""+nama_lengkap)
                .addBodyParameter("no_hp",""+no_hp)
                .addBodyParameter("maps",""+maps)
                .addBodyParameter("ltd",""+ltd)
                .addBodyParameter("lng",""+lng)
                .addBodyParameter("lokasi_detail",""+lokasi_detail)
                .addBodyParameter("tgl_kejadian",""+tgl_kejadian)
                .addBodyParameter("keterangan",""+keterangan)
                .addBodyParameter("status_laporan",""+status_laporan)
                .addBodyParameter("foto",""+foto)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan   = response.getString("result");
                            Toast.makeText(Report_Police.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(Report_Police.this)
                                        .setMessage("Laporan dikirim!")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(Report_Police.this, MainActivity.class);
                                                startActivity(i);
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(Report_Police.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(Report_Police.this, MainActivity.class);
                                                startActivity(i);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        oke = true;

        // Add a marker in Sydney and move the camera

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


}