package lkp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP 协议接收数据
 */
public class ReceiveDemo {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9099);
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = is.read(bytes);
            String rString = new String(bytes,0,len);
            System.out.println("接收到的数据："+rString);
            OutputStream os = socket.getOutputStream();
            os.write("你吃屎啦 ！".getBytes());
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
