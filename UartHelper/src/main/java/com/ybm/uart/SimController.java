package com.ybm.uart;

public class SimController {
private UartHelper uartHelper;
    public SimController(String device){
            uartHelper=new UartHelper();
            uartHelper.open(device,9600);
    }
    public String getICCID() {


        return "";
    }
}
