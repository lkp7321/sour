package lz.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池：提高系统性能
 * 线程池的实现
 * @author lz130
 */
public class Demo {
    public static void main(String[] args) {
        // 创建一个线程池对象，控制创建几个线程对象
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 可以执行的Runnable对象或者Callable对象
        pool.submit(new MyRunnable());
        pool.submit(new MyRunnable());
        // 结束线程池
        pool.shutdown();
    }
}
