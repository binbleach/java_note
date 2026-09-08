package Multithreading;

public class InterruptExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("线程开始睡眠");
                Thread.sleep(5000); // 睡眠5秒
                System.out.println("线程睡眠结束");
            } catch (InterruptedException e) {
                System.out.println("线程在睡眠时被中断");
                // 恢复中断状态
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        try {
            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt(); // 中断子线程
    }
}
