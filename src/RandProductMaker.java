import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private JButton addButton, quitButton;
    private int recordCount = 0;

    public RandProductMaker() {
        setTitle("Product Entry");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // center frame in screen
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Labels and Fields
        add(new JLabel("Name:"));
        nameField = new JTextField(35);
        add(nameField);

        add(new JLabel("Description:"));
        descriptionField = new JTextField(75);
        add(descriptionField);

        add(new JLabel("ID (6 chars):"));
        idField = new JTextField(6);
        add(idField);

        add(new JLabel("Cost:"));
        costField = new JTextField();
        add(costField);

        add(new JLabel("Record Count:"));
        recordCountField = new JTextField("0");
        recordCountField.setEditable(false);
        add(recordCountField);

        // Buttons
        addButton = new JButton("Add Product");
        addButton.addActionListener(new AddProductListener());
        add(addButton);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);

        setVisible(true);
    }

    private class AddProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = padString(nameField.getText(), 35);
            String description = padString(descriptionField.getText(), 75);
            String id = padString(idField.getText(), 6);
            double cost;

            try {
                cost = Double.parseDouble(costField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid cost input!");
                return;
            }

            try (RandomAccessFile file = new RandomAccessFile("products.dat", "rw")) {
                file.seek(file.length()); // Move to the end of the file
                file.writeBytes(name);
                file.writeBytes(description);
                file.writeBytes(id);
                file.writeDouble(cost);

                recordCount++;
                recordCountField.setText(String.valueOf(recordCount));

                JOptionPane.showMessageDialog(null, "Product added!");

                nameField.setText("");
                descriptionField.setText("");
                idField.setText("");
                costField.setText("");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving product.");
            }
        }
    }

    private String padString(String str, int length) {
        if (str.length() >= length) {
            return str.substring(0, length);
        }
        return String.format("%-" + length + "s", str);
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
