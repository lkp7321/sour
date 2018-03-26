package lkp.dn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * 监听UDP协议的端口
 */
public class RoomChat {
    public static void main(String[] args) throws IOException {
        // 发送数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket datagramSocket = null;
                try {
                    datagramSocket = new DatagramSocket();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    String len = null;
                    while ((len = bufferedReader.readLine()) != null) {
                        byte[] bytes = len.getBytes();
                        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("127.0.0.1"), 9096);
                        datagramSocket.send(datagramPacket);
                    }
                }catch (IOException e) {
                        e.printStackTrace();
                }
                datagramSocket.close();
            }
        }){}.start();
        // 接收
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket datagramSocket = null;
                try {
                    datagramSocket = new DatagramSocket(9096);
                    while (true) {
                        DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
                        datagramSocket.receive(datagramPacket);
                        String message = new String(datagramPacket.getData(),0,1024);
                        String ip = datagramPacket.getAddress().getHostAddress();
                        System.out.println("从地址："+ip+" 接收到数据："+message);
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }){}.start();
    }
}
