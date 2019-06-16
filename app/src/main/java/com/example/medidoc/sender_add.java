package com.example.medidoc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class sender_add extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private static final String TAG =sender_add.class.getSimpleName();
    private Uri uriContact;
    private String contactID;
    private String contactNumber = null;
    private  String contactName = null;
    EditText addn;
    EditText adds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sender_add_layout);
        final ArrayList<String> names;
        final ArrayList<String> phones;
        Button phonebooks = findViewById(R.id.in_phonebook);
        Button register = findViewById(R.id.sender_add_button);
        Button cancels = findViewById(R.id.add_cancel);
        addn = findViewById(R.id.add_number);
        adds = findViewById(R.id.add_name);

        final SharedPreferences number_list = getApplicationContext().getSharedPreferences("mediSettings",0);


        Set<String> phonel = number_list.getStringSet("phonenum",null);
        Set<String> namel = number_list.getStringSet("phonename",null);




        phonebooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });

        cancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(),setting.class);
                startActivityForResult(to_find_id,0);
            }
        });

        if(namel!=null&&phonel!=null) {
            names = new ArrayList<String>(namel);
            phones = new ArrayList<String>(phonel);
        }else{
            names = new ArrayList<String>();
            phones = new ArrayList<String>();
        }
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = number_list.edit();
                Set<String> phonel = new HashSet<String>();
                Set<String> namel = new HashSet<String>();

                names.add(adds.getText().toString());
                phones.add(addn.getText().toString());

                namel.addAll(names);
                phonel.addAll(phones);

                editor.putStringSet("phonenum",phonel);
                editor.putStringSet("phonename",namel);
                editor.apply();

                Intent to_find_id = new Intent(v.getContext(),sendlist.class);
                startActivityForResult(to_find_id,0);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE_PICK_CONTACTS&&resultCode == RESULT_OK){
            Log.d(TAG,"Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();
            retrieveContactNumber();

            addn.setText(contactNumber);
            adds.setText(contactName);
        }
    }

    private void retrieveContactNumber() {


        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactName() {


        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);
    }
}
