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

import java.util.List;
import java.util.UUID;

public class TokenTest extends AppCompatActivity{

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BluetoothAdapter mBluetoothAdapter;

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
                builder.setOnDismissListener(new DialogInterface.OnDismissListener(){
                    public void onDismiss(DialogInterface dialog){
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
                BluetoothLeScanner bLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

                new BlueTask(mBluetoothAdapter, getApplicationContext()).execute(bLeScanner);

            }
        });

    }

}

// Creating a new Thread to work on.
class BlueTask extends AsyncTask<BluetoothLeScanner, Void, Void>{
    BluetoothAdapter mBluetoothAdapter;
    Context appContext;
    public BlueTask(BluetoothAdapter mBA, Context aC){
        mBluetoothAdapter = mBA;
        appContext = aC;
    }
    protected Void doInBackground(BluetoothLeScanner... bLeScanners) {

        for (BluetoothLeScanner ble: bLeScanners){
            ble.startScan(new ConnectScan(mBluetoothAdapter, appContext));
        }

        return null;
    }
}

// Making th logic behind the scaning
class ConnectScan extends ScanCallback{
    BluetoothAdapter mBluetoothAdapter;
    Context appContext;
    public ConnectScan(BluetoothAdapter mBA,Context aC){
        super();
        mBluetoothAdapter = mBA;
        appContext = aC;
    }

    public void onBatchScanResults(List<ScanResult> results){
        System.out.println("onBatchScanResults:");
        for(ScanResult r:results){
            System.out.print(r.toString() + ", ");
        }
    }

    public void onScanFailed(int errorCode){
        System.out.println("onScanFailed: ");
        System.out.println(errorCode);
    }

    public void onScanResult(int callbackType, ScanResult result){

        try{if(!result.getScanRecord().getServiceUuids().isEmpty() && result.getScanRecord().getServiceUuids().get(0).toString().startsWith("00007ab0")){
            System.out.println("Connecting to device: " + result.getScanRecord().getDeviceName());
            BluetoothDevice mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(result.getDevice().getAddress());
            BluetoothGatt mBluetoothGatt = mBluetoothDevice.connectGatt(appContext,true,new bLeGattCallback());
            System.out.println("hejd");
            System.out.println(mBluetoothGatt.discoverServices());
            System.out.println(mBluetoothGatt.getServices());
            mBluetoothGatt.readCharacteristic(new BluetoothGattCharacteristic(UUID.fromString("00007ab1-0000-1000-8000-00805f9b34fb"), 0x02, 0));

        }}catch (NullPointerException e){

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

class bLeGattCallback extends BluetoothGattCallback {

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
        // Callback reporting the result of a characteristic read operation.
        System.out.println(characteristic.getStringValue(0));

    }

}