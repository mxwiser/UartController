package com.ybm.uart;

import com.vi.vioserial.NormalSerial;

public class UartController {
    NormalSerial normalSerial;
    public UartController(String com,int baudRate){
        normalSerial=NormalSerial.instance();
        normalSerial.open(com,baudRate);
   }

   public void sendHex(String hex){
        normalSerial.sendHex(hex);
   }
}
