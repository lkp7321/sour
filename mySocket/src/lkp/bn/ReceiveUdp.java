package lkp.bn;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveUdp implements Runnable {
    private DatagramSocket datagramSocket;
    public ReceiveUdp(DatagramSocket datagramSocket){
        this.datagramSocket = datagramSocket;
    }
    @Override
    public void run() {
        try {
            while (true) {
                byte[] bys = new byte[1024];
                DatagramPacket dgp = new DatagramPacket(bys, bys.length);
                datagramSocket.receive(dgp);
                // 解析数据包
                byte[] bys1 = dgp.getData();
                String dau = new String(bys1, 0, bys.length);
                System.out.println("发送地址：" +  dgp.getAddress().getHostAddress() + " ,发送内容：" + dau);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
