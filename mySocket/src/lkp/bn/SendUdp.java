package lkp.bn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * 发送端UDP
 * @author lz130
 */
public class SendUdp implements Runnable{
    private DatagramSocket datagramSocket;
    public SendUdp(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }

    @Override
    public void run(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                byte[] bys = line.getBytes();
                // 打包数据
                DatagramPacket dataGramPacket = new DatagramPacket(bys, bys.length, InetAddress.getByName("10.25.88.53"), 12306);
                // 发送数据包
                datagramSocket.send(dataGramPacket);
            }
            // 释放资源
            datagramSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
