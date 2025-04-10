# 🔍 Graph Algorithm Visualizer

An interactive Java-based application to visualize popular graph algorithms with animations, color-coded traversal, and path display.

Built using **Java Swing**, this tool helps users (and students) understand how algorithms like **BFS, DFS, Dijkstra, Prim, and Kruskal** work step-by-step on custom input graphs.

---

## ✨ Features

- 🟦 **Input your own graph** (weighted or unweighted)
- 🔘 **Circular nodes** with clean borders
- 🔁 **Directed edges** with arrowheads
- 🔢 **Edge weights** shown clearly beside edges
- 🟡 **Animated traversal** for:
  - Breadth-First Search (BFS)
  - Depth-First Search (DFS)
  - Dijkstra’s Algorithm
  - Prim’s Minimum Spanning Tree
  - Kruskal’s Minimum Spanning Tree
- 🧭 **Start/Goal node color legend**
- 📋 **Path output** shown in a live legend panel

---

## 🧰 Tech Stack

- Language: Java (JDK 17+ recommended)
- UI: Java Swing
- Tools: JPanel, JFrame, Timer animation

---

## 🚀 How to Run Locally

### 1. Clone the Repository
```bash
git clone https://github.com/BhaveshNikam09/debug_visualizer.git
cd debug_visualizer
```

### 2. Compile the Java Files
```bash
javac GraphVisualizerUI.java GraphUtils.java GraphViseFullScreen.java
```

### 3. Run the Application
```bash
java GraphViseFullScreen
```

✅ The app window will open with controls on the left and visualization in the center.

---

---

## 🧪 Sample Input

```txt
0 1 4; 0 2 3; 1 3 2; 2 3 5
```

- This means:
  - Edge 0 → 1 with weight 4
  - Edge 0 → 2 with weight 3
  - ...

---

## 🧠 Algorithm Legend

| Color       | Meaning               |
|-------------|------------------------|
| 🟦 Cyan      | Start Node            |
| 🟥 Pink      | Goal Node             |
| 🟨 Yellow    | Visited Node          |
| 🟩 Green     | Traversed Edge/Node   |

---

## 📁 Folder Structure

```
📦 debug_visualizer/
├── GraphUtils.java
├── GraphVisualizerUI.java
├── GraphViseFullScreen.java
└── README.md
```

---

## 🙌 Acknowledgements

Created by **Bhavesh Nikam** for academic demo purposes  
With help from ChatGPT 🧠⚡

---

## 📢 License

MIT License – free to use, share, and remix

