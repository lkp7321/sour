package lz.thr4;


public class SetStudent implements Runnable {
    private Student s;
    private int x = 0;
    public SetStudent(Student s){
        this.s = s;
    }
    @Override
    public void run(){
        while(true) {
           synchronized (s) {
               if (s.flag) {
                   try {
                       s.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               if (x % 2 == 0) {
                   s.age = 18;
                   s.name = "lala";
                   System.out.println("产：" + Thread.currentThread().getName());
               } else {
                   s.age = 13;
                   s.name = "nanan";
                   System.out.println("产：" + Thread.currentThread().getName());
               }
               x++;
               s.flag = true;
               s.notify();
           }
        }
    }
}
