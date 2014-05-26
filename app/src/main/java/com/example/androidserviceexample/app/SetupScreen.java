package com.example.androidserviceexample.app;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josh on 5/25/14.
 */
public class SetupScreen extends Activity { /* ListActivity {*/

    private ArrayAdapter<String> adapter;
    private List<String> emailList;

    ArrayAdapter<String> m_adapter;
    ArrayList<String> m_listItems = new ArrayList<String>();
    /** Called when the activity is first created. */

    Button bt;
    EditText et;
    TextView tv;
    ListView lv;

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



        bt = (Button) findViewById(R.id.button1);
        et = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textView);
        lv = (ListView) findViewById(R.id.listView);
        m_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, m_listItems);
        lv.setAdapter(m_adapter);
        final String input = et.getText().toString();

       /* bt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                m_listItems.add(new String(input));
                m_adapter.notifyDataSetChanged();
            }
        });
        */
        bt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String input = et.getText().toString();
                if(null!=input&&input.length()>0){

                    m_listItems.add(input);

                    m_adapter.notifyDataSetChanged();

                }
            }
        });


       // emailList = new ArrayList<String>();
       // adapter = new ArrayAdapter<String>(this,
       //         android.R.layout.simple_list_item_1, android.R.id.text1,
       //         emailList);
       // setListAdapter(adapter);
    }
}
