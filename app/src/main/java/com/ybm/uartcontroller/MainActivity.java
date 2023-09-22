package com.ybm.uartcontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ybm.uart.SimController;
import com.ybm.uart.UartHelper;

public class MainActivity extends AppCompatActivity {
    int feedback=-1;
    String feedback_str="";
    UartHelper uarthelper;
    SimController simController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uarthelper =new UartHelper();
        simController = new SimController();
        //uarthelper.open("/dev/ttyS4",9600);


    }


    public void setp_first(View view){

        new Thread(){
            @Override
            public void run() {
                super.run();
                feedback=uarthelper.getLockController().openAllLock(1);
                handler.sendEmptyMessage(1);
            }
        }.start();




    }

    public  void setp_second(View view){
            new Thread(){
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    super.run();
                    feedback_str= simController.getICCID("/dev/ttyUSB3");
                    handler.sendEmptyMessage(2);
                }
            }.start();


    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                Toast.makeText(getApplicationContext(),"code:"+feedback,Toast.LENGTH_LONG).show();
            }
            if (msg.what==2){
                Toast.makeText(getApplicationContext(),"code:"+feedback_str,Toast.LENGTH_LONG).show();
            }
        }
    };

}