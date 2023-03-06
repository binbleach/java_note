package Lambda;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

/*
    总结：
        1、函数式接口：接口中只有一个抽象方法的，接口一般用@FunctionalInterface注释
            1）Consumer  :  消费型接口，可以传参消费
            2）Function  :  计算型接口，可以用于计算和转换
            3）Predicate :  判断型接口，可以用于判断，返回值式boolean
            4）Supplier  :  生产型接口，可以用于生产，不能传参
        2、 lambda可以简化函数式接口的匿名内部类.
        3、 lambda省略规则：1）参数类型可以省略。 2）方法体只有一句话的话，return和大括号{} 可以省略
                          3）方法只有一个参数的时候，小括号也是可以省略的。
                          4）记不住就省略不记，用快捷键 alt + 回车 选择 replace with lambda 快速简化匿名内部类。
        4、方法引用：
            1）重写方法的时候方法体只有一行代码，且代码是某个静态方法调用，
                且重写方法的参数和静态方法调用的参数完全一致则可以使用。格式：类名::方法名
            2）重写方法的时候方法体只有一行代码，且代码是第一个参数的成员方法调用，
                且重写方法剩余参数和成员方法调用参数完成一致则可使用。格式：类名::方法名
            3）重写方法的时候方法体只有一行代码，且代码是某个对象的成员方法调用，
                且重写方法的参数和成员方法调用参数完成一致则可使用。格式：对象名::方法名
            快捷键：alt+回车选择replace lambda with method reference
        5、构造器引用：
            1）重写方法的时候方法体只有一行代码，且代码是调用了某个类的构造器
                且重写方法的参数和调用构造器的参数完全一致则可以使用。格式：类名::new
*/
public class LambdaMain {
    public static void main(String[] args) {
        test01();
        test02();
        test03((left, right) -> left + right);
        test04(value -> value%2 ==0);
        test04(value -> value%2 ==0,value -> value>3);
        test05( s -> Integer.parseInt((String) s));
        test06(value -> System.out.print(value));
    }

    // 01 匿名内部类，不省略
    public static void test01 (){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("test 01 方法执行...");
            }
        }).start();
    }

    /* 02 lambda简化匿名内部类（函数式接口的才能简化）
        可以 alt + 回车 选择 replace with lambda 快速简化匿名内部类*/
    public static void test02(){
        new Thread(() ->
            System.out.println("test 02 方法执行...")
        ).start();
    }

    /* 03 IntBinaryOperator 匿名调用 入两个int 返一个int
     */
    public static void test03(IntBinaryOperator operator) {
        int a = 10;
        int b = 20;
        System.out.println(operator.applyAsInt(a, b));
    }

    // 04 IntPredicate 匿名调用 入int返回 boolean
    public static void test04(IntPredicate predicate) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : arr) {
            if (predicate.test(i)) {
                System.out.print(i);
            }
        }
        System.out.println();
    }

    // 04 IntPredicate 的 and 方法使用
    public static void test04(IntPredicate predicate,IntPredicate predicate1) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : arr) {
            if (predicate.and(predicate1).test(i)) {
                System.out.print(i);
            }
        }
        System.out.println();
    }

    // 05 Function 匿名调用，入和返可控制
    public static<T,R> void test05(Function<T, R> function) {
        String str = "1235";
        R result = function.apply((T) str);
        System.out.println(result);
    }

    //05 IntConsumer 匿名调用
    public static void test06(IntConsumer consumer) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i : arr) {
            consumer.accept(i);
        }
        System.out.println();
    }
}
