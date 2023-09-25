package com.ybm.uart;

public class LockController{
    private UartHelper uartHelper;

    private  String hexData;
    private byte[] hex_buffer;
    public LockController(UartHelper uartHelper){
        this.uartHelper = uartHelper;
    }
    public int lock_cmd(int cmd,int address,int channel,int operate){
        byte[] bytes=new byte[5];
        long timeout=800;
        if (cmd==0x9D)
             timeout=10000;

        int state;
        bytes[0]= (byte) cmd;
        bytes[1]= (byte) address;
        bytes[2]= (byte) channel;
        bytes[3]= (byte) operate;
        bytes[4]= (byte) (bytes[0]^bytes[1]^bytes[2]^bytes[3]);
        uartHelper.send_byte(bytes);
        hexData=uartHelper.getReceive(timeout);
        if (hexData.equals(""))
            state = uartHelper.TIMEOUT;
        else if (hexData.length()>=10){
            state = uartHelper.SUCCESS;
        }else {
            state = uartHelper.EXPIRE;
        }

        if (state==uartHelper.SUCCESS){
            if (cmd==0x80){
                try {
                    hex_buffer=uartHelper.hex_to_bytes(hexData);
                    if (hex_buffer[4]==(hex_buffer[0]^hex_buffer[1]^hex_buffer[2]^hex_buffer[3])){
                        if (hex_buffer[3]==0x11){
                            state=uartHelper.VALID;
                        }else {
                            state=uartHelper.INVALID;
                        }
                    }else {
                            state = uartHelper.EXPIRE;
                    }

                } catch (Exception e) {
                    state = uartHelper.EXCEPTION;
                    throw new RuntimeException(e);
                }
            }
        }

       return state;
    }





    public int openAllLock(int address){
        return  lock_cmd(0x9D,address,0x02,0x10);
    }
    public  int openLock(int address, int channel){
        return  lock_cmd(0x8A,address,channel,0x11);
    }
    public  int openPower(int address, int channel){
        return  lock_cmd(0x9A,address,channel,0x10);
    }
    public  int closePower(int address, int channel){
        return  lock_cmd(0x9B,address,channel,0x10);
    }
    public  int getState(int address, int channel){
        return  lock_cmd(0x80,address,channel,0x33);
    }

    public String getChipID(int address){
       if (lock_cmd(0xDD,address,0x11,0x11)==0){
           return hexData.substring(6,20);
       }else {
           return null;
       }

    }
    public String getVersion(int address){
        if (lock_cmd(0xDE,address,0x11,0x11)==0){
            return hexData.substring(4,8);
        }else {
            return null;
        }
    }

    public int setVector(int address,int vector){
        return lock_cmd(0xDF,address,1,vector);
    }


}
