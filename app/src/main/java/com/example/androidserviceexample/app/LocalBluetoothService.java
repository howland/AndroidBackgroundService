package com.example.androidserviceexample.app;

/**
 * Created by josh on 5/25/14.
 */

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class LocalBluetoothService extends Service {
    private final IBinder mBinder = new MyBinder();
    private ArrayList<String> list = new ArrayList<String>();
    private BluetoothAdapter mBluetoothAdapter;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // System.out.println("in LocalWordService: onStartCommand");






//Old and busted method
        //mBluetoothAdapter = BluetoothAdapter.getInstance();
        //mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

//New hotness method
        BluetoothManager manager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = manager.getAdapter();





        if (list.size() >= 20) {
            list.remove(0);
        }
        list.add("UPDATED: " + System.currentTimeMillis());
        //System.out.println("In LocalWordService");
        //System.out.println(list);
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
       // System.out.println("in LocalWordService: onBind");
        return mBinder;
    }

    public class MyBinder extends Binder {
        LocalBluetoothService getService() {
            return LocalBluetoothService.this;
        }
    }

    public List<String> getWordList() {
        return list;


    }

}