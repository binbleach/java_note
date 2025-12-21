package huangjiabin;

import huangjiabin.tool.TimeTool;

public class _01fibonacci {
    /*
    *   斐波那契数列
    *   0 1 1 2 3 5 8 13 ...
    *   对比两个算法 fib和 fib2，当index越大时fib等待时间越久，index为50时该fib算法耗时42秒几乎不可用。而fib2耗时约为0秒
    *   判断算法优劣：
    *       1、事后统计法：写测试代码执行相同条件测试耗时，又麻烦又难以保证代码的公正性
    *       2、估算时间复杂度、空间复杂度：大O表示法，忽略常数、低阶去估算
    *   大O表示法：
    *       9               >>  O(1)
    *       2n+3            >>  O(n)
    *       n²+2n+6         >>  O(n²)
    *       4n³+3n²+22n+100 >>  O(n³)
    *       log₂(n)         >>  O(logn)
    *       3*log₂(n)+2*nlog₂(n) >>  O(nlogn)
    *       O(1) < O(logn) < O(n) < O(nlogn) < O(n²) < O(n³) < O(2ⁿ) < O(n!) < O(nⁿ)
    *   对数的细节：
    *       log₂n = log₂9 * log₉n 忽略常数的情况下 log₂n 和 log₉n 的时间复杂度是一样的，都称为logn
    *   回看两个斐波那契数列算法：
    *       fib的复杂度为：O(2ⁿ)，fib2的时间复杂度为：O(n)，fib3的时间复杂度为：O(1)
    */
    //递归解决，时间复杂度是：O(2ⁿ)
    public static int fib(int index){
        if(index < 2) return index;
        return fib(index - 1)+ fib(index - 2);
    }
    //循环解决，时间复杂度是：O(n)
    public static int fib2(int index){
        if(index < 2) return index;
        int firstNum = 0;
        int secondNum = 1;
        while (index-- > 1){
            secondNum += firstNum;
            firstNum = secondNum - firstNum;
        }
        return secondNum;
    }
    //利用数学模型去解决，时间复杂度是：O(1)
    public static int fib3(int index){
        double sqrt = Math.sqrt(5);
        return (int)((Math.pow((1+sqrt)/2,index)-Math.pow((1-sqrt)/2,index))/sqrt);
    }

    public static void main(String[] args) {
        int index = 6;
        TimeTool.check("fib", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib(index));
            }
        });
        TimeTool.check("fib2", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib2(index));
            }
        });
        TimeTool.check("fib3", new TimeTool.Task() {
            @Override
            public void execute() {
                System.out.println(fib3(index));
            }
        });
    }
}
