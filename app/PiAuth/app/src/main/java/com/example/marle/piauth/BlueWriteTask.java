package com.example.marle.piauth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.UUID;


/**
 * Created by marle on 4/30/17.
 */

// Creating a new Thread to work on.
public class BlueWriteTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {

        for(String s:params){
            BluetoothGattCharacteristic writeCharacteristic = global.gatt.getService(UUID.fromString("00007ab0-0000-1000-8000-00805f9b34fb")).getCharacteristic(UUID.fromString("00007ab2-0000-1000-8000-00805f9b34fb"));
            writeCharacteristic.setValue(global.privateKey +":" + s);
            global.gatt.writeCharacteristic(writeCharacteristic);
        }
        return null;
    }
}