package at.tugraz.asdafternoon3.ui.table;

import at.tugraz.asdafternoon3.businesslogic.FinanceFlatDAO;
import at.tugraz.asdafternoon3.data.FinanceFlat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FinanceFlatTableModel extends AbstractTableModel {

    private final List<FinanceFlat> financeFlatList = new ArrayList<>();

    public FinanceFlatTableModel(List<FinanceFlat> financeFlatList) {
        this.financeFlatList.addAll(financeFlatList);
    }

    @Override
    public int getRowCount() {
        return financeFlatList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Title";
            case 2:
                return "Price";
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return false;
            case 1:
                return true;
            case 2:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FinanceFlat finance = financeFlatList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return finance.getId();
            case 1:
                return finance.getTitle();
            case 2:
                return finance.getPrice();
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        FinanceFlat financeFlat = financeFlatList.get(rowIndex);

        switch (columnIndex) {
            case 1:
                financeFlat.setTitle((String) aValue);
                break;
            case 2:
                if (aValue instanceof Integer) {
                    financeFlat.setPrice((Integer) aValue);
                }
                break;
        }

        // TODO: Make this nicer, but we need to update this in the database
        try {
            DatabaseConnection.getInstance().createDao(FinanceFlatDAO.class).update(financeFlat);
        } catch (Exception ex) {
            // TODO: reset values
            System.err.println(ex.getMessage());
        }

        fireTableRowsUpdated(columnIndex, columnIndex);
    }


    public void addFinanceFlat(FinanceFlat financeFlat) {
        int rowIndex = financeFlatList.size();
        financeFlatList.add(financeFlat);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    public void removeFinanceFlat(int rowIndex) {
        financeFlatList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public FinanceFlat getFinanceFlatAtIndex(int rowIndex) {
        return financeFlatList.get(rowIndex);
    }
}
