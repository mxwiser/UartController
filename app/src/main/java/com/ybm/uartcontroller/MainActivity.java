package com.ybm.uartcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ybm.uart.UartHelper;

public class MainActivity extends AppCompatActivity {

    UartHelper uarthelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uarthelper =new UartHelper();
        uarthelper.open("/dev/ttyS4",9600);

    }


    public void setp_first(View view){
        int feedback=uarthelper.getLockController().openAllLock(1);
        Toast.makeText(getApplicationContext(),"code:"+feedback,Toast.LENGTH_LONG).show();

    }

    public  void setp_second(View view){
            new Thread(){
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    super.run();
                    int feedback=uarthelper.getLockController().openLock(1,1);
                    Log.e("========================",""+feedback);
                }
            }.start();


    }
}