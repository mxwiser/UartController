package com.ybm.uart;

import com.vi.vioserial.NormalSerial;
import com.vi.vioserial.listener.OnNormalDataListener;

public class UartHelper {
    private NormalSerial normalSerial;
    private String com;
    private int baudRate;
    private byte[] feedback_bytes;
    private LockController lockController;
    public UartHelper(String com, int baudRate){
        normalSerial=NormalSerial.instance();
        this.com=com;
        this.baudRate=baudRate;
   }

   public void open(){
       normalSerial.open(com,baudRate);
       normalSerial.addDataListener(onNormalDataListener);
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
