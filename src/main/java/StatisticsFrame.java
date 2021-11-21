import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StatisticsFrame extends JDialog {
    JFrame frame = new JFrame("EventsLog");
    JPanel jPanel = new JPanel(new GridBagLayout());
    JButton script = createSimpleButton("Сценарий");
    JButton screen = createSimpleButton("События (экран)");
    JButton element = createSimpleButton("События (элемент)");

    public StatisticsFrame () {
        script.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                script.setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                script.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        screen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                screen.setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                screen.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });
        element.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                element.setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                element.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 10, 5, 10);
        constraints.anchor = GridBagConstraints.WEST;

        screen.setMinimumSize(new Dimension(300, 200));
        script.setMinimumSize(new Dimension(300, 200));
        element.setMinimumSize(new Dimension(300, 200));

        Box box = new Box(BoxLayout.Y_AXIS);
        add( box );

        JTextArea resultField = new JTextArea();
        resultField.setFont(new Font("Arial", Font.PLAIN, 20));

        JScrollPane scroll = new JScrollPane(resultField);
        scroll.setPreferredSize(new Dimension(660, 400));

        jPanel.add(script, constraints);
        jPanel.add(screen, constraints);
        jPanel.add(element, constraints);
        jPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(scroll);
        box.add(jPanel);
        frame.add(box);

        frame.pack();
        frame.setVisible(true);
    }

    private static JButton createSimpleButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }
}
