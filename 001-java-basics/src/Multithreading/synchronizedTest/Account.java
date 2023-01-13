package Multithreading.synchronizedTest;

//银行账户
public class Account {

    private String name; //账户名字
    private double balance;  //账户余额

    void withdraw(double money){
        /*一个对象一个锁，拿了锁才能进*/
        synchronized(this){         /*传进去的很重要必须是两个线程共享的对象*/
            double newBalance=this.getBalance()-money;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.setBalance(newBalance);
            System.out.println(getName()+"账户取走"+money+"还剩:"+getBalance());
        }
    }

    /*synchronized用在方法上锁的一定是this 不灵活还扩大了范围*/
    /*synchronized void  withdraw(double money){ //取款这里锁住了因为只new了一个Account对象只有一把锁
        double newBalance=this.getBalance()-money;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setBalance(newBalance);
        System.out.println(getName()+"账户取走"+money+"还剩:"+getBalance());
    }*/

    /*-----------------------------------------------*/

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

}
