package com.lkp.io.file;

import java.io.File;

/**
 * File ： 文件和路径名的抽象表现形式
 * File(pathName)：根据路径得到一个file对象
 * File(String parent, String child)：根据一个目录和一个子文件得到一个对象
 * mkdir()：创建一个文件夹，如果存在就不创建了
 * createNewFile()：创建一个文件，如果存在就不创建了
 * delete()：删除文件
 * renameTo()：修改，
 *   如果修改后的文件路径与被修改路径相同，则修改名称，
 *   如果修改后的文件路径与被修改路径相同，则剪贴此文件并修改名称为目标文件。
 */
public class MyFile {
    public static void main(String[] args) {
//        // 抽象的文件对象，并不一定存在。
//        // File file1 = new File("C:\\myDemo\\oo\\a.txt");
//        // File file2 = new File("C:\\myDemo\\oo","a.txt");
//        // 创建AA文件夹
//        File file = new File("C:\\Users\\lz130\\Desktop\\myDemo\\oo\\AA\\a.txt");
//        // file.mkdir();
//        // 创建AA下的 a.txt
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 创建文件夹及文件
//        File files= new File("C:\\Users\\lz130\\Desktop\\myDemo\\ee\\bb\\a.txt");
//        files.mkdirs();
//        System.out.println("lalaa");

        // 在项目中创建文件夹
        File filesr= new File("aaa\\bbb\\ccc");
        filesr.mkdirs();
        System.out.println("lalaa");
        // 删除文件"ccc"
        File filesr1= new File("aaa\\bbb\\ccc");
        filesr.delete();
        //修改名称
        File filesr2 = new File("aaa\\bbb");
        File filesr3 = new File("aaa\\ccc");
        filesr2.renameTo(filesr3);
    }
}
