package com.ybm.uart;

public class LockController{



    public static int SUCCESS =0;
    public static int FAIL=1;


     private UartHelper uartHelper;
     public LockController(UartHelper uartHelper){
        this.uartHelper = uartHelper;
     }

    public int openAllLock(int address){
         uartHelper.send_byte(new byte[]{(byte) 0x9D,(byte)address,(byte)0x01,(byte)0x33});
         return SUCCESS;
    }
}
