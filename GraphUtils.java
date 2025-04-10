import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.Queue;

public class GraphUtils {

    private static final int NODE_RADIUS = 30;
    private static List<Edge> edges = new ArrayList<>();
    private static Map<Integer, Point> nodePositions = new HashMap<>();
    private static boolean isWeighted = false;

    static class Edge {
        int from, to, weight;
        public Edge(int f, int t, int w) {
            from = f; to = t; weight = w;
        }
    }

    public static void handleInputGraph(JPanel panel) {
        panel.removeAll();
        edges.clear();
        nodePositions.clear();

        int choice = JOptionPane.showOptionDialog(null, "Is the graph weighted?",
                "Graph Type", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Yes", "No"}, "Yes");

        isWeighted = (choice == JOptionPane.YES_OPTION);

        JTextArea area = new JTextArea(10, 30);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(400, 200));

        int result = JOptionPane.showConfirmDialog(null, scroll, "Enter edges (e.g. 0 1 4; 1 2 5)",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String input = area.getText();
            edges = parseInput(input);
            nodePositions = placeNodes(panel.getWidth(), panel.getHeight(), getAllNodes());
            drawGraph(panel, null, null);
            GraphVisualizerUI.pathArea.setText(""); // Clear path legend
        }
    }

    public static void runBFS(JPanel panel) {
        Set<Integer> visited = new HashSet<>();
        List<Edge> seenEdges = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        int start = edges.get(0).from;

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        path.add(start);

        javax.swing.Timer timer = new javax.swing.Timer(700, null);
        timer.addActionListener(e -> {
            if (!queue.isEmpty()) {
                int curr = queue.poll();
                for (Edge ed : edges) {
                    if (ed.from == curr && !visited.contains(ed.to)) {
                        visited.add(ed.to);
                        seenEdges.add(ed);
                        path.add(ed.to);
                        queue.add(ed.to);

                        GraphVisualizerUI.pathArea.setText("Path: " + String.join(" → ",
                            path.stream().map(String::valueOf).toList()));
                        break;
                    }
                }
                drawGraph(panel, seenEdges, path);
            } else {
                timer.stop();
            }
        });
        timer.start();
    }

    public static void runDFS(JPanel panel) {
        Set<Integer> visited = new HashSet<>();
        List<Edge> seenEdges = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        int start = edges.get(0).from;

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited.add(start);
        path.add(start);

        javax.swing.Timer timer = new javax.swing.Timer(700, null);
        timer.addActionListener(e -> {
            if (!stack.isEmpty()) {
                int curr = stack.pop();
                for (int i = edges.size() - 1; i >= 0; i--) {
                    Edge ed = edges.get(i);
                    if (ed.from == curr && !visited.contains(ed.to)) {
                        visited.add(ed.to);
                        seenEdges.add(ed);
                        path.add(ed.to);
                        stack.push(ed.to);

                        GraphVisualizerUI.pathArea.setText("Path: " + String.join(" → ",
                            path.stream().map(String::valueOf).toList()));
                        break;
                    }
                }
                drawGraph(panel, seenEdges, path);
            } else {
                timer.stop();
            }
        });
        timer.start();
    }
    public static void runDijkstra(JPanel panel) {
        int start = edges.get(0).from;
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        for (int node : getAllNodes()) dist.put(node, Integer.MAX_VALUE);
        dist.put(start, 0);
        pq.add(new int[]{start, 0});
        List<Edge> visitedEdges = new ArrayList<>();
        Set<Integer> visitedNodes = new HashSet<>();

        javax.swing.Timer timer = new javax.swing.Timer(1000, null);
        timer.addActionListener(e -> {
            if (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int u = curr[0];
                visitedNodes.add(u);

                for (Edge ed : edges) {
                    if (ed.from == u) {
                        int v = ed.to;
                        int cost = dist.get(u) + ed.weight;
                        if (cost < dist.getOrDefault(v, Integer.MAX_VALUE)) {
                            dist.put(v, cost);
                            parent.put(v, u);
                            pq.add(new int[]{v, cost});
                            visitedEdges.add(ed);
                        }
                    }
                }
                drawGraph(panel, visitedEdges, new ArrayList<>(visitedNodes));
                GraphVisualizerUI.pathArea.setText("Visited: " + visitedNodes);
            } else {
                timer.stop();
            }
        });
        timer.start();
    }

    public static void runPrim(JPanel panel) {
        Set<Integer> visited = new HashSet<>();
        List<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        int start = edges.get(0).from;
        visited.add(start);
        for (Edge e : edges) if (e.from == start) pq.add(e);

        javax.swing.Timer timer = new javax.swing.Timer(1000, null);
        timer.addActionListener(e -> {
            while (!pq.isEmpty()) {
                Edge curr = pq.poll();
                if (!visited.contains(curr.to)) {
                    visited.add(curr.to);
                    mst.add(curr);
                    for (Edge e2 : edges)
                        if (visited.contains(e2.from) && !visited.contains(e2.to)) pq.add(e2);
                    break;
                }
            }
            drawGraph(panel, mst, new ArrayList<>(visited));
            GraphVisualizerUI.pathArea.setText("Visited: " + visited);
            if (visited.size() == getAllNodes().size()) timer.stop();
        });
        timer.start();
    }

