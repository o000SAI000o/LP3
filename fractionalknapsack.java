/*✅ Problem :
Given n items where each item i has value v[i] and weight w[i], and a knapsack with capacity W, 
pick items (you can take fractions) to maximize total value.

Key difference:
    0/1 Knapsack: item must be taken whole (NP-hard; DP O(nW) or O(n·cap)).

Fractional Knapsack: can take portions of items → Greedy is optimal.

💡 Greedy Idea (Why it works):
    Compute value density (ratio) r[i] = v[i]/w[i].
    Sort items by r[i] descending.
    Take items from highest ratio down:
        If whole item fits, take it.
        Otherwise, take the fraction that fits the remaining capacity.

Why optimal?
This is a classic “exchange argument”: any solution that contains a lower ratio weight 
while leaving out a higher ratio weight (or portion of it) can be improved by exchanging them. 
So the greedy choice is safe at each step.
*/

/*🧠 How the Code Works (Simple Explanation)
Step	What Happens?
1	We calculate value/weight ratio for each item
2	Sort items so the highest ratio item comes first
3	Start adding items to the bag: take full item if it fits
4	If not enough space, take fractional part of the item
5	Stop when bag becomes full

Greedy Choice:
Always take item with highest value per kg first because it gives maximum gain.

🔍 Dry Run of Example

Items:

Item	Value	Weight	Ratio
1	    60	    10	    6.0
2	    100	    20	    5.0
3	    120	    30	    4.0

Capacity = 50

Step-by-step:
Step	              Choice	    Bag Used	Value Gained	Total Value
Take Item 1 fully	  10	        10/50	    60	            60
Take Item 2 fully	  20	        30/50	    100	            160
Bag space left = 20	   —	           —	    —	            —
Take 20/30 of Item 3  20	    50/50	  120×(20/30)=80	240

✅ Final Answer = 240
⏱️ Time & Space Complexity
Approach	        Time Complexity	                Reason
Sorting + Greedy	O(n log n)	            Sorting items by ratio
Space	            O(1) or O(n)         No extra DS except array (already given)

💡 Why Greedy Works Here?

Because we are allowed to take fractions, and value increases linearly with weight.
So taking the highest "value/kg" first always gives the best return.

This logic does NOT work for 0/1 Knapsack, which requires DP.

*/

import java.util.Arrays;

public class fractionalknapsack {
    static class Item{
        int value,weight;
        double ratio;

        Item(int value, int weight){
            this.value = value;
            this.weight = weight;
            this.ratio = (double)value/weight;
        }
    }

    public static double maxprofit(Item[] items, int capacity){
        //step 1: sort items by value/weight ratio in descending order
        Arrays.sort(items, (a,b) -> Double.compare(b.ratio,a.ratio) );
        
        double totalvalue = 0.0;

        //step 2: pick items greedily
        for(Item it: items){
            if(capacity == 0) break;
            
            if(it.weight <= capacity){
                //take full item
                totalvalue += it.value;
                capacity -= it.weight;

            } else {
                //take fraction ot item
                double fraction = (double)capacity / it.weight;
                totalvalue += it.value * fraction;
                capacity = 0;
            }
        }
        return totalvalue;
    }

    public static void main(String args[]){
        Item[] items = {
            new Item(60, 10),
            new Item(100, 20),
            new Item(120, 30)
        };
        int capacity = 50;

        System.out.println("Maximum value: " + maxprofit(items, capacity));

    }
}