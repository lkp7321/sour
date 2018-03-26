package lz.thr2;

public class ThreadSafe implements Runnable {
    private static int tickets = 100;
    private static int i = 0;
    // 解决同步问题
    @Override
    public void run() {
        while(true) {
            if (i % 2 == 0) {
                synchronized (ThreadSafe.class) {
                    if (tickets>0) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("现在是："+Thread.currentThread().getName()+"在售第："+tickets--+"张票");
                    }
                }
            }else{
                ss();
            }
          i++;
        }
    }
    // 同步方法
    private static synchronized void ss() {
       if (tickets>0) {
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println("现在是："+Thread.currentThread().getName()+"在售第："+tickets--+"张票");
       }
    }
    public static void main(String[] args) {
        ThreadSafe th = new ThreadSafe();
        Thread t1 = new Thread(th, "线程1");
        Thread t2 = new Thread(th, "线程2");
        t1.start();
        t2.start();

    }
}
