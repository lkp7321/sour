package lz.thr5;

public class D2 implements Runnable {
    private ThreadSocket ts;
    private int x = 0;
    public D2(ThreadSocket ts) {
        this.ts = ts;
    }
    @Override
    public void run() {
        while(true) {
           ts.getSock();
        }
    }
}