    public static void runKruskal(JPanel panel) {
        Map<Integer, Integer> parent = new HashMap<>();
        for (int node : getAllNodes()) parent.put(node, node);
        List<Edge> all = new ArrayList<>(edges);
        all.sort(Comparator.comparingInt(e -> e.weight));
        List<Edge> mst = new ArrayList<>();
        int[] i = {0};

        javax.swing.Timer timer = new javax.swing.Timer(1000, null);
        timer.addActionListener(e -> {
            while (i[0] < all.size()) {
                Edge ed = all.get(i[0]);
                int pu = find(parent, ed.from);
                int pv = find(parent, ed.to);
                if (pu != pv) {
                    mst.add(ed);
                    parent.put(pu, pv);
                    break;
                }
                i[0]++;
            }
            List<Integer> current = new ArrayList<>();
            for (Edge ed : mst) {
                 current.add(ed.from);
                 current.add(ed.to);
            }

            drawGraph(panel, mst, current);
            GraphVisualizerUI.pathArea.setText("MST: " + current);
            if (mst.size() == getAllNodes().size() - 1) timer.stop();
            i[0]++;
        });
        timer.start();
    }

    private static int find(Map<Integer, Integer> p, int x) {
        if (p.get(x) != x) p.put(x, find(p, p.get(x)));
        return p.get(x);
    }

    private static List<Edge> parseInput(String in) {
        List<Edge> list = new ArrayList<>();
        for (String s : in.split(";")) {
            String[] parts = s.trim().split("\\s+");
            if (parts.length >= 2) {
                int f = Integer.parseInt(parts[0]);
                int t = Integer.parseInt(parts[1]);
                int w = (parts.length == 3 && isWeighted) ? Integer.parseInt(parts[2]) : 1;
                list.add(new Edge(f, t, w));
            }
        }
        return list;
    }

    private static Set<Integer> getAllNodes() {
        Set<Integer> nodes = new HashSet<>();
        for (Edge e : edges) {
            nodes.add(e.from);
            nodes.add(e.to);
        }
        return nodes;
    }
    private static Map<Integer, Point> placeNodes(int width, int height, Set<Integer> nodes) {
        Map<Integer, Point> pos = new HashMap<>();
        int cx = width / 2, cy = height / 2;
        int r = Math.min(width, height) / 3;
        int i = 0, n = nodes.size();
        for (int node : nodes) {
            double angle = 2 * Math.PI * i / n;
            pos.put(node, new Point(cx + (int)(r * Math.cos(angle)), cy + (int)(r * Math.sin(angle))));
            i++;
        }
        return pos;
    }

    private static void drawGraph(JPanel panel, List<Edge> highlightEdges, List<Integer> highlightNodes) {
        panel.removeAll();

        for (Edge e : edges) {
            Point p1 = nodePositions.get(e.from), p2 = nodePositions.get(e.to);
            panel.add(new ArrowLine(p1, p2, e.weight, isWeighted,
                (highlightEdges != null && highlightEdges.contains(e)) ? Color.GREEN : Color.BLACK));
        }

        int startNode = edges.isEmpty() ? -1 : edges.get(0).from;
        int goalNode = edges.isEmpty() ? -1 : edges.get(edges.size() - 1).to;

        for (int node : nodePositions.keySet()) {
            JLabel label = new JLabel(String.valueOf(node), SwingConstants.CENTER);
            Point p = nodePositions.get(node);
            label.setBounds(p.x - NODE_RADIUS, p.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
            label.setOpaque(false);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.BLACK);

            if (node == startNode) {
                label.setBackground(Color.CYAN);
            } else if (node == goalNode) {
                label.setBackground(Color.PINK);
            } else if (highlightNodes != null && highlightNodes.contains(node)) {
                label.setBackground(Color.YELLOW);
            } else {
                label.setBackground(Color.WHITE);
            }

            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);

            label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(label.getBackground());
                    g2.fillOval(0, 0, c.getWidth(), c.getHeight());
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawOval(0, 0, c.getWidth(), c.getHeight());
                    super.paint(g, c);
                }
            });

            panel.add(label);
        }

        panel.revalidate();
        panel.repaint();
    }

    static class ArrowLine extends JComponent {
        Point start, end;
        int weight;
        boolean showWeight;
        Color color;

        public ArrowLine(Point s, Point e, int w, boolean sw, Color c) {
            start = s; end = e; weight = w; showWeight = sw; color = c;
            setBounds(0, 0, 2000, 2000);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double dx = end.x - start.x, dy = end.y - start.y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            double ux = dx / dist, uy = dy / dist;
            int sx = (int)(start.x + ux * NODE_RADIUS), sy = (int)(start.y + uy * NODE_RADIUS);
            int ex = (int)(end.x - ux * NODE_RADIUS), ey = (int)(end.y - uy * NODE_RADIUS);

            g2.drawLine(sx, sy, ex, ey);

            AffineTransform tx = g2.getTransform();
            g2.translate(ex, ey);
            g2.rotate(Math.atan2(dy, dx));
            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(0, 0);
            arrowHead.addPoint(-10, -5);
            arrowHead.addPoint(-10, 5);
            g2.fill(arrowHead);
            g2.setTransform(tx);

            if (showWeight) {
                int midX = (sx + ex) / 2;
                int midY = (sy + ey) / 2;

                int offsetX = (int)(-uy * 10);
                int offsetY = (int)(ux * 10);

                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.BOLD, 13)); // bold weight label
                g2.drawString(String.valueOf(weight), midX + offsetX, midY + offsetY);
            }
        }
    }
}
