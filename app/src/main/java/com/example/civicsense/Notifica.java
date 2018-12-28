package com.example.civicsense;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

class Notifica {
    private static final Notifica ourInstance = new Notifica();

    static Notifica getInstance() {
        return ourInstance;
    }

    private Notifica() {
    }

    public void showNotifica(String s, Context context){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder n  = new NotificationCompat.Builder(context)
                .setContentTitle("Arrivato nuovo messaggio!!")
                .setContentText(s)
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setSound(alarmSound);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n.build());
    }

}
