package com.ybm.uart;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MLock {
    private static boolean isLock=false;

    public synchronized  void lock() {
        while (isLock) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        isLock=true;
    }




    public synchronized  void tryLock(long timeout) {

        try {
            wait(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        isLock=true;
    }




    public synchronized  void unlock() {
        isLock=false;
        notify();
    }


}
