package Army;

public class Army {
    weapon w [];
    Army(int max){
        w = new weapon [max];

    }

    void addWeapon(weapon wa) throws AddweaponException {
        for(int i=0;i<w.length;i++){
            if(null == w[i]){
                w[i]=wa;
            }
        }
        throw new AddweaponException("武器库满了");
    }

    void attackAll(){
        for(int i =0;i<w.length;i++){
            if(w[i] instanceof attackable){
                attackable a = (attackable)w[i];
                a.attack();
            }
        }
    }
    void moveAll(){
        for(int i =0;i<w.length;i++){
            if(w[i] instanceof moveable){
                moveable a = (moveable)w[i];
                a.move();
            }
        }
    }
}
