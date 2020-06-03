package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.FlatApplication;
import at.tugraz.asdafternoon3.businesslogic.FinanceFlatDAO;
import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.data.FinanceFlat;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.table.FinanceFlatTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceFlatOverview {
    private JPanel contentPane;
    private JButton backButton;
    private JButton removeButton;
    private JButton addButton;
    private JSpinner spPrice;
    private JTable financeFlatTable;
    private JTextField tfTitle;
    private JLabel lTotalCosts;
    private JLabel lRoommateCosts;
    private JLabel lHeader;
    private JLabel lTotalSum;
    private JLabel lCostsPerRoommate;

    private final Flat activeFlat;
    private final FinanceFlatTableModel tableModel;

    private final List<FinanceFlat> financeItems;

    public FinanceFlatOverview(Flat flat) {
        initLocalization();
        this.activeFlat = flat;

        this.financeItems = new ArrayList<>();

        try {
            this.financeItems.addAll(DatabaseConnection.getInstance().createDao(FlatDAO.class).getFinanceFlat(flat));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getContentPane(), "Finance items could not be found.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        tableModel = new FinanceFlatTableModel(financeItems);
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateSum();
            }
        });
        financeFlatTable.setModel(tableModel);

        calculateSum();

        backButton.addActionListener(e -> {
            FlatApplication.get().setContentPane(new FlatOverview(activeFlat).getContentPane());
        });

        addButton.addActionListener(e -> {
            FinanceFlat financeFlat = createFinanceFlat();
            if (financeFlat != null) {
                tableModel.addFinanceFlat(financeFlat);
                financeItems.add(financeFlat);
                calculateSum();
            }
        });

        removeButton.addActionListener(e -> {
            int rowIndex = financeFlatTable.getSelectedRow();
            FinanceFlat financeFlat = tableModel.getFinanceFlatAtIndex(rowIndex);

            try {
                DatabaseConnection.getInstance().createDao(FinanceFlatDAO.class).delete(financeFlat);
                tableModel.removeFinanceFlat(rowIndex);
                financeItems.remove(financeFlat);
                System.out.println("finance Item: " + financeItems.toString());
                calculateSum();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(contentPane, "Could not remove object");
            }
        });
    }

    private void initLocalization() {
        lHeader.setText(Localization.getInstance().getCurrent().getString("financeflat.header"));
        lTotalSum.setText(Localization.getInstance().getCurrent().getString("financeflat.totalsum"));
        lCostsPerRoommate.setText(Localization.getInstance().getCurrent().getString("financeflat.costsperroommate"));
        addButton.setText(Localization.getInstance().getCurrent().getString("financeflat.button.add"));
        removeButton.setText(Localization.getInstance().getCurrent().getString("financeflat.button.remove"));
        backButton.setText(Localization.getInstance().getCurrent().getString("frame.button.back"));
    }

    private FinanceFlat createFinanceFlat() {
        FinanceFlat newFinanceFlat = new FinanceFlat(tfTitle.getText(),
                (Integer) spPrice.getValue(),
                activeFlat);

        try {
            FinanceFlatDAO creator = DatabaseConnection.getInstance().createDao(FinanceFlatDAO.class);

            if (!creator.validate(newFinanceFlat)) {
                JOptionPane.showMessageDialog(contentPane, "Finance data is not valid");
            } else {
                return creator.create(newFinanceFlat);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(contentPane, "Could not create finance flat");
        }

        return null;
    }

    private void calculateSum() {
        double sum = 0;
        double roommateCosts = 0;
        for (int counter = 0; counter < financeItems.size(); counter++) {
            sum += financeItems.get(counter).getPrice();
        }
        lTotalCosts.setText(String.valueOf(sum));

        FlatDAO creator = new FlatDAO(DatabaseConnection.getInstance().getSessionFactory());
        int nRoommates = creator.getRoommates(activeFlat).size();
        if (nRoommates == 0) {
            lRoommateCosts.setText("No roommates");
        } else {
            roommateCosts = sum / nRoommates;
            lRoommateCosts.setText(String.format("%.2f", roommateCosts));
        }
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
        contentPane.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setBackground(new Color(-14078925));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-14078925));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lHeader = new JLabel();
        lHeader.setForeground(new Color(-4145152));
        lHeader.setText("Finance Flat Overview");
        panel1.add(lHeader, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        backButton = new JButton();
        backButton.setBackground(new Color(-12816512));
        backButton.setForeground(new Color(-2103318));
        backButton.setText("Back");
        panel1.add(backButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-14078925));
        contentPane.add(panel2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setBackground(new Color(-12816512));
        removeButton.setForeground(new Color(-2103318));
        removeButton.setText("Remove");
        panel2.add(removeButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setBackground(new Color(-12816512));
        addButton.setForeground(new Color(-2103318));
        addButton.setText("Add");
        panel2.add(addButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spPrice = new JSpinner();
        panel2.add(spPrice, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfTitle = new JTextField();
        tfTitle.setBackground(new Color(-12632257));
        tfTitle.setForeground(new Color(-2103318));
        panel2.add(tfTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        financeFlatTable = new JTable();
        financeFlatTable.setBackground(new Color(-14078925));
        financeFlatTable.setForeground(new Color(-2103318));
        financeFlatTable.setGridColor(new Color(-12816512));
        contentPane.add(financeFlatTable, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-14078925));
        panel3.setForeground(new Color(-2103318));
        contentPane.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lCostsPerRoommate = new JLabel();
        lCostsPerRoommate.setForeground(new Color(-2103318));
        lCostsPerRoommate.setText("Costs per Roommate:");
        panel3.add(lCostsPerRoommate, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lTotalSum = new JLabel();
        lTotalSum.setForeground(new Color(-2103318));
        lTotalSum.setText("Total sum:");
        panel3.add(lTotalSum, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lTotalCosts = new JLabel();
        lTotalCosts.setForeground(new Color(-2103318));
        lTotalCosts.setText("0");
        panel3.add(lTotalCosts, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lRoommateCosts = new JLabel();
        lRoommateCosts.setForeground(new Color(-2103318));
        lRoommateCosts.setText("0");
        panel3.add(lRoommateCosts, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
