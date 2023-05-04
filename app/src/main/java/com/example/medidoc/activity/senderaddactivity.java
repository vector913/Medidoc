package com.example.medidoc.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medidoc.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class senderaddactivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private static final String TAG = senderaddactivity.class.getSimpleName();
    private Uri uriContact;
    private String contactID;
    private String contactNumber = null;
    private  String contactName = null;
    private int requestCode;
    private int resultCode;
    Intent data;
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

        final SharedPreferences number_list = getApplicationContext().getSharedPreferences(getResources().getString(R.string.prefKeyName),0);


        Set<String> phonel = number_list.getStringSet("phonenum",null);
        Set<String> namel = number_list.getStringSet("phonename",null);




        phonebooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });

        cancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_find_id = new Intent(v.getContext(), settingactivity.class);
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

                Intent to_find_id = new Intent(v.getContext(), sendlistactivity.class);
                startActivityForResult(to_find_id,0);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE_PICK_CONTACTS&&resultCode == RESULT_OK){
            Log.d(TAG,"Response: " + data.toString());
            uriContact = data.getData();
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
          askForContactPermission();

            addn.setText(contactNumber);
            adds.setText(contactName);
        }
    }


    private void getContact(){

        if(requestCode == REQUEST_CODE_PICK_CONTACTS&&resultCode==RESULT_OK) {
            Cursor contctCursorVar = getContentResolver().query(uriContact, null,
                    null, null, null);
            if (contctCursorVar.getCount() > 0) {
                //contctCursorVar.moveToFirst();
                while (contctCursorVar.moveToNext()) {
                    contactID = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                    contactName = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    Log.i("Names", contactName);

                    if (Integer.parseInt(contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        // Query phone here. Covered next
                        contactNumber = contctCursorVar.getString(contctCursorVar.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("Number", contactNumber);
                    }

                }
                contctCursorVar.close();
            }
        }
    }

    public void askForContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(senderaddactivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(senderaddactivity.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(senderaddactivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , REQUEST_CODE_PICK_CONTACTS);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(senderaddactivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_CODE_PICK_CONTACTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }else{
                getContact();
            }
        }
        else{
            getContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(getApplicationContext(),"전화번호부에 접근권한이 없습니다.", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
