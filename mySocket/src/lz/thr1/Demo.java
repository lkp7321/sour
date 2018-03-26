package lz.thr1;

/**
 * main方法：主线程
 * jvm 的启动是多线程的，在启动虚拟机时，运行了垃圾回收机制（否则会出现内存溢出）（主线程+垃圾回收最低是启动了两个）
 * 分时调度：每个线程轮流使用CPU
 * 抢占式：随机（优先级相同的时候）
 * java中采用的就是抢占式
 * @author lz130
 */
public class Demo extends Thread{
    public Demo(String name){
        super(name);
    }
    public Demo() {
    }
    @Override
    public void run() {
        System.out.println("程序开始执行："+getName());
//      for (int i = 0; i < 10; i++) {// i为10，启动两个线程太快
//          System.out.println("x:"+i);
//      }
        for (int i = 0; i < 100; i++) {// i为100，分辨两个线程
            //该进程 每隔两秒 输出一次
            //休眠
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 不能以此保证每个线程都调一次
            Thread.yield(); // 暂停当前线程，执行其他线程
            System.out.println("x:"+getName()+"="+i);
        }
    }
    public static void main(String[] args) {
//        Demo demo = new Demo();
//        demo.setName("线程1");
//        Demo demo1 = new Demo();
//        demo1.setName("线程2");
//        demo.start();
//        demo1.start();
        // 主线程
        System.out.println(Thread.currentThread().getName());
        //多线程
        Demo demo1 = new Demo("线程1");
        Demo demo2 = new Demo("线程2");
        //获取线程的优先级
        System.out.println(demo1.getPriority());//默认优先级：5
        System.out.println(demo2.getPriority());
        //设置优先级
        //仅仅表示 在获得cpu资源的几率高一些
        demo1.setPriority(1);
        demo2.setPriority(10);//优先级高
        // false 为 用户线程
        // true 为 守护线程
        // 默认为用户线程（false）
        // 当主线程结束用户线程还会继续执行
        // 当主线程结束没有用户线程（都是守护线程）jvm停止运行。
        demo1.setDaemon(true);
        demo2.setDaemon(true);
        demo1.start();
//        try {
//            demo1.join();// 等待该线程结束
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        demo2.start();
        Thread.currentThread().setName("主线程");
        for (int i = 0; i < 2; i++) {
            System.out.println(Thread.currentThread().getName()+"="+i);
        }
        //中断线程
        //demo1.stop();// 此方法不安全
        demo1.interrupt();// 中断线程
        //线程的生命周期
        //新建：创建线程对象
        //就绪：有执行资格，没执行权
        //运行：有执行资格，有执行权
        //阻塞：一些操作使其变为没有执行资格，没有执行权，但是使用一些方法可以使其被激活（sleep，waite）（sleep时间过了，notify）
        //死亡：线程对象变成垃圾
    }
}
