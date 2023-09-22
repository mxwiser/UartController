package com.ybm.uart;

public class LockController{
    private UartHelper uartHelper;
    private  String hexData;
    public LockController(UartHelper uartHelper){
        this.uartHelper = uartHelper;
    }
    private int lock_cmd(int cmd,int address,int channel,int operate){
        byte[] bytes=new byte[5];
        int state;
        bytes[0]= (byte) cmd;
        bytes[1]= (byte) address;
        bytes[2]= (byte) channel;
        bytes[3]= (byte) operate;
        bytes[4]= (byte) (bytes[0]^bytes[1]^bytes[2]^bytes[3]);
        uartHelper.send_byte(bytes);
        hexData=uartHelper.getReceive();

        if (hexData.equals(""))
            state = uartHelper.TIMEOUT;
        if (hexData.length()>=10){
            state = uartHelper.SUCCESS;
        }else {
            state = uartHelper.EXPIRE;
        }

        if (state==uartHelper.SUCCESS){
            if (cmd==0x80){

            }
        }

       return state;
    }



    public int openAllLock(int address){
        return  lock_cmd(0x9D,address,0x02,0x10);
    }
    public  int openLock(byte address, byte channel){
        return  lock_cmd(0x8A,address,channel,0x11);
    }
    public  int openPower(byte address, byte channel){
        return  lock_cmd(0x9A,address,channel,0x10);
    }
    public  int closePower(byte address, byte channel){
        return  lock_cmd(0x9B,address,channel,0x10);
    }
    public  int getState(byte address, byte channel){
        return  lock_cmd(0x80,address,channel,0x33);
    }

}
