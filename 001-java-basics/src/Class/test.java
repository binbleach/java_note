package Class;


import java.sql.Timestamp;
import java.util.*;

public class test {
    public static void main(String[] args) {
        Girl g = new Girl("关晓彤",100);
        Boy l = new Boy("鹿晗",175);
        Boy l2 = new Boy("鹿晗",180);
        l=l2;
        g.love(l);
        g.tall();
        NotChange notChangeA = new NotChange("A");
        NotChange notChangeB = new NotChange("B");
        NotChange notChangeC = new NotChange("C");
        notChangeA=notChangeC;
        notChangeB=notChangeC;
        System.out.println(notChangeA==notChangeB);


        //下面使无关的
        Man man = new Man();
        man.setHuman();
        Soldier soldier = new Soldier();
        soldier.setHuman(man);
        System.out.println(soldier.getHuman().getThinking());

        Date date = new Date();
        Timestamp nousedate = new Timestamp(date.getTime());
        System.out.println(nousedate);
        System.out.println();

        if("1" ==null){
            System.out.println("1" ==null);
        }

    }
}
