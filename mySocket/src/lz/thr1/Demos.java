package lz.thr1;

/**
 * 实现接口的方式：实现多线程
 * 避免单一继承造成的局限性
 * 适合多个相同的代码处理同一个资源的情况
 * 实现线程和程序代码、数据等的有效分离，较好的体现了面向对象的设计思想
 * @author lz130
 */
public class Demos implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"="+i);
        }
    }

    public static void main(String[] args) {
        Demos demos = new Demos();
//        Thread t1 = new Thread(demos);
//        Thread t2 = new Thread(demos);
//        t1.setName("线程1");
//        t1.setName("线程2");
        Thread t1 = new Thread(demos, "线程1");
        Thread t2 = new Thread(demos, "线程2");
        t1.start();
        t2.start();
    }
}
