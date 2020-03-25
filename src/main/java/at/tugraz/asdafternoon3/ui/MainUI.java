package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.database.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    private JPanel contentPane;
    private JButton clickMeButton;

    public MainUI() {
        clickMeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hello World");
            }
        });
    }

    public static void main(String[] args) {
        // Database initialization
        DatabaseConnection.getInstance().initOrm();

        JFrame frame = new JFrame("MainUI");
        frame.setContentPane(new MainUI().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
