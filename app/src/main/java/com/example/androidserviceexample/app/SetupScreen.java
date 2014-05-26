package com.example.androidserviceexample.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by josh on 5/25/14.
 */
public class SetupScreen extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        for(int i=0;i<100;i++)
            System.out.println("SetupScreen created!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_screen);

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
