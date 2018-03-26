package lz.thr4;

/**
 * 第一次：因为不同的类创造了不同的资源对象
 * 第二次：资源相同，但是不能保证同步安全（还没生产就已经开始消费）
 *      (在CPU的执行时间片段中线程1可能执行到一半，或是还没开始执行。线程2就获得执行权)
 * 第三次：加锁，但是不能保证（销完在产 和 产后在销）
 *      (给不同的线程都加上锁，（锁相同）不同的线程加上同一把锁)
 * 第四次：产之前检查有没有资源（没有.就产；有.就等）
 *        销之前检查有没有资源（没有.就等；有.就销）
 *        提供了等待和唤醒（机制）
 *状态：
 * 同步阻塞，等待阻塞，
 * 运行状态经过synchronized进入同步阻塞状态
 * 运行状态的线程，通过wait进入等待阻塞状态，notify唤醒线程到同步阻塞，锁可用进入就绪状态
 *
 * @author lz130
 */
public class StudentDemo {
    public static void main(String[] args) {
        Student s = new Student();

        SetStudent c = new SetStudent(s);
        GetStudent g = new GetStudent(s);
        Thread t1 = new Thread(c,"chi");
        Thread t2 = new Thread(g,"las");
        t1.start();
        t2.start();
    }
}
