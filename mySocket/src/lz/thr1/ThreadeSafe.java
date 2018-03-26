package lz.thr1;

import java.util.*;

/**
 * 是否有多线程
 * 是否存在共享数据
 * 是否操作共享数据
 *
 * @author lz130
 */
public class ThreadeSafe implements Runnable {
    private int tickets = 100;
    private Object obj = new Object();
    @Override
//    public void run() {
//        while(true) {
//            if(tickets>0){
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName()+" 正在出售票数："+tickets--);
//            }
//        }
//    }
    // 解决同步问题
    public void run() {
        while(true) {
            synchronized (obj) {
                if (tickets > 0) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 正在出售票数：" + tickets--);
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadeSafe th = new ThreadeSafe();
        Thread t1 = new Thread(th, "线程1");
        Thread t2 = new Thread(th, "线程2");
        t1.start();
        t2.start();
        // 几个线程安全的类
        StringBuffer sbf = new StringBuffer("aa");
        Vector<String> vector = new Vector<>(); // 不论线程是否安全都不使用
        // 使用线程安全的数组集合
        List<String > list = Collections.synchronizedList(new ArrayList<String>());
        Hashtable<String, String> hst = new Hashtable<String, String>();

    }
}
