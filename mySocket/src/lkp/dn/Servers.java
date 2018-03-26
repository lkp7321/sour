package lkp.dn;

import java.io.*;
import java.net.Socket;

public class Servers implements Runnable {
    private Socket s;
    public Servers(Socket s) {
        this.s = s;
    }
    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = null;
            while((line = br.readLine()) != null) {
                System.out.println("Receive："+line);
            }
            BufferedWriter bwServer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bwServer.write("Return：成功");
            bwServer.newLine();
            bwServer.flush();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
