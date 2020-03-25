package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.database.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlatOverview {
    private JPanel panel1;
    private JPanel main;
    private JPanel BasicOverview;
    private JPanel Navigation;
    private JButton button1;
    private JButton shoppingListButton;
    private JButton cleaningScheduleButton;
    private JButton financeFurnitureAndEquipmentButton;
    private JButton financeFlatButton;

    public FlatOverview() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FlatOverview");
        frame.setContentPane(new FlatOverview().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
