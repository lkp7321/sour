package lkp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP协议接受数据
 * @author lz130
 */
public class DataReceive {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(9099);
        DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
        datagramSocket.receive(datagramPacket);
        String message = new String(datagramPacket.getData(),0,1024);
        String ip = datagramPacket.getAddress().getHostAddress();
        System.out.println("从地址："+ip+" 接受到数据："+message);
    }
}
