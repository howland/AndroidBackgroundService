package com.example.androidserviceexample.app;

/**
 * Created by josh on 5/24/14.
 */

        import android.app.NotificationManager;
        import android.app.Service;
        import android.content.Intent;
        import android.os.IBinder;
        import android.util.Log;
        import android.widget.Toast;

public class MyService extends Service {



    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("LocalService: Received start id " + startId + ": " + intent);
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        // For time consuming an long tasks you can launch a new thread here...
        Toast.makeText(this, " Service Started", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();

    }



}