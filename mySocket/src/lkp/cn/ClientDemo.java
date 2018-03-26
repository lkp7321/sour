package lkp.cn;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * TCP
 * 发送数据
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("10.25.88.53", 8888);
        OutputStream os = socket.getOutputStream();
        os.write("XXX我爱你".getBytes());
        socket.close();
    }
}
