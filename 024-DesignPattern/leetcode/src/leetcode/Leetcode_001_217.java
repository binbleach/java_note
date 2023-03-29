package leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*
    给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
*/
public class Leetcode_001_217 {
    public static void main(String[] args) {
        int [] arr = new int[100000];
        for(int i =1;i<100000;i++){
            arr[i] = i;
        }
        arr[0] = 99999;
        long b = System.currentTimeMillis();
        System.out.println(containsDuplicate3(arr));
        long e = System.currentTimeMillis();
        System.out.println("耗时："+(e-b));

    }

    //自己，垃圾
    public static boolean containsDuplicate(int[] nums) {
        Set set = new HashSet();
        for(int n:nums){
            set.add(n);
        }
        if(set.size()!=nums.length){
            return true;
        }else {
            return false;
        }
    }
    //官方排序，最优
    public static boolean containsDuplicate2(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }

    //官方哈希表，次优
    public static boolean containsDuplicate3(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int x : nums) {
            if (!set.add(x)) {
                return true;
            }
        }
        return false;
    }
}
