import javax.swing.*;
import java.awt.*;

public class GraphVisualizerUI {

    public static JTextArea pathArea = new JTextArea(5, 20); // shared path display

    public static void main(String[] args) {
        JFrame frame = new JFrame("Graph Algorithm Visualizer");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel graphPanel = new JPanel(null);
        graphPanel.setBackground(Color.WHITE);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(30, 30, 30));
        sidePanel.setPreferredSize(new Dimension(200, 0));

        String[] algorithms = {
            "Input Graph", "DFS", "BFS", "Dijkstra", "Prim", "Kruskal"
        };

        for (String algo : algorithms) {
            JButton button = new JButton(algo);
            button.setMaximumSize(new Dimension(180, 40));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.addActionListener(e -> {
                switch (algo) {
                    case "Input Graph" -> GraphUtils.handleInputGraph(graphPanel);
                    case "DFS" -> GraphUtils.runDFS(graphPanel);
                    case "BFS" -> GraphUtils.runBFS(graphPanel);
                    case "Dijkstra" -> GraphUtils.runDijkstra(graphPanel);
                    case "Prim" -> GraphUtils.runPrim(graphPanel);
                    case "Kruskal" -> GraphUtils.runKruskal(graphPanel);
                }
            });
            sidePanel.add(Box.createVerticalStrut(10));
            sidePanel.add(button);
        }

        JButton clear = new JButton("Clear");
        clear.setBackground(new Color(178, 34, 34));
        clear.setForeground(Color.WHITE);
        clear.setFont(new Font("Arial", Font.BOLD, 14));
        clear.setMaximumSize(new Dimension(180, 40));
        clear.setAlignmentX(Component.CENTER_ALIGNMENT);
        clear.addActionListener(e -> {
            graphPanel.removeAll();
            graphPanel.repaint();
            pathArea.setText(""); // Clear path info
        });

        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(clear);

        // 📋 Path & Legend Panel
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setPreferredSize(new Dimension(250, 150));
        legendPanel.setBackground(new Color(245, 245, 245));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Legend & Path"));

        JLabel startLabel = new JLabel("🟦 Start Node (Cyan)");
        JLabel goalLabel = new JLabel("🟥 Goal Node (Pink)");
        JLabel visitLabel = new JLabel("🟨 Visited Node (Yellow)");

        startLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        goalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        visitLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        pathArea.setEditable(false);
        pathArea.setLineWrap(true);
        pathArea.setWrapStyleWord(true);
        pathArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane pathScroll = new JScrollPane(pathArea);

        legendPanel.add(startLabel);
        legendPanel.add(goalLabel);
        legendPanel.add(visitLabel);
        legendPanel.add(Box.createVerticalStrut(10));
        legendPanel.add(new JLabel("🛣️ Traversal Path:"));
        legendPanel.add(pathScroll);

        frame.add(sidePanel, BorderLayout.WEST);
        frame.add(graphPanel, BorderLayout.CENTER);
        frame.add(legendPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }
}
