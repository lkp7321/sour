package lkp.cn;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * TCP
 * 接收数据
 */
public class ServerDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        // 监听
        Socket socket = serverSocket.accept();// 监听并接收到次套接字的连接，此方发在连接传入之前一直阻塞
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        String ss = new String(bytes, 0, len);
        System.out.println(ss);
        // 释放资源
        socket.close();
        // 此对象不能被关闭
        // serverSocket.close();
//        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("b");
//        list.add("e");
//        list.add("b");
//        for (int i = 0; i <list.size() ; i++) {
//            if(!list.get(i).equals("b")){
//                continue;
//            }else{
//                list.remove(i);
//            }
//        }
//        System.out.println(list.toString());
    }
}
