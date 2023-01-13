package exception;

public class Interview {
    public static int m (){
        int i = 100;
        try{
             return 1;  //返回的是100
        }finally{
            ++i;
            System.out.println("哈哈哈哈");
        }
        // 以上至下的语法规则
        // return最后执行的语法规则
            /*
            为了解决两个矛盾他的编译是这样的
            i= 100；
            j= i;
            i++;
            return j;
             */
    }

    public static void main(String[] args) {
        System.out.println(Interview.m());
    }
}

