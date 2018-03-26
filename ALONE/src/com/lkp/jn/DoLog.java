package com.lkp.jn;

import java.io.*;

public class DoLog extends GetSome {
    public void code() throws IOException{
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("test.txt"));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("test4.txt",true));
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = bufferedInputStream.read(bytes)) != -1){
            bufferedOutputStream.write(bytes,0,len);
            bufferedOutputStream.flush();
        }
        bufferedOutputStream.close();
    }
}
