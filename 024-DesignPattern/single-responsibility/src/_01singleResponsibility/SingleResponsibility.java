package _01singleResponsibility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class SingleResponsibility {
    public static void main(String[] args) {
        //需求：统计文本文件中有多少个单词
        //反例
        negative();
        try {
            //正例
            positive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //反例
    public static void negative(){
        try {
            //=========================负责加载文件
            Reader in = new FileReader("024-DesignPattern/1.txt");
            BufferedReader br = new BufferedReader(in);
            String line ;
            StringBuilder sb = new StringBuilder("");
            while ((line =br.readLine())!=null){
                sb.append(line);
            }
            //==========================负责获取单词数量
            String [] words = sb.toString().split("[^a-zA-Z]+");
            System.out.println(words.length);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //正例
    public static void positive() throws IOException {
        String str = loadFile("024-DesignPattern/1.txt");
        String regex ="[^a-zA-Z]+";
        System.out.println(wordCount(str,regex));
    }
    //==============只负责加载文件
    public static   String loadFile(String path) throws IOException {
        Reader in = new FileReader(path);
        BufferedReader br = new BufferedReader(in);
        String line;
        StringBuilder sb = new StringBuilder("");
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    //=========只负责计算单词数量
    public static int wordCount(String sb, String regex){
        String [] words = sb.split(regex);
        return  words.length;
    }
}
