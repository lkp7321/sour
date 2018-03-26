package lz.ThreadPool;

/**
 * 匿名内部类的方式实现多线程
 * @author lz130
 */
public class InThreadPool {
    public static void main(String[] args) {
        // Thread方式生成线程
        new Thread(){
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("THREAD："+Thread.currentThread().getName()+" ："+i);
                }
            }
        }.start();
        // Runnable 方式生成线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("RUNNABLE："+Thread.currentThread().getName()+" ："+i);
                }
            }
        }){}.start();
        // 匿名内部类，两个线程Runnable，和Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println("RUN："+Thread.currentThread().getName()+" ："+i);
                }
            }
        }){
            public void run(){
                for (int i = 0; i < 100; i++) {
                    System.out.println("TRO："+Thread.currentThread().getName()+" ："+i);
                }
            }
        }.start();

    }
}
