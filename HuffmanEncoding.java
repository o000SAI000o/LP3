import java.util.PriorityQueue;
import java.util.Comparator;

// Node structure for Huffman Tree
class Node {
    char ch;            // Character
    int freq;           // Frequency
    Node left, right;   // Left and right child

    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }

    Node(int freq, Node left, Node right) {
        this.ch = '-';  // Internal nodes do not store characters
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}

public class HuffmanEncoding {

    // Recursive function to print Huffman Codes
    public static void printCodes(Node root, String code) {
        if (root == null) return;

        // If leaf node reached, print character and its code
        if (root.left == null && root.right == null) {
            System.out.println(root.ch + " : " + code);
            return;
        }

        printCodes(root.left, code + "0");
        printCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
        char[] chars = {'A', 'B', 'C', 'D', 'E'};
        int[] freq   = {  5,   9,  12,  13, 16};

        // Min-Heap based on frequency (Greedy choice: pick lowest two)
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.freq));

        // Step 1: Create leaf nodes and push to Min-Heap
        for (int i = 0; i < chars.length; i++) {
            pq.add(new Node(chars[i], freq[i]));
        }

        // Step 2: Build Huffman Tree
        while (pq.size() > 1) {
            Node left = pq.poll();    // Least freq node
            Node right = pq.poll();   // Second least freq node

            Node newNode = new Node(left.freq + right.freq, left, right);
            pq.add(newNode); // Push combined node back to heap
        }

        Node root = pq.poll(); // Root of Huffman Tree

        System.out.println("Huffman Codes:");
        printCodes(root, "");
    }
}

/* 

Below is a **complete, simple, and in-depth explanation** of the Huffman Encoding code I provided earlier.
This will help you **understand, remember, and explain in your viva**.

---

# ✅ **Huffman Encoding – Full Explanation of Code**

### 📍 **Goal of Huffman Encoding**

Huffman Encoding is a **lossless compression technique** that assigns *shorter 
binary codes to frequent characters* and *longer codes to less frequent ones*.
It ensures **minimum total cost of encoding** by using a **Greedy Strategy**.

---

## 📌 **Understanding the Components of the Code**

### ### 1️⃣ Node Class (Tree Structure)

```java
class Node {
    char ch;
    int freq;
    Node left, right;

    Node(char ch, int freq) {
        this.ch = ch;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }

    Node(int freq, Node left, Node right) {
        this.ch = '-';
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}
```

**Purpose:**
Represents a node in the Huffman Tree.

There are two types of nodes:

| Type          | Stores                | When Used                          |
| ------------- | --------------------- | ---------------------------------- |
| Leaf Node     | Character & Frequency | For original characters (A,B,C...) |
| Internal Node | `-` & sum of freq     | Created during merging             |

---

### 2️⃣ Min-Heap (PriorityQueue)

```java
PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.freq));
```

* A **Min-Heap** is used because we repeatedly need the **two lowest-frequency nodes**.
* Greedy choice: pick the smallest two each time.

---

### 3️⃣ Add Characters to Heap

```java
for (int i = 0; i < chars.length; i++) {
    pq.add(new Node(chars[i], freq[i]));
}
```

Each character with its frequency is inserted as a separate node.

---

### 4️⃣ Build Huffman Tree (Main Greedy Part)

```java
while (pq.size() > 1) {
    Node left = pq.poll();    // lowest freq
    Node right = pq.poll();   // second lowest

    Node newNode = new Node(left.freq + right.freq, left, right);
    pq.add(newNode);
}
```

This loop executes until only **one node remains** (root of tree).

Process:

| Step   | Action                                                    |
| ------ | --------------------------------------------------------- |
| 1      | Remove two smallest nodes                                 |
| 2      | Combine them → create parent node with sum of frequencies |
| 3      | Insert parent back in heap                                |
| Repeat | Until only root remains                                   |

This greedy approach ensures **minimum weighted path** → optimal encoding.

---

### 5️⃣ Traversing Tree to Get Codes

```java
public static void printCodes(Node root, String code) {
    if (root == null) return;

    if (root.left == null && root.right == null) {
        System.out.println(root.ch + " : " + code);
        return;
    }

    printCodes(root.left, code + "0");
    printCodes(root.right, code + "1");
}
```

* Left edge → assign `"0"`
* Right edge → assign `"1"`
* When a leaf node is reached → print the code

This function performs a **DFS traversal** to generate binary codes.

---

### 6️⃣ Main Function

```java
public static void main(String[] args) {
    char[] chars = {'A', 'B', 'C', 'D', 'E'};
    int[] freq   = {  5,   9,  12,  13, 16};

    ...
    printCodes(root, "");
}
```

### Output Example:

```
Huffman Codes:
E : 0
A : 100
B : 101
C : 110
D : 111
```

**Higher frequency = shorter code**.
Example: E(16) → `0` (short) because it’s most frequent.

---

## 🔥 Why Is Huffman Encoding Greedy?

Because at every step we make the optimal choice:

> *Always pick the two smallest frequency nodes first.*

This ensures minimal cost without backtracking — classic greedy approach.

---

## ⏱️ Time & Space Complexity

| Stage           | Complexity     | Explanation            |
| --------------- | -------------- | ---------------------- |
| Building heap   | O(n)           | n insertions           |
| Each merge step | O(log n)       | heap insertion/removal |
| Total merges    | (n − 1)        | One per merge          |
| **Total Time**  | **O(n log n)** | Efficient              |
| **Space**       | **O(n)**       | Tree + codes storage   |

---

## 🧠 Mini Dry Run (Quick Understanding)

Characters: A(5), B(9), C(12), D(13), E(16)

| Step                             | Action |
| -------------------------------- | ------ |
| Pick 5 & 9 → merge = 14          |        |
| PQ now has: 12, 13, 14, 16       |        |
| Pick 12 & 13 → merge = 25        |        |
| PQ now: 14, 16, 25               |        |
| Pick 14 & 16 → merge = 30        |        |
| PQ now: 25, 30                   |        |
| Pick 25 & 30 → merge = 55 → root |        |

Then assign bits using tree traversal.

---

## 🏁 20-Second Viva Answer to Memorize

> Huffman Encoding is a lossless compression algorithm that assigns shorter binary codes to more frequent characters. It uses a greedy strategy by repeatedly selecting the two nodes with the smallest frequency and merging them into a new node. A Huffman tree is formed, and codes are generated by traversing left as ‘0’ and right as ‘1’. It takes O(n log n) time using a min-heap and produces optimal prefix-free codes.

---

Would you like:

A) A Java version that **encodes and decodes a full string**?
B) A **diagram of the Huffman tree** for the above example?
*/