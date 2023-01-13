package base;

public class Array2Test {

    public static void main(String[] args) {
        //二维数组的创建方式第一：
        int [][] arr = {{1,2,3},{2,3,4}};
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                System.out.print(arr[i][j]);
            }
        }

        int [][] a;
        a =new int [3][10];
        a[1][2]=9;
        System.out.println(a[1][2]);

        System.out.println();

        //二维数组创建方式二
        int [][] arr1 = new int[2][];
        arr1[0] = new int []{1,2,3};
        arr1[1] = new int[]{2,3,4};
        for(int[]i:arr1){
            for(int num:i){
                System.out.print(num);
            }
        }

         System.out.println();

        //二维数组创建第三种：
        int [][]arr3;
        arr3=new int[][]{{1,2,3},{2,3,4}};
        for (int[] ints : arr3) {
            for (int i : ints) {
                System.out.print(i);
            }
        }

    }

}
