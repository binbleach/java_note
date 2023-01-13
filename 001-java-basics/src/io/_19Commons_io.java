package io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class _19Commons_io {
    public static void main(String[] args) {
        File srcFile = new File("io/commons-io-1.txt");
        File destFile = new File("io/commons-io-2.txt");

        try {
            //复制文件
            FileUtils.copyFile(srcFile,destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
