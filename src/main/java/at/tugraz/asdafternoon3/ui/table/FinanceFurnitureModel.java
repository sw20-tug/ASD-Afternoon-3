package at.tugraz.asdafternoon3.ui.table;

import at.tugraz.asdafternoon3.businesslogic.FinanceDAO;
import at.tugraz.asdafternoon3.businesslogic.RoommateDAO;
import at.tugraz.asdafternoon3.data.Finance;
import at.tugraz.asdafternoon3.database.DatabaseConnection;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FinanceFurnitureModel extends AbstractTableModel{

    private final List<Finance> financeList = new ArrayList<>();

    public FinanceFurnitureModel(List<Finance> financeList) {
        this.financeList.addAll(financeList);
    }

    @Override
    public int getRowCount() {
        return financeList.size();
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
                return "Name";
            case 2:
                return "Cost";
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
        Finance finance = financeList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return finance.getId();
            case 1:
                return finance.getName();
            case 2:
                return finance.getCosts();
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Finance finance = financeList.get(rowIndex);

        switch (columnIndex) {
            case 1:
                finance.setName((String) aValue);
                break;
            case 2:
                if (aValue instanceof Integer) {
                    finance.setCosts((Integer) aValue);
                }
                break;
        }

        // TODO: Make this nicer, but we need to update this in the database
        try {
            DatabaseConnection.getInstance().createDao(FinanceDAO.class).update(finance);
        } catch (Exception ex) {
            // TODO: reset values
            System.err.println(ex.getMessage());
        }

        fireTableRowsUpdated(columnIndex, columnIndex);
    }


    public void addFinance(Finance finance) {
        int rowIndex = financeList.size();
        financeList.add(finance);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    public void removeFinance(int rowIndex) {
        financeList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public Finance getFinanceAtIndex(int rowIndex) {
        return financeList.get(rowIndex);
    }
}
