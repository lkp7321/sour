package com.lkp.io;

import java.io.*;

/**
 * JAVA 字符流
 */
public class CharInput {
    public static void main(String[] args) throws IOException {
//        //字符流输入
//        System.out.println("字节转字符");
//        charInput();
//        System.out.println("");
//        System.out.println("+++++++++++++++++++++++++");
//        //一次读多个字符
//        System.out.println("字节转字符2");
//        charInput2();
//        //字符流3
//        charInput3();
//        //字符缓冲
//        charInput4();
//        //line
//        charInput5();
        //charOut();
        charOut2();
        charOut3();
        charOut4();
    }

    /**
     * 字节转字符流，一次读一个字符
     * @throws IOException
     */
    private static void charInput() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("test2.txt"));
        int is = 0;
        while ((is = inputStreamReader.read()) != -1){
            System.out.print((char) is);
        }
        inputStreamReader.close();
    }

    /**
     * 字节转字符流，一次读一个字符数组
     * @throws IOException
     */
    private static void charInput2() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("test2.txt"));
        char[] chars = new char[2];
        int is = 0;
        while ((is = inputStreamReader.read(chars)) != -1){
            System.out.println(new String(chars,0,is));
        }
        inputStreamReader.close();
    }

    /**
     * 字符流
     * @throws IOException
     */
    private static void charInput3() throws IOException {
        FileReader fileReader = new FileReader("test2.txt");
        char[] chars = new char[4];
        int is = 0;
        while ((is = fileReader.read(chars)) != -1){
            System.out.println(new String(chars,0,is));
        }
        fileReader.close();
    }

    /**
     * 字符缓冲流
     * @throws IOException
     */
    private static void charInput4() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("test2.txt"));
        String bufferReaderString = null;
        while ((bufferReaderString = bufferedReader.readLine())!= null){
            System.out.println(bufferReaderString);
            System.out.println("啦啦啦");
        }
        bufferedReader.close();
    }

    /**
     * 有行标的字符流
     * @throws IOException
     */
    private static void charInput5() throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader("test2.txt"));
        lineNumberReader.setLineNumber(1);
        String str = null;
        while ((str = lineNumberReader.readLine()) != null){
            System.out.println(str);
        }
        lineNumberReader.close();
    }

    /**
     * 字符输出，字节转字符
     * @throws IOException
     */
    private static void charOut() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("test3.txt"));
        outputStreamWriter.write("我我我爱爱爱你你你");
        outputStreamWriter.close();
    }

    /**
     * 字符输出，字节转字符
     * @throws IOException
     */
    private static void charOut2() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("test3.txt",true));
        char[] chars = {'啦','啦','啦','啦'};
        outputStreamWriter.write(chars,0,chars.length-2);
        outputStreamWriter.close();
    }

    /**
     * 输出
     * @throws IOException
     */
    private static void charOut3() throws IOException {
        FileWriter fileWriter = new FileWriter("test3.txt",true);
        fileWriter.write("我和你，心连心，同住地球村");
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * 缓冲
     * @throws IOException
     */
    private static void charOut4() throws  IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("test3.txt",true));
        bufferedWriter.write("dsf发动文革额文化馆色弱发公司的发挥更大打发噶是");
        bufferedWriter.newLine();
        bufferedWriter.write("dahfiuiudf的发生变化速度");
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
