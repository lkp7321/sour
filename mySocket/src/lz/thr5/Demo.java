package lz.thr5;

/**
 * 线程组
 * 使用 ThreadGroup 来表示线程组， 对一批线程进行分类管理
 * @author lz130
 */
public class Demo {
    public static void main(String[] args) {
        //method1();
        //method2();
        //优化
        ThreadGroup threadGroup = new ThreadGroup("我的线程组");
        ThreadSocket ts = new ThreadSocket();
        D1 d1 = new D1(ts);
        D2 d2 = new D2(ts);
        Thread t1 = new Thread(threadGroup, d1,"优化线程一");
        Thread t2 = new Thread(threadGroup, d2,"优化线程二");
        t1.start();
        t2.start();
    }
    public static void method2() {
        ThreadGroup threadGroup = new ThreadGroup("我的线程组");
        Grop1 grop1 = new Grop1();
        Thread t1 = new Thread(threadGroup, grop1, "线程一");
        Thread t2 = new Thread(threadGroup, grop1, "线程二");
        System.out.println(t1.getThreadGroup().getName());
        System.out.println(t2.getThreadGroup().getName());
    }
    public static void method1() {
        Grop1 grop1 = new Grop1();
        Thread t1 = new Thread(grop1, "线程一");
        Thread t2 = new Thread(grop1, "线程二");
        ThreadGroup tg = t1.getThreadGroup();
        ThreadGroup tg2 = t2.getThreadGroup();
        System.out.println(tg);//默认是主线程
        System.out.println(tg2);//默认是主线程
        System.out.println(Thread.currentThread().getThreadGroup().getName());//主线程
//        t1.start();
//        t2.start();
    }

}
class Grop1 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"：i");
        }
    }
}
