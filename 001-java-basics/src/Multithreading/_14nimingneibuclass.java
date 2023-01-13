package Multithreading;

public class _14nimingneibuclass {
    public static void main(String[] args) {
        Thread mythread = new Thread(new Runnable(){//匿名内部类
            public void run(){
                for(int i =0;i<1000;i++){
                    System.out.println("支线线程-->"+i);
                }
            }
        });

        mythread.start();
        for(int i =0;i<1000;i++){
            System.out.println("主线线程-->"+i);
        }
    }
}
