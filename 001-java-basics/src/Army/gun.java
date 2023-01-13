package Army;

public class gun extends weapon implements moveable,attackable {
    @Override
    public void attack() {
        System.out.println("枪支开始射击");
    }

    @Override
    public void move() {
        System.out.println("枪支开始移动");
    }
}
