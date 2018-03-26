package lkp.an;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * UDP
 * 发送端
 * @author lz130
 */
public class UdpDemo {
    public static void main(String[] args) throws IOException {
        // 创建发送端socket对象
        // 数据包Socket 对象
        DatagramSocket dgs = new DatagramSocket();
        // 创建数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            byte[] bys = line.getBytes();
            // 打包数据
            DatagramPacket dataGramPacket = new DatagramPacket(bys, bys.length, InetAddress.getByName("10.25.88.53"), 10086);
            // 发送数据包
            dgs.send(dataGramPacket);
        }
        // 释放资源
        dgs.close();
    }
}
