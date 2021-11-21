import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JDialog {
    JFrame frame = new JFrame("EventsLog");
    JPanel jPanel = new JPanel(new GridBagLayout());
    JButton newEventBtn = createSimpleButton("New event");
    JButton getStatsBtn = createSimpleButton("Get statistics");

    public MainFrame () {
        newEventBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
             public void mouseEntered(java.awt.event.MouseEvent evt) {
                 newEventBtn.setForeground(Color.BLUE);
             }
            @Override
            public void mouseExited(MouseEvent e) {
                newEventBtn.setForeground(Color.BLACK);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                EventFrame eventFrame = new EventFrame();
            }
        });
        getStatsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                getStatsBtn.setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                getStatsBtn.setForeground(Color.BLACK);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                StatisticsFrame statisticsFrame = new StatisticsFrame();
            }
        });

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.WEST;

        newEventBtn.setMinimumSize(new Dimension(300, 200));
        getStatsBtn.setMinimumSize(new Dimension(300, 200));

        jPanel.add(newEventBtn, constraints);
        jPanel.add(getStatsBtn, constraints);
        frame.add(jPanel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }

    private static JButton createSimpleButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }
}
