import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class EventFrame extends JDialog {
    JFrame frame = new JFrame("EventsLog");
    JLabel message = new JLabel("");
    JTextField nameField = new JTextField(50);
    JTextArea paramsField = new JTextArea();
    JTextArea descriptionField = new JTextArea();
    JButton addBtn = createSimpleButton("Add");
    JButton fileBtn = createSimpleButton("Load from file");
    EventAddition event = new EventAddition();

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

            @Override
            public void mouseClicked(MouseEvent e) {
                String name = nameField.getText();
                String desc = descriptionField.getText();
                String params = paramsField.getText();
                message.setText(event.addEvent(name, desc, params));
            }
        });

        fileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fileBtn.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fileBtn.setForeground(Color.BLACK);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    message.setText(LoadFile.readFile());
                    message.setText(LoadFile.readLinks());
                } catch (IOException ex) {
                    message.setText("Error");
                }
            }
        });
        fileBtn.setFont(new Font("Arial", Font.PLAIN, 20));

        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Box box = new Box(BoxLayout.Y_AXIS);
        add( box );
        addTextFieldsToPanel(box, nameField, descriptionField, paramsField, addBtn, fileBtn, message);
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

    private static void addTextFieldsToPanel(Box box, JTextField nameField, JTextArea descriptionField,
                                             JTextArea paramsField, JButton addBtn, JButton fileBtn, JLabel message) {
        message.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel nameLabel = new JLabel("Name: ");
        nameField.setMaximumSize( nameField.getPreferredSize() );
        nameField.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel descLabel = new JLabel("Description: ");
        descriptionField.setPreferredSize(new Dimension(530, 250));
        JScrollPane scroll = new JScrollPane(descriptionField);
        scroll.setPreferredSize(new Dimension(552, 100));
        scroll.setMaximumSize(new Dimension(552, 100));
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel paramsLabel = new JLabel("Parameters: ");
        paramsField.setPreferredSize(new Dimension(530, 250));
        JScrollPane scroll2 = new JScrollPane(paramsField);
        scroll2.setPreferredSize(new Dimension(552, 100));
        scroll2.setMaximumSize(new Dimension(552, 100));
        paramsField.setFont(new Font("Arial", Font.PLAIN, 15));

        nameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        nameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        descLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        descriptionField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        paramsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        paramsField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        fileBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        message.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        box.add( Box.createVerticalGlue() );
        box.add( nameLabel );
        box.add( nameField );
        box.add( descLabel );
        box.add( scroll );
        box.add( paramsLabel );
        box.add( scroll2 );
        box.add(addBtn);
        box.add(fileBtn);
        box.add(message);
        box.add( Box.createVerticalGlue() );
    }
}