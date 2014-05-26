package com.example.androidserviceexample.app;

/**
 * Created by josh on 5/25/14.
 */



import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


public class LogScreen extends ListActivity {
    private LocalBluetoothService s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("in main onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_log);
        wordList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                wordList);
        setListAdapter(adapter);

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, LocalBluetoothService.class);
        bindService(intent, mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder binder) {
            System.out.println("in onServiceConnected");
            LocalBluetoothService.MyBinder b = (LocalBluetoothService.MyBinder) binder;
            s = b.getService();
            Toast.makeText(LogScreen.this, "Connected", Toast.LENGTH_SHORT)
                    .show();
        }

        public void onServiceDisconnected(ComponentName className) {
            s = null;
        }
    };


    private ArrayAdapter<String> adapter;
    private List<String> wordList;

    public void onClick(View view) {
        if (s != null) {
            Toast.makeText(this, "Number of elements" + s.getWordList().size(),
                    Toast.LENGTH_SHORT).show();
            wordList.clear();
            wordList.addAll(s.getWordList());
            adapter.notifyDataSetChanged();
        }else{
            System.out.println("ERROR: s is null!");
        }
    }
}

/*

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogScreen extends Activity
{
    MainActivity ob;
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.service_log);
        Button b = (Button) findViewById(R.id.btnClick2);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void setOb( MainActivity obA){
        this.ob=obA;
    }
}
*/