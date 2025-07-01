import java.util.Arrays;

public class RemoveElement27 {
    static int removeELement(int[] nums, int val){
        int k=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i] != val){
                nums[k]= nums[i];
                k++;
            }
        }
        return k;
    }
    public static void main(String[] args) {
        
        int[] nums = {1,2,2,3,4,5,6,7};
        int val = 2;
        System.out.println(removeELement(nums,val));
        System.out.println(Arrays.toString(nums));


    }
    
}
