import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JButton searchButton, quitButton;
    private JTextArea resultArea;

    public RandProductSearch() {
        setTitle("Product Search");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        // center frame in screen
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Search by Name:"));
        searchField = new JTextField(20);
        inputPanel.add(searchField);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchListener());
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTerm = searchField.getText().trim().toLowerCase();
            resultArea.setText("");

            try (RandomAccessFile file = new RandomAccessFile("products.dat", "r")) {
                int recordSize = 35 + 75 + 6 + 8; // Total size of one product entry
                ArrayList<String> results = new ArrayList<>();

                while (file.getFilePointer() < file.length()) {
                    byte[] nameBytes = new byte[35];
                    file.read(nameBytes);
                    String name = new String(nameBytes).trim();

                    byte[] descBytes = new byte[75];
                    file.read(descBytes);
                    String description = new String(descBytes).trim();

                    byte[] idBytes = new byte[6];
                    file.read(idBytes);
                    String id = new String(idBytes).trim();

                    double cost = file.readDouble();

                    if (name.toLowerCase().contains(searchTerm)) {
                        results.add(String.format("ID: %s, Name: %s, Cost: $%.2f\nDescription: %s\n", id, name, cost, description));
                    }
                }

                if (results.isEmpty()) {
                    resultArea.setText("No matching products found.");
                } else {
                    resultArea.setText(String.join("\n\n", results));
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error reading file.");
            }
        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
