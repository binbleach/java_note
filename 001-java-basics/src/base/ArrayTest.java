package base;

public class ArrayTest {
    public static void main(String[] args){
        //数组创建的第一种方法
        int[] arr = {1,2,3,4};
        System.out.println();
        System.out.println(arr[0]);
        //第二种方法
        int[] arr2 = new int [] {2,3,4,5};
        System.out.println(arr2[0]);
        //第三种
        int[] arr3 = new int[3];
        System.out.println(arr3[1]);
        //数组的历遍，普通
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        //数组遍历，升级
        System.out.println();
        for(int num:arr){
            System.out.println(num);
        }

        System.out.println("=====================");
        int[] nums={1,0,3,4,0,5,0,0,2};
        int j = 0;
        for(int i =0;i<nums.length;i++){
            if(nums[i]!=0){
                if(i!=j){
                    nums[j]=nums[i];
                    nums[i]=0;
                }
                j++;
            }
        }
        for (int num : nums) {
            System.out.println(num);
        }
        System.out.println("=======================");

    }
}
