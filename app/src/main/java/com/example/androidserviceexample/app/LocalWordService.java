package com.example.androidserviceexample.app;

/**
 * Created by josh on 5/25/14.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class LocalWordService extends Service {
    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("in LocalWordService: onStartCommand");

        if (list.size() >= 20) {
            list.remove(0);
        }
        list.add("UPDATED: " + System.currentTimeMillis());
        System.out.println("In LocalWordService");
        System.out.println(list);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        System.out.println("in LocalWordService: onBind");
        return mBinder;
    }

    public class MyBinder extends Binder {
        LocalWordService getService() {
            return LocalWordService.this;
        }
    }

    public List<String> getWordList() {
        return list;


    }

}