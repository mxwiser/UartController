package com.ybm.uart;

import com.vi.vioserial.NormalSerial;
import com.vi.vioserial.listener.OnNormalDataListener;

public class UartController {
    NormalSerial normalSerial;
    public UartController(String com,int baudRate){
        normalSerial=NormalSerial.instance();
        normalSerial.open(com,baudRate);
        normalSerial.addDataListener(onNormalDataListener);
   }

   public void sendHex(String hex){
        normalSerial.sendHex(hex);
   }

   OnNormalDataListener onNormalDataListener=new OnNormalDataListener() {
       @Override
       public void normalDataBack(String hexData) {

       }
   };
}
