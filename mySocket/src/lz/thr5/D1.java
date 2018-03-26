package lz.thr5;

public class D1 implements Runnable {
    private ThreadSocket ts;
    private int x = 0;
    public D1(ThreadSocket ts) {
        this.ts = ts;
    }
    @Override
    public void run() {
        while(true) {
            if(x % 2 ==0){
                ts.setSock("lili",17);
            }else {
                ts.setSock("nana",13);
            }
            x++;
        }
    }
}
