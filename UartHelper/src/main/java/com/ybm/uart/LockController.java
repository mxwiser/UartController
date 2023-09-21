package com.ybm.uart;

public class LockController{
    private UartHelper uartHelper;
    public LockController(UartHelper uartHelper){
        this.uartHelper = uartHelper;
    }
    private int lock_cmd(int cmd,int address,int channel,int operate){
        byte[] bytes=new byte[5];
        bytes[0]= (byte) cmd;
        bytes[1]= (byte) address;
        bytes[2]= (byte) channel;
        bytes[3]= (byte) operate;
        bytes[4]= (byte) (bytes[0]^bytes[1]^bytes[2]^bytes[3]);
        uartHelper.send_byte(bytes);
        String hexData=uartHelper.getReceive();
        return 0;
    }
    public int openAllLock(int address){
        return  lock_cmd(0x9D,address,0x02,0x10);
    }


}
