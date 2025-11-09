public class NQueensFixed {

    static int N;               // Size of chessboard (N x N)
    static int[][] board;       // Board to store queen placements (1 = queen, 0 = empty)
    static int fixedRow, fixedCol;  // Position of the first fixed queen

    // Function to check if placing a queen at (row, col) is safe
    public static boolean isSafe(int row, int col) {

        // ✅ Check vertical column above
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1)  // Another queen exists in the same column
                return false;
        }

        // ✅ Check upper-left diagonal (↖ direction)
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1)    // Queen found on left diagonal
                return false;
        }

        // ✅ Check upper-right diagonal (↗ direction)
        for (int i = row - 1, j = col + 1; i >= 0 && j < N; i--, j++) {
            if (board[i][j] == 1)    // Queen found on right diagonal
                return false;
        }

        // If no conflict detected, safe to place queen here
        return true;
    }

    // Backtracking function to place queens row by row
    public static boolean solve(int row) {

        // If this row already has the first fixed queen, skip it
        if (row == fixedRow)
            return solve(row + 1);

        // ✅ Base Case: If all queens are placed (reached beyond last row)
        if (row == N)
            return true;

        // Try placing queen in each column of the current row
        for (int col = 0; col < N; col++) {

            // Check if current position is safe for queen
            if (isSafe(row, col)) {
                board[row][col] = 1;        // Place queen

                // Recur to place queen in next row
                if (solve(row + 1))
                    return true;            // If successful → stop & return

                board[row][col] = 0;        // ❌ Backtrack: remove queen & try next column
            }
        }

        // If queen cannot be placed in any column of this row → return false
        return false;
    }

    public static void main(String[] args) {

        N = 8;                        // 8x8 board (you can change this value)
        board = new int[N][N];        // Initialize chessboard with zeros

        // ✅ Fix the first queen at a given position
        fixedRow = 0;
        fixedCol = 0;
        board[fixedRow][fixedCol] = 1;  // Place first queen manually

        // Start solving from row 0
        if (solve(0)) {
            System.out.println("Solution Found:");
            
            // Print the board
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++)
                    System.out.print(board[i][j] + " ");
                System.out.println();
            }

        } else {
            System.out.println("No solution exists with first queen fixed at (" + fixedRow + "," + fixedCol + ")");
        }
    }
}

/*Below is a complete, exam-ready explanation of the code including:

✔ Code Workflow & Step-by-Step Execution (Dry Run)
✔ Backtracking Explanation
✔ isSafe() Logic Explanation
✔ Time & Space Complexity
✔ Edge Cases & Output Behavior
✔ Optional Viva Questions

---

## ✅ **N–Queens Problem (Fixed Queen Version) – Full Explanation**

### 📌 **Problem Statement**

Place N queens on an N×N chessboard such that no two queens attack each other.
Here, the **first queen's row and column are fixed**, and we must place the remaining N–1 queens using **Backtracking**.

---

## 🧠 **Core Concepts Used**

| Concept           | Usage in Code                                     |
| ----------------- | ------------------------------------------------- |
| Backtracking      | Tries all possibilities and backtracks on failure |
| Recursion         | Repeatedly calls `solve()` for next rows          |
| 2D Array          | Stores queen positions: 1 = queen, 0 = empty      |
| Constraints Check | `isSafe()` ensures no attack between queens       |

---

## 🔍 **How the Code Works (Step-by-Step)**

### **1. Fixed Queen Placement**

* The first queen is manually placed at position `(fixedRow, fixedCol)`.
* The algorithm skips this row during recursive placing.

### **2. Backtracking (solve function)**

For each row:

1. Try every column from 0 to N−1
2. Check if placing a queen is safe using `isSafe()`
3. If safe → place queen and recursively solve for next row
4. If recursion returns false → remove queen (BACKTRACK) and try next column
5. If no column works → return false to previous row

### **3. Base Case**

When `row == N`, all queens are placed successfully → return true

---

## ♟️ **isSafe() Function Explanation**

When placing a queen at `(row, col)`, we check:

| Check                  | Meaning                       |
| ---------------------- | ----------------------------- |
| Column Upwards         | No queen in same column above |
| Upper Left Diagonal ↖  | No queen on left diagonal     |
| Upper Right Diagonal ↗ | No queen on right diagonal    |

We check **only above** because we place row by row from top to bottom, so no need to check below.

---

## 🧪 **Mini Dry Run (for 4×4 case with fixed queen at (0,0))**

Board initially:

```
Q . . .
. . . .
. . . .
. . . .
```

* Row0 is fixed → skip to row1
* Try row1: col0 (unsafe), col1 (safe) → place
* Try row2: col0 (unsafe), col1 (unsafe), col2 (safe) → place
* Try row3: col0 (unsafe), col1 (unsafe), col2 (unsafe), col3 (safe) → place
  ✅ Solution found

---

## ⏳ **Time & Space Complexity**

| Complexity | Value     | Why                                                   |
| ---------- | --------- | ----------------------------------------------------- |
| **Time**   | **O(N!)** | Backtracking tries multiple placements and backtracks |
| **Space**  | **O(N²)** | 2D board of size N×N + recursion stack                |

Even with fixed queen, worst case remains **O(N!)**
Fixing one queen reduces search space slightly but does not change complexity type.

---

## 🧯 **Edge Cases**

| Case                                 | Output                         |
| ------------------------------------ | ------------------------------ |
| Fixed queen at invalid position      | No solution                    |
| Fixed queen blocks all possibilities | Backtracking returns false     |
| N < 4                                | No valid solution (except N=1) |

---

## 📤 **Output Format**

If solution exists:
Prints the board with 1 = queen, 0 = empty

If not:
`No solution exists with first queen fixed at (x,y)`

---

## 🎤 Possible Viva Questions & Answers

**Q1: Why do we use Backtracking for N-Queens?**
Because it systematically explores all possible placements and backtracks when a partial solution cannot lead to a valid full solution.

**Q2: Why don’t we check rows below in isSafe()?**
We place queens row-wise top to bottom, so lower rows are empty; only previous rows matter.

**Q3: What happens if we remove the fixed queen logic?**
Then the algorithm becomes a standard N-Queens solver without constraints.

**Q4: Time Complexity?**
O(N!) because each queen has N choices and recursion explores all possibilities.

---

Would you like me to also provide a **diagram explanation** of safe checks (vertical + diagonal) to help you explain to external examiner?

Reply: **Yes diagram** or **No**
*/
