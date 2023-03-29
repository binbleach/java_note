package _07composite.nagtive;

import java.util.Stack;
/*
    为什么出现stack.get(0) ="b"先进先出，明明栈是先进后出的呀,因为Stack继承了 Vector方法，被迫重写了get
*/
public class TestStack {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        stack.push("d");
        //不是栈，先进先出
        System.out.println(stack.get(0));
        //是栈，先进后厨
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
