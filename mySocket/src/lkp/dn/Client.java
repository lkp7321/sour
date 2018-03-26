package lkp.dn;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("127.0.0.1", 9999);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write("我爱你");
            bw.newLine();
            bw.flush();
        socket.shutdownOutput();
        BufferedReader brs = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String re = brs.readLine();
        System.out.println(re);
        socket.close();
    }
}
