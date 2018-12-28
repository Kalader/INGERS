package com.example.civicsense;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/*
    - salvataggio dati della segnalazione pt2
    - richiamo dei pattern
*/
public class Activity3 extends AppCompatActivity implements LocationListener {

    private Button button;
    private Button buttonBack;
    private EditText description;
    private TextView locationtxt,pathtxt;
    private Button btnpic;
    private ImageView imgTakenPic;
    LocationManager locationManager;

    private static int LOAD_IMAGE_RESULTS = 1;
    private static final int CAM_REQUEST=1313;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Intent intent = getIntent();
        final Model segnalazione = (Model) intent.getSerializableExtra("dati");


        description = (EditText) findViewById(R.id.TextD);

        locationtxt = (TextView) findViewById(R.id.txAddress);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }else{
            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            }
            catch(SecurityException e) {
                e.printStackTrace();
            }
        }

        btnpic = (Button) findViewById(R.id.ImgIns);
        imgTakenPic = (ImageView) findViewById(R.id.imgtaken);
        pathtxt = (TextView)findViewById(R.id.txtpath);
        btnpic.setOnClickListener(new btnTakePhotoClicker());

        button = (Button) findViewById(R.id.Invio);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facade facade = new Facade(description,imgTakenPic,null);
                if(facade.check3()){
                    if(locationtxt.getText().length() == 0){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity3.this);
                        builder.setTitle("Avviso");
                        builder.setMessage("Attiva GPS e/o Internet(Connessione dati o WiFi)");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else{
                        segnalazione.setDescrizione(description.getText().toString());
                        segnalazione.InsertFB();
                        segnalazione.InsertDB(Activity3.this);

                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity3.this);
                    builder.setTitle("Avviso");
                    builder.setMessage("Descrivere il problema e/o inserire foto");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });



        buttonBack = (Button)findViewById(R.id.Indietro);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Prendi i dati dell'immagine presa
            Uri pickedImage = data.getData();
            // Prendi il percorso dell'immagine presa
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Visualizza l'immagine presa nell'applicazione
            imgTakenPic.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            pathtxt.setText(imagePath);
            pathtxt.setVisibility(View.GONE);
            Intent intent = getIntent();
            final Model segnalazione = (Model) intent.getSerializableExtra("dati");
            segnalazione.setImg_path(pathtxt.getText().toString());

            // Chiudi il cursore o altrimenti ti avvisa di un errore
            cursor.close();

        }else if(requestCode == CAM_REQUEST && resultCode == RESULT_OK && data != null){
            // Prendi i dati dell'immagine presa
            Uri pickedImage = data.getData();
            // Prendi il percorso dell'immagine presa
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Visualizza l'immagine presa nell'applicazione
            imgTakenPic.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            pathtxt.setVisibility(View.GONE);
            Intent intent = getIntent();
            final Model segnalazione = (Model) intent.getSerializableExtra("dati");
            segnalazione.setImg_path(pathtxt.getText().toString());


            // Chiudi il cursore o altrimenti ti avvisa di un errore
            cursor.close();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locationtxt.setText(addresses.get(0).getAddressLine(0));

            Intent intent = getIntent();
            final Model segnalazione = (Model) intent.getSerializableExtra("dati");
            segnalazione.setLocazione(locationtxt.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {}

    class btnTakePhotoClicker implements  Button.OnClickListener{

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity3.this);
            builder.setTitle("Avviso");
            builder.setMessage("Dove prendo l'immagine?");
            builder.setNeutralButton("Dalla Camera", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Crea Intent per la fotocamera
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // Inizia la nuova attività considerando come risultato l'img presa dalla fotocamera
                    startActivityForResult(intent,CAM_REQUEST);
                }
            });
            builder.setPositiveButton("Dalla Galleria", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Crea un Intent per la Galleria
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    // Inizia la nuova attività considerando come risultato l'img presa dalla galleria
                    startActivityForResult(i, LOAD_IMAGE_RESULTS);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
