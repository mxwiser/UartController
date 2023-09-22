package com.ybm.uart;

public class MThreadLock {
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
