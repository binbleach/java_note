package stack;

public class test {
    public static void main(String[] args) {
        Stack a = new Stack();
        try {
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            a.push(new Object());
            //开始错
            a.push(new Object());
        } catch (StackException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        try {
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            a.bod();
            //
            a.bod();
        } catch (StackException e) {
            System.out.println(e.getMessage());
        }
    }
}
