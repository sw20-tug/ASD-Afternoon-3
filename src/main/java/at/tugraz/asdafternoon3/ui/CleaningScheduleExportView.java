package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.FlatApplication;
import at.tugraz.asdafternoon3.businesslogic.CalenderExport;
import at.tugraz.asdafternoon3.businesslogic.CleaningScheduleDAO;
import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.DatabaseObject;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.combobox.RoommateComboBoxModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CleaningScheduleExportView {
    private JPanel contentPane;
    private JButton exportButton;
    private JButton backButton;
    private JComboBox<Roommate> cbRoommates;

    private final Flat activeFlat;
    private final RoommateComboBoxModel model;

    public CleaningScheduleExportView(Flat flat) {
        activeFlat = flat;

        List<Roommate> roommates = new ArrayList<>();
        try {
            roommates = DatabaseConnection.getInstance().createDao(FlatDAO.class).getRoommates(flat);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getContentPane(), "Roommates could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        model = new RoommateComboBoxModel(roommates);
        cbRoommates.setModel(model);

        backButton.addActionListener(e -> FlatApplication.get().setContentPane(new CleaningScheduleUI(activeFlat).getContentPane()));
        exportButton.addActionListener(e -> {
            try {
                CleaningScheduleDAO dao = DatabaseConnection.getInstance().createDao(CleaningScheduleDAO.class);
                Roommate selected = model.getSelectedItem();
                CalenderExport export = new CalenderExport(selected == null ? dao.getAll() : dao.getForRoommate(selected));
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("ICS file", "ics"));
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // lil hacky
                    File file = fileChooser.getSelectedFile();
                    String filename = file.getAbsolutePath();
                    if (!filename.endsWith(".ics")) {
                        filename += ".ics";
                    }
                    export.export(new File(filename));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getContentPane(), "Cleaning schedule could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setBackground(new Color(-14078925));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-14078925));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 22, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-4145152));
        label1.setText("Cleaning Schedule Export");
        panel1.add(label1, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exportButton = new JButton();
        exportButton.setBackground(new Color(-12816512));
        exportButton.setForeground(new Color(-2103318));
        exportButton.setText("Export");
        panel1.add(exportButton, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setBackground(new Color(-12816512));
        backButton.setForeground(new Color(-2103318));
        backButton.setText("Back");
        panel1.add(backButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbRoommates = new JComboBox();
        cbRoommates.setBackground(new Color(-12632257));
        cbRoommates.setForeground(new Color(-2103318));
        panel1.add(cbRoommates, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
