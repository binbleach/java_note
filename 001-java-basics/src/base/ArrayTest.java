package base;

public class ArrayTest {
    public static void main(String[] args){
        //数组创建的第一种方法
        int[] arr = {1,2,3,4};
        System.out.println(arr[0]);
        //第二种方法
        int[] arr2 = new int [] {2,3,4,5};
        System.out.println(arr2[0]);
        //第三种
        int[] arr3 = new int[3];    //值为{0，0，0}
        System.out.println(arr3[1]);
        //数组的历遍，普通
        for (int i = 0; i < arr3.length; i++) {
            System.out.print(arr3[i]);
        }
        //数组遍历，升级
        System.out.println();
        for(int num:arr){
            System.out.println(num);
        }

    }
}
