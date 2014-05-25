package com.example.androidserviceexample.app;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class MainActivity extends ListActivity {
    private LocalWordService s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("in main onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                wordList);
        setListAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent= new Intent(this, LocalWordService.class);
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
            LocalWordService.MyBinder b = (LocalWordService.MyBinder) binder;
            s = b.getService();
            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
                    .show();
        }

        public void onServiceDisconnected(ComponentName className) {
            s = null;
        }
    };


    private ArrayAdapter<String> adapter;
    private List<String> wordList;

    public void onClick(View view) {
        System.out.println("in onClick in mainActivity");
        if (s != null) {
            System.out.println("in onClick: s!=null!");
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