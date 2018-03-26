package lkp.bn;

import java.net.DatagramSocket;
import java.net.SocketException;

public class ChatRoom {
    public static void main(String[] args) throws SocketException {
        DatagramSocket dsSend = new DatagramSocket();
        DatagramSocket dsReceive = new DatagramSocket(12306);

        SendUdp sendUdp = new SendUdp(dsSend);
        ReceiveUdp receiveUdp = new ReceiveUdp(dsReceive);

        Thread t1 = new Thread(sendUdp);
        Thread t2 = new Thread(receiveUdp);

        t1.start();
        t2.start();


    }
}
