import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GraphViseFullScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GraphVise");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel frontPagePanel = createFrontPagePanel(cardLayout, mainPanel);
        mainPanel.add(frontPagePanel, "FrontPage");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createFrontPagePanel(CardLayout cardLayout, JPanel mainPanel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("<html><b style='font-size:80pt;'>GRAPH</b><span style='color:green; font-size:80pt;'>VISE</span></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel taglineLabel = new JLabel("Visualizing Directed Graph Algorithms with Animation");
        taglineLabel.setFont(new Font("Serif", Font.ITALIC, 30));
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        taglineLabel.setForeground(Color.DARK_GRAY);

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setBackground(new Color(34, 177, 76));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(200, 70));
        startButton.setMaximumSize(new Dimension(250, 80));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener((ActionEvent e) -> {
            SwingUtilities.getWindowAncestor(mainPanel).dispose(); // Close intro window
            GraphVisualizerUI.main(null); // Launch the visualizer UI
        });

        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(taglineLabel);
        panel.add(Box.createVerticalStrut(40));
        panel.add(startButton);
        panel.add(Box.createVerticalGlue());

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.add(panel);

        return wrapperPanel;
    }
}
