package XuanZhePaiXu;

public class test {
    public static void main(String[] args) {
        int []arr = {2,4,3,5,1,6,7,9,8,0};
        for(int i = 0;i<arr.length-1;i++){
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]<arr[j]){
                    int temp;
                    temp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
