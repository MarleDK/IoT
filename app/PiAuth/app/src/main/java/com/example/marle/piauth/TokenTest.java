package com.example.marle.piauth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import java.util.UUID;

public class TokenTest extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner bLeScanner;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "We are working on something greater please stand by", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Asks for permission to acces device lokation.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }

        Button mBluetoothConnect = (Button) findViewById(R.id.BluetoothConnect);

        mBluetoothConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initializes Bluetooth adapter.
                final BluetoothManager bluetoothManager =
                        (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                mBluetoothAdapter = bluetoothManager.getAdapter();

                // Ensures Bluetooth is available on the device and it is enabled. If not,
                // displays a dialog requesting user permission to enable Bluetooth.
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, 1);

                }

                // Starts scanning for devices.
                bLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

                new BlueTask(mBluetoothAdapter, getApplicationContext()).execute(bLeScanner);

            }
        });


        Button getPrivateKey = (Button) findViewById(R.id.getPrivateKey);
        final EditText user = (EditText) findViewById(R.id.user);
        final EditText pass = (EditText) findViewById(R.id.pass);
        global.setQueue(this);
        System.out.println("getPrivateKey button: " + getPrivateKey);

        getPrivateKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("user: " + user.getText() + " pass: " + pass.getText());
                Data data = new Data(user.getText().toString(), pass.getText().toString(), global.publicKey);
                new ConnectToServer().execute(data);


            }
        });

        Button btnWriteToPi = (Button) findViewById(R.id.btnWriteToPi);
        final EditText writeToPi = (EditText) findViewById(R.id.writeToPi);

        btnWriteToPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = writeToPi.getText().toString();
                System.out.println("Writing " + text + " to Pi");
                new BlueWriteTask(mBluetoothAdapter , getApplicationContext(), text).execute(bLeScanner);

            }
        });

    }

}
