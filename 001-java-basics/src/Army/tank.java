package Army;

public class tank extends weapon implements attackable,moveable {
    public void attack(){
        System.out.println("坦克开始攻击");
    }
    public void move(){
        System.out.println("坦克开始移动");
    }
}
