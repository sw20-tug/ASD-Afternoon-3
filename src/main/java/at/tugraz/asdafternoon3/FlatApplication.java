package at.tugraz.asdafternoon3;

import at.tugraz.asdafternoon3.ui.MainUI;

import javax.swing.*;

import static java.lang.Thread.sleep;

public class FlatApplication {

    private static FlatApplication instance = null;

    public static FlatApplication get() {
        if (instance == null) {
            instance = new FlatApplication();
        }
        return instance;
    }

    private JFrame frame;

    public FlatApplication() {
        try {
            this.frame = new JFrame("MainUI");
            frame.setContentPane(new MainUI().getContentPane());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setContentPane(JPanel newContentPane) {
        JPanel contentPane = (JPanel) frame.getContentPane();

        contentPane.removeAll();
        contentPane.add(newContentPane);
        contentPane.revalidate();
        contentPane.repaint();
        frame.pack();
        frame.repaint();
    }

    public static void main(String[] args) {
        new FlatApplication();
    }


}
