package anurag.services;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MyService service;
    boolean bounded = false;

    Intent intent;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.PlayerBinder binder = (MyService.PlayerBinder)iBinder;
            service = binder.getService();
            bounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bounded = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,MyService.class);
    }

    public void start(View view){
        //startService(intent);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);//starts the service

    }

    public void stop(View view){

        stopService(intent);//inbuilt function and destroys the service
    }

    public void play(View view){
        if(bounded) {
            service.play();//plays the song
        }
    }

    public void pause(View view){
        if(bounded){
            service.pause();
        }
    }

}
