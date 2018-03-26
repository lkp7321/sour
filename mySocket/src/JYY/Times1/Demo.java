package JYY.Times1;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器
 * @author lz130
 */
public class Demo {
    /**
     * 三秒之后执行，输出完就关闭。
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule( new TimerTask(){
            @Override
            public void run() {
                System.out.println("bang bang 咯");
                timer.cancel();
            }
        }
        ,
        3000
        );
    }
}
class Demo1 {
    /**
     * 每隔三秒输出
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask(){
                    @Override
                    public void run() {
                        System.out.println("bang bang 咯");
                        System.out.println(new Date());
                        //timer.cancel();
                    }
                },
                0,
                3000
        );
    }
}
