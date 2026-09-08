package com.huangjiabin.a01_gc;


/**
 查看gc日志参数：-XX:+PrintGCDetails -Xms256m -Xmx256m
 Parallel GC（并行垃圾回收器，jdk8默认）：PSYoungGen年轻代 + ParOldGen老年代
    优点：最求高吞吐，多线程并行的垃圾回收器。缺点：STW停顿时间长。
 [GC (Allocation Failure) [PSYoungGen: 65375K->875K(76288K)] 65375K->883K(251392K), 0.0025660 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 gc范围：GC（新生代回收）、Full GC（全堆整体回收）
 gc原因：Allocation Failure（内存分配失败）、System.gc()（代码调用）、Ergonomics（JVM 主动调优）
 gc目标：PSYoungGen（年轻代）、ParOldGen（老年代）、Metaspace（元空间）、没前缀的代表整个堆
 gc效果：65375K->875K(76288K)（回收前占用->回收后占用(容量)）
 gc停顿时间STW：0.0025660 secs（本次 GC 只用了 2.5 毫秒，极快）
 gc详细时间：user用户态cpu耗时（gc线程）、sys内核态cpu耗时（系统调用、io）、real实际耗时（会四舍五入/格式化不精确）
 注意：日志里写 0% used 是日志做了取整省略，实际不是真的 0，是几乎为空
 输出：
 === 开始运行 ===
 [GC (Allocation Failure) [PSYoungGen: 65375K->875K(76288K)] 65375K->883K(251392K), 0.0025660 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 65648K->888K(76288K)] 65656K->896K(251392K), 0.0012470 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 65943K->840K(76288K)] 65951K->848K(251392K), 0.0011231 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 65608K->872K(76288K)] 65616K->888K(251392K), 0.0005923 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 65649K->840K(76288K)] 65665K->856K(251392K), 0.0010011 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 65623K->872K(86016K)] 65639K->888K(261120K), 0.0007848 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 85503K->32K(85504K)] 85519K->761K(260608K), 0.0007772 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 === 主动建议执行 Full GC ===
 [GC (System.gc()) [PSYoungGen: 45751K->32K(85504K)] 46480K->761K(260608K), 0.0007014 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [Full GC (System.gc()) [PSYoungGen: 32K->0K(85504K)] [ParOldGen: 729K->679K(175104K)] 761K->679K(260608K), [Metaspace: 3328K->3328K(1056768K)], 0.0056838 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 [GC (Allocation Failure) [PSYoungGen: 83583K->32K(85504K)] 84262K->711K(260608K), 0.0002725 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 83609K->32K(85504K)] 84289K->711K(260608K), 0.0007734 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 [GC (Allocation Failure) [PSYoungGen: 83613K->32K(85504K)] 84292K->711K(260608K), 0.0005203 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 === 程序结束 ===
 Heap
 PSYoungGen      total 85504K, used 60718K [0x00000000fab00000, 0x0000000100000000, 0x0000000100000000)
 eden space 83968K, 72% used [0x00000000fab00000,0x00000000fe643be0,0x00000000ffd00000)
 from space 1536K, 2% used [0x00000000ffd00000,0x00000000ffd08000,0x00000000ffe80000)
 to   space 1536K, 0% used [0x00000000ffe80000,0x00000000ffe80000,0x0000000100000000)
 ParOldGen       total 175104K, used 679K [0x00000000f0000000, 0x00000000fab00000, 0x00000000fab00000)
 object space 175104K, 0% used [0x00000000f0000000,0x00000000f00a9df0,0x00000000fab00000)
 Metaspace       used 3336K, capacity 4500K, committed 4864K, reserved 1056768K
 class space    used 363K, capacity 388K, committed 512K, reserved 1048576K
*/
public class GcDemo {

    // 1、占用内存的大对象1MB
    static class BigObj {
        private byte[] data = new byte[1024 * 1024];
    }

    public static void main(String[] args) {
        System.out.println("=== 开始运行 ===");

        // 2、循环创建大量临时对象，制造 GC 垃圾
        for (int i = 0; i < 500; i++) {
            new BigObj();
        }

        System.out.println("=== 主动建议执行 Full GC ===");
        // 3、建议 JVM 执行 Full GC（只是建议，不保证立刻执行）
        System.gc();

        // 4、再创建一波对象，触发年轻代 Minor GC
        for (int i = 0; i < 300; i++) {
            new BigObj();
        }

        System.out.println("=== 程序结束 ===");
    }
}
