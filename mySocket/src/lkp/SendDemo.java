package lkp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * TCP 协议发送数据
 */
public class SendDemo {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 9099);
            OutputStream os = socket.getOutputStream();
            os.write("xxxis即将发生地哦啊".getBytes());
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            String rMessage = new String(bytes,0, is.read(bytes));
            System.out.println("消息发送成功，回应我："+rMessage);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
