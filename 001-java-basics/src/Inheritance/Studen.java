package Inheritance;

class Student extends Person {
    int id;
    public void tedian(){
        Person l = new Person();
        l.age=200;
        System.out.println("学生要学习");
        System.out.println(l.age);
    }
}

