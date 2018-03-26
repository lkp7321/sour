package com.lkp.io.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 文件的获取
 * 高级获取（过滤）
 */
public class MyFile2 {
    public static void main(String[] args) {
        File file = new File("zz.txt");
        // 绝对路径
        System.out.println("getAbsolutePath："+file.getAbsolutePath());
        // 相对路径
        System.out.println("getPath："+file.getPath());
        // 文件名称
        System.out.println("getName："+file.getName());
        // 文件字节长度
        System.out.println("length："+file.length());
        // 文件最后修改时间
        System.out.println("lastModified："+file.lastModified());

        // 获取指定路径下的，文件或目录数组
        File files = new File("E:\\");
        String[] strs = files.list();
        for (String str : strs
             ) {
            System.out.println("目录："+str);
        }
        // 获取指定路径下的File数组
        File[] files1 = files.listFiles();
        for (File file1 : files1
             ) {
            System.out.println("File目录："+file1.getAbsolutePath());
        }
        // 文件获取高级
        String[] strings = files.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (new File(dir, name).isFile() && name.endsWith(".java"));
            }
        });
        for (String s : strings
             ) {
            System.out.println("dgfd："+s);
        }
    }
}
