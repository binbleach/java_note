package com.huangjiabin.a01_gc;

import java.util.ArrayList;
import java.util.List;

/**

 输出：
 [GC (Allocation Failure) [PSYoungGen: 65375K->10139K(76288K)] 65375K->62372K(251392K), 0.0175528 secs] [Times: user=0.03 sys=0.03, real=0.02 secs]
 [GC (Allocation Failure) [PSYoungGen: 74913K->10184K(76288K)] 127145K->126937K(251392K), 0.0188787 secs] [Times: user=0.06 sys=0.03, real=0.02 secs]
 [Full GC (Ergonomics) [PSYoungGen: 10184K->0K(76288K)] [ParOldGen: 116753K->126636K(175104K)] 126937K->126636K(251392K), [Metaspace: 3328K->3328K(1056768K)], 0.0203885 secs] [Times: user=0.00 sys=0.02, real=0.02 secs]
 [Full GC (Ergonomics) [PSYoungGen: 65055K->15360K(76288K)] [ParOldGen: 126636K->174766K(175104K)] 191692K->190126K(251392K), [Metaspace: 3328K->3328K(1056768K)], 0.0168492 secs] [Times: user=0.00 sys=0.00, real=0.02 secs]
 [Full GC (Ergonomics) [PSYoungGen: 65327K->62794K(76288K)] [ParOldGen: 174766K->174766K(175104K)] 240093K->237560K(251392K), [Metaspace: 3836K->3836K(1056768K)], 0.0171346 secs] [Times: user=0.06 sys=0.08, real=0.02 secs]
 [Full GC (Ergonomics) [PSYoungGen: 65536K->64842K(76288K)] [ParOldGen: 174766K->174766K(175104K)] 240302K->239608K(251392K), [Metaspace: 3836K->3836K(1056768K)], 0.0069382 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 [Full GC (Allocation Failure) [PSYoungGen: 64842K->64808K(76288K)] [ParOldGen: 174766K->174747K(175104K)] 239608K->239556K(251392K), [Metaspace: 3836K->3836K(1056768K)], 0.0120951 secs] [Times: user=0.05 sys=0.11, real=0.01 secs]
 Heap
 PSYoungGen      total 76288K, used 65536K [0x00000000fab00000, 0x0000000100000000, 0x0000000100000000)
 eden space 65536K, 100% used [0x00000000fab00000,0x00000000feb00000,0x00000000feb00000)
 from space 10752K, 0% used [0x00000000ff580000,0x00000000ff580000,0x0000000100000000)
 to   space 10752K, 0% used [0x00000000feb00000,0x00000000feb00000,0x00000000ff580000)
 ParOldGen       total 175104K, used 174751K [0x00000000f0000000, 0x00000000fab00000, 0x00000000fab00000)
 object space 175104K, 99% used [0x00000000f0000000,0x00000000faaa7f50,0x00000000fab00000)
 Metaspace       used 3869K, capacity 4536K, committed 4864K, reserved 1056768K
 class space    used 424K, capacity 428K, committed 512K, reserved 1048576K
 Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 at com.huangjiabin.yh.minifast.a01_gc.GcOldDemo$BigObj.init(GcOldDemo.java:8)
 at com.huangjiabin.yh.minifast.a01_gc.GcOldDemo.main(GcOldDemo.java:16)
 */
public class GcOldDemo {
    static class BigObj {
        byte[] buf = new byte[1024 * 1024];
    }

    public static void main(String[] args) throws InterruptedException {
        List<BigObj> list = new ArrayList<>();

        // 持续往集合放对象，GC 无法回收，慢慢塞满老年代
        while (true) {
            boolean add = list.add(new BigObj());
            Thread.sleep(10);
        }
    }
}
