package com.huangjiabin.a05_位图_qq号去重;

/**
  位图：十进制转为2进制，进行标记。非常适合海量数据存在性判断、去重、签到统计等场景；不是数字或者范围特别大或者重复的不适合
  1L = ... 00000001
  1L << 2 = ... 0000100 = 4
  |（按位或）： 位数上1、0为1。(1|1=1; 1|2=3; 1|3=3)
  &（按位与）： 位数上1、1为1。(1&1=1; 1&2=0; 1&3=1)
  ~（按位取反）：1 =0；0 =1
  long：默认值为0L。
  Long：默认值为null。
  计算机中负数用补码表示，负数的补码是反码加上1，正数的反码是本身。用反码是为了加减运算，用补码是为了消除-0和+0的浪费
  1 byte = 8 bit 有符号位存储范围-128~127，无符号位0-255，共256位
  1 byte（字节）= 1 boolean = 1/2 char = 1/2 short = 1/4 int = 1/4 float = 1/8 long = 1/8 double
  中文在utf-8中占3字节，在GBK中占2字节
 */
public class Bitmap {
    private long[] bits = new long[1];

    public static void main(String[] args) {
        Bitmap bitmap = new Bitmap();
        bitmap.add(29010381);
        System.out.println("add："+bitmap.bits);
        System.out.println(bitmap.bits[453287]);    //十进制
        System.out.println(Long.toBinaryString(bitmap.bits[453287]));   //二进制
        System.out.println("290103815_contains："+bitmap.contains(29010382));
        System.out.println("290103814_contains："+bitmap.contains(29010381));
        bitmap.remove(290103814);
        System.out.println("remove："+bitmap.bits);
        System.out.println("remove："+bitmap.contains(290103814));
    }

    public void add(int num){
        if(num < 0 ){
            throw new IllegalArgumentException("必须是正整数");
        }
        /**
            long[] bits = 一栋楼，每层有64个房间（为什么是64因为：1long = 8byte = 64bit）
            每个 long = 一层
            每个 bit 位 = 一个房间
            房间亮灯 (1) → 有人
            房间熄灯 (0) → 没人
        */
        //1、算出是那一层楼
        int index = num / 64;
        //2、扩容
        if(index >= bits.length){
            long[] newBits = new long[index + 1];
            System.arraycopy(bits,0,newBits,0,bits.length);
            bits = newBits;
        }
        //3、算出是几号房
        int bitPosition = num % 64;
        //4、标记入住（原理就是按位或：位置上只要有一个为1就是1）
        bits[index] |= (1L << bitPosition);
    }

    public void remove(int num){
        if(num < 0 ) return;
        int index = num / 64;
        if(index > bits.length) return;
        int bitPosition = num % 64;
        //4、移除标记（原理就是按位与：位置上只要有一个为0就是0）
        bits[index] &= ~(1L << bitPosition);
    }

    public boolean contains(int num){
        if(num < 0 ) return false;
        int index = num / 64;
        if(index > bits.length) return false;
        int bitPosition = num % 64;
        return (bits[index] & (1L << bitPosition)) != 0; // 不为0代表存在
    }
}
