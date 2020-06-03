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
    private JLabel lHeader;
    private JLabel lName;
    private JLabel lAddress;
    private JLabel lSize;
    private Flat currentFlat;

    public FlatList(Flat flat) {
        initLocalizations();
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

    private void initLocalizations() {
        lHeader.setText(Localization.getInstance().getCurrent().getString("flatlist.header"));
        lName.setText(Localization.getInstance().getCurrent().getString("flatlist.name"));
        lAddress.setText(Localization.getInstance().getCurrent().getString("flatlist.address"));
        lSize.setText(Localization.getInstance().getCurrent().getString("flatlist.size"));
        addButton.setText(Localization.getInstance().getCurrent().getString("flatlist.button.add"));
        removeButton.setText(Localization.getInstance().getCurrent().getString("flatlist.button.remove"));
        setToCurrentButton.setText(Localization.getInstance().getCurrent().getString("flatlist.button.settocurrent"));
        backButton.setText(Localization.getInstance().getCurrent().getString("frame.button.back"));
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
        contentPane.setBackground(new Color(-14078925));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 0, 10), -1, -1));
        panel1.setBackground(new Color(-14078925));
        contentPane.add(panel1, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-14078925));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        flatTable = new JTable();
        flatTable.setBackground(new Color(-14078925));
        flatTable.setForeground(new Color(-2103318));
        flatTable.setGridColor(new Color(-12816512));
        panel2.add(flatTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-14078925));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, true));
        lName = new JLabel();
        lName.setForeground(new Color(-4145152));
        lName.setText("Name");
        panel3.add(lName, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfName = new JTextField();
        tfName.setBackground(new Color(-12632257));
        tfName.setForeground(new Color(-2103318));
        panel3.add(tfName, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfAddress = new JTextField();
        tfAddress.setBackground(new Color(-12632257));
        tfAddress.setForeground(new Color(-2103318));
        panel3.add(tfAddress, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfSize = new JTextField();
        tfSize.setBackground(new Color(-12632257));
        tfSize.setForeground(new Color(-2103318));
        panel3.add(tfSize, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lSize = new JLabel();
        lSize.setForeground(new Color(-4145152));
        lSize.setText("Size");
        panel3.add(lSize, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lAddress = new JLabel();
        lAddress.setForeground(new Color(-4145152));
        lAddress.setText("Address");
        panel3.add(lAddress, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel4.setBackground(new Color(-14078925));
        panel1.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setAlignmentX(0.0f);
        backButton.setBackground(new Color(-12816512));
        backButton.setForeground(new Color(-2103318));
        backButton.setHideActionText(false);
        backButton.setHorizontalAlignment(0);
        backButton.setHorizontalTextPosition(0);
        backButton.setMargin(new Insets(0, 0, 0, 0));
        backButton.setPreferredSize(new Dimension(70, 20));
        backButton.setText("Back");
        backButton.setVerticalAlignment(0);
        panel4.add(backButton, BorderLayout.WEST);
        lHeader = new JLabel();
        lHeader.setAlignmentY(0.5f);
        lHeader.setBackground(new Color(-14078925));
        Font lHeaderFont = this.$$$getFont$$$(null, -1, 20, lHeader.getFont());
        if (lHeaderFont != null) lHeader.setFont(lHeaderFont);
        lHeader.setForeground(new Color(-4145152));
        lHeader.setHorizontalAlignment(0);
        lHeader.setHorizontalTextPosition(0);
        lHeader.setText("Flat List");
        lHeader.setVerticalTextPosition(0);
        panel4.add(lHeader, BorderLayout.CENTER);
        pControl = new JPanel();
        pControl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pControl.setBackground(new Color(-14078925));
        pControl.setEnabled(true);
        contentPane.add(pControl, BorderLayout.SOUTH);
        addButton = new JButton();
        addButton.setBackground(new Color(-12816512));
        addButton.setForeground(new Color(-2103318));
        addButton.setText("Add");
        pControl.add(addButton);
        removeButton = new JButton();
        removeButton.setBackground(new Color(-12816512));
        removeButton.setForeground(new Color(-2103318));
        removeButton.setText("Remove");
        pControl.add(removeButton);
        setToCurrentButton = new JButton();
        setToCurrentButton.setBackground(new Color(-12816512));
        setToCurrentButton.setEnabled(true);
        setToCurrentButton.setForeground(new Color(-2103318));
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
