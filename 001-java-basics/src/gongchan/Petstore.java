package gongchan;

public class Petstore {

    static Animal getanimal(String a){

        if ("猫".equals(a)){
            return new Cat();
        }
        return new Dog();
    }
}
