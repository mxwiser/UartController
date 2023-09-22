package com.ybm.uart;

import android.serialport.SerialPortFinder;
import android.util.Log;

import com.vi.vioserial.NormalSerial;
import com.vi.vioserial.listener.OnNormalDataListener;

public class UartHelper {

    public static final int SUCCESS =0;
    public static final int VALID=1;
    public static final int INVALID=2;
    public static final int TIMEOUT=-1;
    public static final int EXPIRE=-2;
    public static final int EXCEPTION=-3;


    private String feedback_str="";
    private MThreadLock locker;

    private NormalSerial normalSerial;
    private SerialPortFinder serialPortFinder;

    private byte[] feedback_bytes;
    private LockController lockController;
    public UartHelper(){
        normalSerial=NormalSerial.instance();
        normalSerial.addDataListener(onNormalDataListener);
        serialPortFinder=new SerialPortFinder();
        locker=new MThreadLock();
   }


   protected String getReceive(long timeout){
       feedback_str="";
       locker.unlock();
       locker.lock();
       locker.tryLock(timeout);
       return feedback_str;
   }

   public int open(String com, int baudRate){

       return normalSerial.open(com,baudRate);

   }

    public LockController getLockController(){
       if (lockController==null)
            lockController=new LockController(this);
       return lockController;
    }

   public void sendHex(String hex){
        normalSerial.sendHex(hex);
   }

   OnNormalDataListener onNormalDataListener=new OnNormalDataListener() {
       @Override
       public void normalDataBack(String hexData) {
           feedback_str=hexData;
           locker.unlock();
       }
   };
    public void send_byte(byte[] bytes){
        normalSerial.sendHex(bytes_to_string(bytes));
    }

    public String[] getAllDevices(){
        return serialPortFinder.getAllDevices();
    }
    public String[] getAllDevicesPath(){
        return serialPortFinder.getAllDevicesPath();
    }

   public String bytes_to_string(byte[] bytes){
       StringBuffer hexString = new StringBuffer();
       for (int i = 0; i<bytes.length; i++)
           hexString.append(String.format("%02x",bytes[i])) ;
       return hexString.toString();
   }

   public byte[] hex_to_bytes(String hexData) throws Exception{
       feedback_bytes=new byte[hexData.length()/2];
       for (int i = 0; i<hexData.length()/2; i++){
              byte b= (byte) Integer.parseInt(hexData.substring(i*2,i*2+2),16);
              feedback_bytes[i]=b;
       }
       return feedback_bytes;
   }

}
