package at.tugraz.asdafternoon3.ui;

import javax.swing.*;

public class FlatOverview {
    private JPanel contentPane;
    private JPanel main;
    private JPanel BasicOverview;
    private JPanel Navigation;
    private JButton button1;
    private JButton shoppingListButton;
    private JButton cleaningScheduleButton;
    private JButton financeFurnitureAndEquipmentButton;
    private JButton financeFlatButton;

    public FlatOverview() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FlatOverview");
        frame.setContentPane(new FlatOverview().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
