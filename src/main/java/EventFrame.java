import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class EventFrame extends JDialog {
    JFrame frame = new JFrame("EventsLog");
    JButton addBtn = createSimpleButton("Add");

    public EventFrame() {
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addBtn.setForeground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addBtn.setForeground(Color.BLACK);
            }
        });

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Box box = new Box(BoxLayout.Y_AXIS);
        add( box );
        addTextFieldsToPanel(box, addBtn);
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
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    private static void addTextFieldsToPanel(Box box, JButton addBtn) {
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(50);
        nameField.setMaximumSize( nameField.getPreferredSize() );
        nameField.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel descLabel = new JLabel("Description: ");
        JTextField descriptionField = new JTextField(50);
        descriptionField.setMaximumSize( descriptionField.getPreferredSize() );
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel paramsLabel = new JLabel("Parameters: ");
        JTextField paramsField = new JTextField(50);
        paramsField.setMaximumSize( paramsField.getPreferredSize() );
        paramsField.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel platformLabel = new JLabel("Platform: ");
        JTextField platformField = new JTextField(50);
        platformField.setMaximumSize( platformField.getPreferredSize() );
        platformField.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel appVersionLabel = new JLabel("App Version: ");
        JTextField appVersionField = new JTextField(50);
        appVersionField.setMaximumSize( appVersionField.getPreferredSize() );
        appVersionField.setFont(new Font("Arial", Font.PLAIN, 20));

        nameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        nameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        descLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        descriptionField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        paramsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        paramsField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        platformLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        platformField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        appVersionLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        appVersionField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        box.add( Box.createVerticalGlue() );
        box.add( nameLabel );
        box.add( nameField );
        box.add( descLabel );
        box.add( descriptionField );
        box.add( paramsLabel );
        box.add( paramsField );
        box.add( platformLabel );
        box.add( platformField );
        box.add( appVersionLabel );
        box.add( appVersionField );
        box.add(addBtn);
        box.add( Box.createVerticalGlue() );
    }
}