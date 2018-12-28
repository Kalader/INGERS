package com.example.civicsense;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.Serializable;
import java.util.UUID;

/*
    - inserimento dei dati in fb e db
*/
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String tipo;
    private String descrizione;
    private String locazione;
    private String img_path;

    private long numero;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setLocazione(String locazione) {
        this.locazione = locazione;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }


    public void InsertFB(){
        Segnalazione segnalazione = new Segnalazione(email,tipo,descrizione,locazione);
        FirebaseDatabase FB = FirebaseDatabase.getInstance();
        DatabaseReference ref = FB.getReference("Segnalazione");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numero = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.child(Long.toString(numero+1)).setValue(segnalazione);
    }

    public void InsertDB(Context context){
        try{
            UploadNotificationConfig config = new UploadNotificationConfig();
            String uploadID = UUID.randomUUID().toString();
            config.setIconForAllStatuses(R.mipmap.ic_launcher);
            config.setTitleForAllStatuses("Avviso");
            new MultipartUploadRequest(context,uploadID,"http://192.168.1.6/CivicSense/InserisciDati.php")
                    .addParameter("email",email)
                    .addParameter("tipo",tipo)
                    .addParameter("descrizione",descrizione)
                    .addParameter("via",locazione)
                    .addFileToUpload(img_path,"image")
                    .setMaxRetries(6)
                    .startUpload();
        } catch (Exception e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Avviso");
            builder.setMessage(e.getMessage());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


}
