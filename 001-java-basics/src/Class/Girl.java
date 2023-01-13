package Class;

public class Girl {
    String name;
    double weight;
    Mom m = new Mom();
    public void love(Boy l){
        System.out.println("的男朋友叫:"+l.name+"他的身高是:"+l.height);
        l.buy();
    }

    public Girl(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public void tall(){
        m.say();
    }
}
