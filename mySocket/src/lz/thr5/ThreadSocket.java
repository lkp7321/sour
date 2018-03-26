package lz.thr5;

public class ThreadSocket {
    private String name;
    private int age;
    private boolean flag;
    public synchronized void setSock(String name, int age) {
        if(this.flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.name = name;
        this.age = age;
        System.out.println("生产："+Thread.currentThread().getName()+":"+name+":"+age);
        this.flag = true;
        this.notify();
    }
    public synchronized void getSock() {
        if(!this.flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费："+Thread.currentThread().getName()+":"+this.name+":"+this.age);
        this.flag = false;
        this.notify();
    }
}
