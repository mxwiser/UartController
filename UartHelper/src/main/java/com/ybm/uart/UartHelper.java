package com.ybm.uart;

import com.vi.vioserial.NormalSerial;
import com.vi.vioserial.listener.OnNormalDataListener;

public class UartHelper {
    private NormalSerial normalSerial;
    private LockController lockController;
    public UartHelper(String com, int baudRate){
        normalSerial=NormalSerial.instance();
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

}
