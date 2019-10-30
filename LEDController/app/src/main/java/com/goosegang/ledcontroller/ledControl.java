package com.goosegang.ledcontroller;

import androidx.appcompat.app.AppCompatActivity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import java.io.IOException;
import java.util.UUID;

import android.os.Bundle;

public class ledControl extends AppCompatActivity {

    Button btnOn, btnOff, btnDisconnect;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);

        Intent newInt = getIntent();
        address = newInt.getStringExtra(MainActivity.EXTRA_ADDRESS);

        btnOn = (Button)findViewById(R.id.buttonOn);
        btnOff = (Button)findViewById(R.id.buttonOff);
        btnDisconnect = (Button)findViewById(R.id.buttonDisconnect);

        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                turnOnLed();      //method to turn on
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffLed();   //method to turn off
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {

        private boolean ConnectSuccess = true;

        protected void onPreExecuted() {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "...");
        }

        @Override
        protected Void doInBackground(Void... devices) {

            try {

                if (btSocket == null || !isBtConnected) {

                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice disconnectPositive = myBluetooth.getRemoteDevice(address);
                    btSocket = disconnectPositive.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();

                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                Toast.makeText(getApplicationContext(), "Connection Failed.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Connected.", Toast.LENGTH_LONG).show();
                isBtConnected = true;
            }

            progress.dismiss();
        }
    }

    private void Disconnect() {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { Toast.makeText(getApplicationContext(),"Error. ",Toast.LENGTH_LONG).show();}
        }
        finish(); //return to the first layout
    }

    private void turnOffLed() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TF".toString().getBytes());
            }
            catch (IOException e)
            {
                Toast.makeText(getApplicationContext(),"Error. ",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void turnOnLed() {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TO".toString().getBytes());
            }
            catch (IOException e)
            {
                Toast.makeText(getApplicationContext(),"Error. ",Toast.LENGTH_LONG).show();
            }
        }
    }
}
