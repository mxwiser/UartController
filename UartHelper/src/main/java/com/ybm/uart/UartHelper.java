package com.ybm.uart;

import com.vi.vioserial.NormalSerial;
import com.vi.vioserial.listener.OnNormalDataListener;

public class UartHelper {

    public static final int SUCCESS =0;
    public static final int FAIL=1;
    public static final int TIMEOUT=2;
    public static final int EXPIRE=3;
    public static final int EXCEPTION=4;
    public static final int VALID=5;
    public static final int INVALID=6;

    private NormalSerial normalSerial;

    private byte[] feedback_bytes;
    private LockController lockController;
    public UartHelper(){
        normalSerial=NormalSerial.instance();
        normalSerial.addDataListener(onNormalDataListener);
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

       }
   };
    public void send_byte(byte[] bytes){
        normalSerial.sendHex(bytes_to_string(bytes));
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
