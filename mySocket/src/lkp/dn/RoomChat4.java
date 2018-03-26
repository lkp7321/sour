package lkp.dn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程改进TCP
 */
public class RoomChat4 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while(true) {
            Socket s = serverSocket.accept();
            new Thread(new Servers(s)).start();
        }
    }
}
