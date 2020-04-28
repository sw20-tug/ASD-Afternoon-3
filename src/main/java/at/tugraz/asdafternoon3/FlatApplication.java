package at.tugraz.asdafternoon3;

import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.FlatOverview;
import at.tugraz.asdafternoon3.ui.CreateFlatUI;

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
            // init database
            DatabaseConnection db = DatabaseConnection.getInstance();
            db.initOrm();
            // create frame
            this.frame = new JFrame("Flat Application");

            // Check if a flat is existing
            long flatEntryCount = new FlatDAO().count();

            if (flatEntryCount == 0) {
                frame.setContentPane(new CreateFlatUI().getContentPane());
            } else {
                frame.setContentPane(new FlatOverview().getContentPane());
            }

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
        FlatApplication.get();
    }
}
