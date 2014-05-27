package com.example.androidserviceexample.app;

/**
 * Created by josh on 5/25/14.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyStartServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent service = new Intent(context, LocalBluetoothService.class);
        context.startService(service);
    }
}
