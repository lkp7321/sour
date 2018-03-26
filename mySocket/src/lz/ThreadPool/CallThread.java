package lz.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程的实现方式3
 * Callable：带泛型的接口
 * Callable 的泛型是指 call()方法的返回值类型。
 */
public class CallThread {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(new MyCallable());
        pool.submit(new MyCallable());
        pool.shutdown();
    }
}
