import java.util.*;

public class Fibo{
    //recc approach

    
    /*
     * ------------------ RECURSIVE APPROACH ------------------
     * Recurrence Relation: F(n) = F(n-1) + F(n-2)
     *
     * Time Complexity:  O(2^n)
     * Reason: Each function call makes 2 more calls → exponential growth
     *
     * Space Complexity: O(n)
     * Reason: Recursive call stack depth goes up to n
     */


    public static int Reccfibo(int n){
        if(n == 0 || n == 1){
            return n;
        }
        return Reccfibo(n-1) + Reccfibo(n-2);
    }

    //non-recc approach , Iterative approach

    
    /*
     * ------------------ NON-RECURSIVE APPROACH ------------------
     * (Dynamic Programming - Tabulation / Iterative Method)
     *
     * Time Complexity:  O(n)
     * Reason: Single loop runs from 2 to n
     *
     * Space Complexity: O(n)
     * Reason: We store Fibonacci values in an array of size n
     *
     * NOTE: Can be optimized to O(1) space by storing only last 2 values
     */

     
    public static int Iterfibo(int m, int nums[]){
        nums[0] = 0;
        nums[1] = 1;
        for(int i=2; i<nums.length; i++){
            nums[i] = nums[i-1] + nums[i-2];
        }
        return nums[m];
    }

    public static void main(String args[]){
        Scanner s = new Scanner(System.in);

        System.out.print("Enter n for Recursive Fibonacci: ");
        int n = s.nextInt();
        System.out.println("Recursive Fibonacci of " + n + " = " + Reccfibo(n));

        System.out.print("Enter m for Iterative Fibonacci: ");
        int m = s.nextInt();
        int nums[] = new int[m + 1];
        System.out.println("Iterative Fibonacci of " + m + " = " + Iterfibo(m, nums));

        System.out.print("Fibonacci Sequence: ");
        for (int num : nums) {
            System.out.print(num + " ");
        }
        
        s.close(); 
    }
}