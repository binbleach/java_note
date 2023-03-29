package _05demeter.positive;

class Computers{
    public  void  closeFile(){
        System.out.println("关闭文件");
    }
    public  void  closeScreen(){
        System.out.println("关闭屏幕");
    }
    public  void  powerOff(){
        System.out.println("断电");
    }
    public void offComputers(){
        closeFile();
        closeScreen();
        powerOff();
    }
}
class Person{
    private Computers computers;
    public void offComputers(){
        computers.offComputers();
    }
}
