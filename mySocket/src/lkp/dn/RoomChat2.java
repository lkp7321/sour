package lkp.dn;

import java.io.*;
import java.net.Socket;

/**
 * TCP
 * 使用键盘录入
 * 发送
 * PS：在使用此协议上传文件的时候在获取反馈的时候会阻塞，使用
 */
public class RoomChat2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",9999);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        socket.shutdownOutput();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = br.readLine();
        System.out.println("反馈的消息："+s);
        bufferedReader.close();
        socket.close();
    }
}
