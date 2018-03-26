package com.lkp.io;
import java.io.*;

/**
 * JAVA 字节流
 */
public class ByteInput {
    public static void main(String[] args) throws IOException {
//        System.out.println("读字节流，1");
//        byteInput1();
//        System.out.println("读字节流，2");
//        byteInput2();
//        System.out.println("写节流，1");
//        byteOutput1();
//        System.out.println("读节缓冲流，1");
//        byteBuffer1();
        System.out.println("写字节缓冲流，1");
        byteBufferOutput1();
    }

    /**
     * 字节输入流
     * @throws IOException
     */
    private static void byteInput1() throws IOException {
        InputStream inputStream = new FileInputStream("test.txt");
        int i = 0;
        while ((i = inputStream.read()) != -1){
            System.out.println((char)i+"");
        }
        inputStream.close();
    }

    /**
     * 字节输入流2
     * @throws IOException
     */
    private static void byteInput2() throws  IOException {
        InputStream inputStream = new FileInputStream("test.txt");
        byte[] bytes = new byte[3];
        int i = 0;
        while ((i = inputStream.read(bytes)) != -1){
            System.out.println(new String(bytes,0,i));
        }
        inputStream.close();
    }

    /**
     * 字节输出流
     * @throws IOException
     */
    private static void byteOutput1() throws IOException {
        OutputStream outputStream = new FileOutputStream("test2.txt",true);
        outputStream.write("ABCD".getBytes());
        outputStream.close();
    }

    /**
     * 字节（缓存）输入流
     * @throws IOException
     */
    private static void byteBuffer1() throws IOException {
        InputStream inputStream = new FileInputStream("test2.txt");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[5];
        int len = 0;
        while((len = bufferedInputStream.read(bytes)) != -1){
            System.out.println(new String(bytes,0,len));
        }
        bufferedInputStream.close();
    }

    /**
     * 字节缓存输出流
     * @throws FileNotFoundException
     */
    private static void byteBufferOutput1() throws IOException {
        OutputStream outputStream = new FileOutputStream("test2.txt",true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write("asgfdfsagegertgweadg哦地方idfo".getBytes());
        bufferedOutputStream.close();
    }
}
