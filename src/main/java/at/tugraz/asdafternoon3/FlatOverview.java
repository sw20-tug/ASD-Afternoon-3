package at.tugraz.asdafternoon3;

import at.tugraz.asdafternoon3.ui.MainUI;
import at.tugraz.asdafternoon3.data.Flat;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlatOverview
{
    private JPanel panel1;
    private JPanel main;
    private JPanel BasicOverview;
    private JPanel Navigation;
    private JButton button1;
    private JButton shoppingListButton;
    private JButton cleaningScheduleButton;
    private JButton financeFurnitureAndEquipmentButton;
    private JButton financeFlatButton;
    private JTextArea taAddress;
    private JTextArea taSize;
    private JTextArea taName;

    public FlatOverview()
    {
    }

    private void createUIComponents()
    {
        //Flat flat = new Flat("TestHome", 100, "Rechbauerstraße 11");
        // TODO: place custom component creation code here
        //taName.setText(flat.getName());
        //taSize.setText(String.format("%d",flat.getSize()));
       // taAddress.setText(flat.getAddress());
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("FlatOverview");

        frame.setContentPane(new FlatOverview().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
