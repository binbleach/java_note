package _05demeter.nagtive;

/*
    person知道的太多了，应该只提供关机方法。其实就是封装原理
*/
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
}
class Person{
    private Computers computers;
    public void offComputers(){
        computers.closeFile();
        computers.closeScreen();
        computers.powerOff();
    }
}
