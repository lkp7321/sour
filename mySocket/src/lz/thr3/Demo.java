package lz.thr3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo implements Runnable {
    private int tickets = 100;
    private Lock lock = new ReentrantLock();
    @Override
    public void run() {
        while (true) {
            lock.lock();
            if(tickets > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":ss:"+tickets--);
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Demo demo1 = new Demo();
        Thread thread1 = new Thread(demo1, "线程1");
        Thread thread2 = new Thread(demo1, "线程2");
        thread1.start();
        thread2.start();
    }
}
