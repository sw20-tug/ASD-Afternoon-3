package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.FlatApplication;
import at.tugraz.asdafternoon3.businesslogic.FinanceFlatDAO;
import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.data.FinanceFlat;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.table.FlatTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlatList {

    private JPanel contentPane;
    private JTable flatTable;
    private JPanel pControl;
    private JButton addButton;
    private JButton removeButton;
    private JButton setToCurrentButton;
    private JTextField tfName;
    private JTextField tfAddress;
    private JTextField tfSize;
    private JButton backButton;
    private Flat currentFlat;

    public FlatList(Flat flat) {
        try {
            currentFlat = flat;
            FlatTableModel model = new FlatTableModel(DatabaseConnection.getInstance().createDao(FlatDAO.class).getAll());
            flatTable.setModel(model);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addFlatClicked();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                deleteFlatClicked();
            }
        });
        setToCurrentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setToCurrentClicked();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlatApplication.get().setContentPane(new FlatOverview(currentFlat).getContentPane());
            }
        });
    }

    private void addFlatClicked() {
        int size = 0;

        try {
            size = Integer.parseInt(tfSize.getText());
            Flat newFlat = new Flat(tfName.getText(), size, tfAddress.getText());
            FlatDAO creator = DatabaseConnection.getInstance().createDao(FlatDAO.class);
            FinanceFlatDAO financeCreator = DatabaseConnection.getInstance().createDao(FinanceFlatDAO.class);

            if (!creator.validate(newFlat)) {
                JOptionPane.showMessageDialog(contentPane, "Flat data is not valid");
            } else {
                newFlat = creator.create(newFlat);
                instantiateFinanceFlatItems(newFlat, financeCreator);
                ((FlatTableModel) flatTable.getModel()).addFlat(newFlat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Could not create flat");
        }
    }

    private void instantiateFinanceFlatItems(Flat flat, FinanceFlatDAO financeCreator) throws Exception {
        FinanceFlat rentalFee = new FinanceFlat("Rental fee", 0, flat);
        FinanceFlat smartphoneBill = new FinanceFlat("Smartphone bill", 0, flat);
        FinanceFlat energyBill = new FinanceFlat("Energy bill", 0, flat);
        FinanceFlat internetBill = new FinanceFlat("Internet bill", 0, flat);
        FinanceFlat tvBill = new FinanceFlat("TV bill", 0, flat);

        rentalFee = financeCreator.create(rentalFee);
        smartphoneBill = financeCreator.create(smartphoneBill);
        energyBill = financeCreator.create(energyBill);
        internetBill = financeCreator.create(internetBill);
        tvBill = financeCreator.create(tvBill);
    }

    private void deleteFlatClicked() {
        try {
            int selectedRow = flatTable.getSelectedRow();
            Flat selectedFlat = ((FlatTableModel) flatTable.getModel()).getElement(selectedRow);
            if (selectedFlat.isCurrent()) {
                throw new Exception("Cannot delete current flat");
            }
            FlatDAO creator = DatabaseConnection.getInstance().createDao(FlatDAO.class);
            creator.delete(selectedFlat);
            ((FlatTableModel) flatTable.getModel()).removeFlat(selectedRow);
            ((FlatTableModel) flatTable.getModel()).fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Could not delete flat " + e.getMessage());
        }


    }

    private void setToCurrentClicked() {
        try {
            int selectedRow = flatTable.getSelectedRow();
            Flat selectedFlat = ((FlatTableModel) flatTable.getModel()).getElement(selectedRow);
            selectedFlat.setIsCurrent(true);
            FlatDAO creator = DatabaseConnection.getInstance().createDao(FlatDAO.class);
            creator.update(selectedFlat);
            FlatTableModel model = new FlatTableModel(DatabaseConnection.getInstance().createDao(FlatDAO.class).getAll());
            flatTable.setModel(model);
            ((FlatTableModel) flatTable.getModel()).fireTableDataChanged();
            currentFlat = selectedFlat;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Could not set flat to current flat");
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("FlatList");
        Flat flat = new Flat("test", 4, "Graz");
        FlatList overview = new FlatList(flat);

        frame.setContentPane(overview.contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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
        contentPane.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        flatTable = new JTable();
        panel2.add(flatTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Name");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfName = new JTextField();
        panel3.add(tfName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfAddress = new JTextField();
        panel3.add(tfAddress, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfSize = new JTextField();
        panel3.add(tfSize, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Size");
        panel3.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Address");
        panel3.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel1.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setText("Back");
        panel4.add(backButton, BorderLayout.WEST);
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 20, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Flat List");
        label4.setVerticalTextPosition(0);
        panel4.add(label4, BorderLayout.CENTER);
        pControl = new JPanel();
        pControl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        contentPane.add(pControl, BorderLayout.SOUTH);
        addButton = new JButton();
        addButton.setText("Add");
        pControl.add(addButton);
        removeButton = new JButton();
        removeButton.setText("Remove");
        pControl.add(removeButton);
        setToCurrentButton = new JButton();
        setToCurrentButton.setText("Set to current");
        pControl.add(setToCurrentButton);
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
