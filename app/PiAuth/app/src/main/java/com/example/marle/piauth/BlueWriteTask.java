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
public class BlueWriteTask extends AsyncTask<BluetoothLeScanner, Void, Void> {

    BluetoothAdapter mBluetoothAdapter;
    Context appContext;
    String messageToPi;

    public BlueWriteTask(BluetoothAdapter mBA, Context aC, String mTP) {
        mBluetoothAdapter = mBA;
        appContext = aC;
        messageToPi = global.privateKey + ":" +mTP;
    }

    protected Void doInBackground(BluetoothLeScanner... bLeScanners) {

        for (BluetoothLeScanner ble : bLeScanners) {
            ble.startScan(new ConnectScan(mBluetoothAdapter, appContext));
        }

        return null;
    }


    // Making th logic behind the scaning
    private class ConnectScan extends ScanCallback {
        BluetoothAdapter mBluetoothAdapter;
        Context appContext;

        public ConnectScan(BluetoothAdapter mBA, Context aC) {
            super();
            mBluetoothAdapter = mBA;
            appContext = aC;
        }

        public void onBatchScanResults(List<ScanResult> results) {
            System.out.println("onBatchScanResults:");
            for (ScanResult r : results) {
                System.out.print(r.toString() + ", ");
            }
        }

        public void onScanFailed(int errorCode) {
            System.out.println("onScanFailed: ");
            System.out.println(errorCode);
        }

        public void onScanResult(int callbackType, ScanResult result) {

            try {
                if (!result.getScanRecord().getServiceUuids().isEmpty() && result.getScanRecord().getServiceUuids().get(0).toString().startsWith("00007ab0")) {
                    System.out.println("Connecting to device: " + result.getScanRecord().getDeviceName());
                    BluetoothDevice mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(result.getDevice().getAddress());
                    System.out.println("mBluetoothDevice is:" + mBluetoothDevice);
                    BluetoothGatt mBluetoothGatt = mBluetoothDevice.connectGatt(appContext, true, new bLeGattCallback());

                }
            } catch (NullPointerException e) {

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private class bLeGattCallback extends BluetoothGattCallback {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == 1) {
                System.out.println("Connection state = connecting");
            } else if (newState == 2) {
                System.out.println();
                System.out.println("Connection state = connected");

                System.out.println("mBluetoothGatt.discoverServices(): " + gatt.discoverServices());
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            System.out.println("discover services status: " + status);
            BluetoothGattCharacteristic mCharacteristic = gatt.getService(UUID.fromString("00007ab0-0000-1000-8000-00805f9b34fb")).getCharacteristic(UUID.fromString("00007ab2-0000-1000-8000-00805f9b34fb"));
            mCharacteristic.setValue(messageToPi.getBytes());
            gatt.writeCharacteristic(mCharacteristic);


        }
    }
}