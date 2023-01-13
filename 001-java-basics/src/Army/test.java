package Army;

public class test {
    public static void main(String[] args) {
        Army c = new Army(2);
        gun gun = new gun();
        tank tank = new tank();
        gun gun2 = new gun();

        try {
            c.addWeapon(gun);
            c.addWeapon(tank);
            c.addWeapon(gun2);
        } catch (AddweaponException e) {
            System.out.println(e.getMessage());
        }

        c.attackAll();
        c.moveAll();
    }
}
