package com.example.androidserviceexample.app;


import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates launching the contacts app to pick a contact.  Does not
 * require permission to read contacts, as that permission will be granted
 * when the selected contact is returned.
 */
public class SetupScreen extends ListActivity {
    Toast mToast;
    static final int PICK_CONTACT = 21313;
    public static final String PREFS_NAME = "EFile31688542";
    // ResultDisplayer mPendingResult;
    private ArrayAdapter<String> adapter;
    //private List<ContactsContract.Contacts> contactList;
    private List<Uri> contactsUriList;
    private List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_screen);

        contactsUriList = new ArrayList<Uri>();
        nameList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                nameList);
        setListAdapter(adapter);
        populateContactList(this);



        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                finish();
            }
        });


        Button findContactButton = (Button)findViewById(R.id.pick_contact);
        findContactButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        System.out.println(position);
        System.out.println(nameList.get(position));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete");
        builder.setMessage("Remove " + nameList.get(position) + " from Emergency Contact List?");

        builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nameList.remove(position);
                contactsUriList.remove(position);
                saveUriValues();
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void populateContactList(Context mContext){
        SharedPreferences eList = getSharedPreferences(PREFS_NAME, 0);

        contactsUriList.clear();
            int size = eList.getInt("Status_size", 0);
    System.out.println("size" + size);
            for(int i=0;i<size;i++)
            {
                contactsUriList.add(Uri.parse(eList.getString("Status_" + i, null)));
                addContactName(contactsUriList.get(i));
            }

    }

    public void saveUriValues()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("Status_size", contactsUriList.size());

        for(int i=0;i<contactsUriList.size();i++){
            editor.remove("Status_" + i);
            editor.putString("Status_" + i, contactsUriList.get(i).toString());
        }

        // Commit the edits!
        editor.commit();
/*
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        System.out.println("Size: " + contactsUriList.size());
        for(int i=0;i<contactsUriList.size();i++)
        {
            System.out.println("Saving: " + contactsUriList.get(i).toString());
            editor.remove("Status_" + i);
            editor.putString("Status_" + i, contactsUriList.get(i).toString());
        }
      */

        //return editor.commit();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode);
        if (data != null) {
            Uri uri = data.getData();
            addContactName(uri);
            contactsUriList.add(uri);
            saveUriValues();
            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[] { BaseColumns._ID },
                            null, null, null);
                    if (c != null && c.moveToFirst()) {
                        int id = c.getInt(0);
                        if (mToast != null) {
                            mToast.cancel();
                        }
                        String txt = "CONTACT INFO:" + ":\n" + uri + "\nid: " + id;
                        mToast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
                        mToast.show();

                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }



    public void addContactName(Uri uri){
        String id, name, phone, hasPhone;
        name = "NULL";
        int idx;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            idx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            id = cursor.getString(idx);

            idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            name = cursor.getString(idx);

            idx = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            hasPhone = cursor.getString(idx);
        }
        nameList.add(name);
        adapter.notifyDataSetChanged();

    }

}