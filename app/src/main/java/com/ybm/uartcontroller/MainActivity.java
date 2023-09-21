package com.ybm.uartcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ybm.uart.UartHelper;

public class MainActivity extends AppCompatActivity {

    UartHelper uarthelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uarthelper =new UartHelper("/dev/ttyS4",9600);
        uarthelper.getLockController();
    }
}