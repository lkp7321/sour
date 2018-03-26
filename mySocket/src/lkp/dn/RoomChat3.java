package lkp.dn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP
 * 使用键盘录入
 * 接收
 */
public class RoomChat3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        BufferedWriter bwServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bwServer.write("成功");
        bwServer.newLine();
        bwServer.flush();
        socket.close();
    }
}
