
/*✅ 0/1 Knapsack – Tabulation (Bottom-Up Dynamic Programming)
❗ Problem Summary:

You are given:
val[] → profit/value array
wt[] → weight array
W → maximum capacity of knapsack
You must pick items to maximize profit
0/1 means: either take full item or don’t take item at all (no fractions allowed)

🧠 Key DP Idea

We build a DP table where:

dp[i][j] = Maximum profit using first i items and capacity j

Matrix dimension: (n+1) × (W+1)

Row → number of items considered
Column → capacity from 0 to W

📌 Code Breakdown
1. Initialize DP Array
int dp[][] = new int[n+1][W+1];


Why n+1 and W+1?
Because we include a row for 0 items and column for 0 capacity

Base case:
If capacity = 0 → profit = 0
If items = 0 → profit = 0

Hence:

dp[i][0] = 0;  
dp[0][j] = 0;

2. Filling the DP Table (Main Logic)

For each item i and capacity j:

if(w <= j) {  
    // Item can be included
    incprofit = v + dp[i-1][j-w]; // include
    excprofit = dp[i-1][j];       // exclude
    dp[i][j] = Math.max(incprofit, excprofit);
} else {
    // Cannot include item
    dp[i][j] = dp[i-1][j];
}


Meaning:

Case	Action
If item weight <= current capacity	We have choice → include or exclude
If item weight > current capacity	Must exclude item
✅ Example Used in Code
val[] = {15,14,10,45,30}
wt[]  = {2, 5, 1, 3, 4}
W = 7


Number of items = 5

🔍 Dry Run Table (Important)

Let’s fill DP table step-by-step (showing the main changes)

i\W                 0	1	2	3	4	5	6	7
0	                0	0	0	0	0	0	0	0
1 (wt=2,val=15)	    0	0	15	15	15	15	15	15
2 (wt=5,val=14)	    0	0	15	15	15	15vs14=15	29	29
3 (wt=1,val=10)	    0	10	15	25	25	25	25	39
4 (wt=3,val=45)	    0	10	15	45	55	60	70	70
5 (wt=4,val=30)	    0	10	15	45	55	60	70	75
✅ Final Answer: 75

Which items chosen?
Items with profit 45 (wt 3), 10 (wt 1), 15 (wt 2), 5th item partially? No partial — 30
 (wt 4) instead of 15+10? DP picks best combo = 45 + 30 = 75.

🧠 How to Think (Short Intuition)

At each step, we ask:

If I include this item, do I get more profit than excluding it?

That’s it!

DP stores answers for smaller subproblems, and we reuse them — avoiding recursion overhead.

📈 Time & Space Complexity
Complexity	Value	Why
Time	O(n × W)	Filling DP table of size (n+1)*(W+1)
Space	O(n × W)	Storing DP table

Can be optimized to O(W) using 1D DP array.

🏆 Viva-Ready 30-Second Answer

“0/1 Knapsack is solved using Dynamic Programming because greedy fails. 
We create a DP table where dp[i][j] represents the maximum profit using 
first i items and capacity j. For each item, we check if we include it
 or exclude it. If the item weight is less than equal to capacity, 
 we compute include profit as value + dp[i-1][capacity - weight], 
 and exclude profit as dp[i-1][capacity]. The max of these is stored.
  Time complexity is O(nW) and space is O(nW).”

🧠 Explanation (Short & Easy)

We create a DP table where dp[i][j] stores the maximum profit using first i items and capacity j.

For each item, we either:
✅ include it (if weight allows)
❌ or exclude it
We take the maximum of both choices.

⏱️ Time & Space Complexity
Complexity	Value	Reason
Time	O(n × W)	Nested loop for items & capacity
Space	O(n × W)	DP table of size (n+1) × (W+1)
✅ When to Use This Method?

Use Dynamic Programming when:
Items cannot be divided (0/1 nature)
Want maximum profit with weight constraint
Greedy will not give optimal result
*/
public class zerooneknapsack {
    // function to solve 0-1 knapsack using DP
    public static int knapsack(int val[], int wt[], int W, int n){

        //dp[i][j] = max profit using first i items and capacity j
        int[][] dp = new int[n + 1][W + 1];

        //Initialize 0th row and 0th column with 0 (base case)
        for(int i=0; i<=n; i++){
            dp[i][0] = 0;
        }
        for(int j = 0; j<=W; j++){
            dp[0][j] = 0;
        }

        //build dp table 

        for(int i=1; i<=n; i++){
            for(int j=1; j<=W; j++){
                int v = val[i-1]; // value of ith item
                int w = wt[i-1]; // weight of ith item

                if(w<=j){  // item can be included
                    int includeprofit = v + dp[i-1][j-w];
                    int excludeprofit = dp[i - 1][j];
                    dp[i][j] = Math.max(includeprofit,excludeprofit);

                }else{  // cannot include this item
                    int excludeprofit = dp[i - 1][j];
                    dp[i][j] = excludeprofit;
                }
            }
        }

        return dp[n][W]; // maximum profit for n items & capacity W
    }
     public static void main(String[] args) {
        int[] val = {15, 14, 10, 45, 30};
        int[] wt =  { 2,  5,  1,  3,  4};
        int W = 7; // Knapsack capacity

        int maxProfit = knapsack(val, wt, W, val.length);
        System.out.println("Maximum Profit: " + maxProfit);
    }
}