package com.turkcell.gelecegiyazanlar.android.wear;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private NotificationManager myNotificationManager;
    private int notId1 = 111;
    private int notId2 = 222;
    private int notId3 = 333;
    private int mesaj1counter = 0;
    private int mesaj2counter = 0;
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.notificationOne);
        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                displayNotificationOne();

            }

        });

        Button b2 = (Button) findViewById(R.id.notificationTwo);
        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                displayNotificationTwo();

            }

        });

        Button b3 = (Button) findViewById(R.id.notificationThree);
        b3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                displayNotificationThree();

            }

        });

        Button b4 = (Button) findViewById(R.id.notificationFour);
        b4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                displayNotificationFour();

            }

        });
    }
        private void displayNotificationOne()
        {

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setContentTitle("Yeni mesaj");
            mBuilder.setContentText("Geleceği yazanlar");
            mBuilder.setTicker("1.tip bildirim!");

            mBuilder.setSmallIcon(R.drawable.mesaj_logo);
            // Her yeni bildirim geldiğinde, bildirim sayısını 1 artırır.

            mBuilder.setNumber(++mesaj1counter);
            mBuilder.setAutoCancel(true);
            myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Bildirim objesini sisteme aktarır.
            myNotificationManager.notify(notId1, mBuilder.build());

        }

        private void displayNotificationTwo()
        {

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setContentTitle("Yeni mesajınız var");
            mBuilder.setContentText("Geleceği Yazanlar");
            mBuilder.setTicker("2.tip bildirim !");
            mBuilder.setSmallIcon(R.drawable.mesaj_logo);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            String[] events = new String[3];
            events[0] = new String("1) Mesaj detayları görünüyor");
            events[1] = new String("2) Big View Notification");
            events[2] = new String("3) Geleceği Yazanlar");

            inboxStyle.setBigContentTitle("Detaylar:");

            for (int i=0; i < events.length; i++) {
                inboxStyle.addLine(events[i]);
            }

            mBuilder.setStyle(inboxStyle);

            // Her yeni bildirim geldiğinde, bildirim sayısını 1 artırır.
            mBuilder.setNumber(++mesaj2counter);

            // Bildirim objesini sisteme aktarır.
            mBuilder.setAutoCancel(true);
            myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            myNotificationManager.notify(notId2, mBuilder.build());

        }

        private void displayNotificationThree()
        {

            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode("41.071607,29.0234621,13"));

            mapIntent.setData(geoUri);
            PendingIntent mapPendingIntent =
                    PendingIntent.getActivity(this, 0, mapIntent, 0);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.main_image)
                            .setContentTitle("Google Maps")
                            .setContentText("Harita Görüntüleme")
                            .addAction(R.drawable.map_logo,
                                    getString(R.string.map), mapPendingIntent);

            myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            myNotificationManager.notify(notId3, notificationBuilder.build());


        }

        private void displayNotificationFour()
        {
            //Ses komutu seçenekleri için  bir string dizisi oluşturmalıyız.
            //Bu diziyi de strings.xml dosyasında oluşturup dizimize kaynak olarak veriyoruz.
            String[] replyChoices = getResources().getStringArray(R.array.reply_choices);
            //İlgili seçeneklerden ses komutu alan değişkenimizi oluşturduk
            RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                    .setChoices(replyChoices)
                    .build();

            //Intent oluşturulur.
            Intent replyIntent = new Intent(this, NotificationFour.class);
            PendingIntent replyPendingIntent =
                    PendingIntent.getActivity(this, 0, replyIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            // Action objesi yaratılır ve ses komutu alabilmek için yarattığımız "remoteInput" objemiz "action"'a eklenir.
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(R.drawable.sesli_komut_logo,getString(R.string.label), replyPendingIntent)
                            .addRemoteInput(remoteInput)
                            .build();

            //WearableExtender ile oluşturduğumuz sesli komut alan action'ı notificationBuilder'a ekliyoruz.
            //Böylelikle gelen notification wear'dan ses komutu almadığı sürece işlem yapmayacaktır.
            Notification notification =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.sesli_komut_logo)
                            .setContentTitle("Sesli Komut Alma")
                            .setContentText("Hangi siteye yönlenmek istersiniz?")
                            .extend(new WearableExtender().addAction(action))
                            .build();

            //Bildirim hazırlanır.
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);
            notificationManager.notify(023, notification);
        }

}






