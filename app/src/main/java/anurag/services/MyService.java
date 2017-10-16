package anurag.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {
    MediaPlayer player;
    Notification notification;
    PlayerBinder binder = new PlayerBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);

        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);

        notification = new NotificationCompat.Builder(this).setContentTitle("Title").setContentIntent(pendingIntent).build();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {called when service is started..
//        play();
//        return Service.START_STICKY;
//    }

    public class PlayerBinder extends Binder {
        public MyService getService(){
            return  MyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {//getting data when services are running and when bind services gets called...
        return binder;
    }


    public void play(){
        if(player!=null){
            player.start();//inbuilt function and starts the song
            startForeground(100,notification);//notifies the notification when ever the play button is hitted..
        }

    }

    public void pause(){
        if(player!=null){
            player.pause();//inbuilt fuction
            stopForeground(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pause();
        if(player != null){
            player.release();//inbuilt function
            player = null;
        }
    }
}
