package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.data.Flat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {
    private JPanel contentPane;
    private JPanel Panel1;
    private JButton btCreateNewFlat;
    private JTextField tfName;
    private JTextField tfAddress;
    private JTextField tfSize;
    private JButton clickMeButton;

    public MainUI() {

        btCreateNewFlat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Flat newFlat;
                int size = 0;

                try {
                    size = Integer.parseInt(tfSize.getText());
                }
                catch (Exception ex)
                {
                    System.out.println("Exception: Couldn't convert string to int: " + ex.getLocalizedMessage());
                }
                newFlat = new Flat(tfName.getText(), size, tfAddress.getText());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainUI");
        frame.setContentPane(new MainUI().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
