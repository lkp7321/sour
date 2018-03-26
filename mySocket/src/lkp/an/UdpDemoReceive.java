package lkp.an;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP
 * 接收端
 * 创建一个接收端socket对象
 * 创建一个数据包
 * 接收数据
 * 解析数据包
 * 释放资源
 *
 */
public class UdpDemoReceive {
    public static void main(String[] args) throws IOException {
        DatagramSocket dgs = new DatagramSocket(10086);
        while (true) {
            byte[] bys = new byte[1024];
            DatagramPacket dgp = new DatagramPacket(bys, bys.length);
            dgs.receive(dgp);
            // 解析数据包
            String dau = new String(dgp.getData(), 0, bys.length);
            System.out.println("发送地址：" + dgp.getAddress().getHostAddress() + " ,发送内容：" + dau);
        }
        // 释放资源，一般情况：当接收端开启后就不需要关闭了。
        // dgs.close();
    }
}
