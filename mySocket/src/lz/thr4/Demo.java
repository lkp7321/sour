package lz.thr4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo implements Runnable{
    private int tickets = 100;
    private Lock lock = new ReentrantLock();
    @Override
    public void run() {
        while(true){
            try {
                lock.lock();
                if (tickets>0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"，正在出售第："+tickets--+"张票");
                }
            }finally {
                lock.unlock();
            }

        }
    }
    public static void main(String[] args) {
        Demo demo = new Demo();
        Thread th1 = new Thread(demo,"售票员1");
        Thread th2 = new Thread(demo,"售票员2");
        th1.start();
        th2.start();
    }
}
