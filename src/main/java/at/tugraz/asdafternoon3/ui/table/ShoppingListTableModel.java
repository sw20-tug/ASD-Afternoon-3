package at.tugraz.asdafternoon3.ui.table;

import at.tugraz.asdafternoon3.businesslogic.RoommateDAO;
import at.tugraz.asdafternoon3.businesslogic.ShoppingListItemDAO;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.data.ShoppingListItem;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import at.tugraz.asdafternoon3.ui.ShoppingList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;


public class ShoppingListTableModel extends AbstractTableModel {

    private final List<ShoppingListItem> shoppingList = new ArrayList<>();

    public ShoppingListTableModel(List<ShoppingListItem> shoppingList) {
        this.shoppingList.addAll(shoppingList);
    }

    @Override
    public int getRowCount() {
        return shoppingList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Completed";
            case 1:
                return "Item";
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Boolean.class;
            case 1:
                return String.class;
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ShoppingListItem item = shoppingList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return item.isCompleted();
            case 1:
                return item.getItem();
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ShoppingListItem item = shoppingList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                item.setCompleted((Boolean) aValue);
                break;
            case 1:
                item.setItem((String) aValue);
                break;
        }

        // TODO: Make this nicer, but we need to update this in the database
        try {
            DatabaseConnection.getInstance().createDao(ShoppingListItemDAO.class).update(item);
        } catch (Exception ex) {
            // TODO: reset values
            System.err.println(ex.getMessage());
        }

        fireTableRowsUpdated(columnIndex, columnIndex);
    }

    public void addItem(ShoppingListItem item) {
        int rowIndex = shoppingList.size();
        shoppingList.add(item);
        fireTableRowsInserted(rowIndex, rowIndex);
    }

    public void removeItem(int rowIndex) {
        shoppingList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public ShoppingListItem getItemAtIndex(int rowIndex) {
        return shoppingList.get(rowIndex);
    }

    public TableRowSorter<ShoppingListTableModel> getCompletedSorter() {
        TableRowSorter<ShoppingListTableModel> sorter = new TableRowSorter<>(this);
        RowFilter<ShoppingListTableModel,Integer> completedFilter = new RowFilter<ShoppingListTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends ShoppingListTableModel, ? extends Integer> entry) {
                ShoppingListItem item = entry.getModel().getItemAtIndex(entry.getIdentifier());
                return !item.isCompleted();
            }
        };
        sorter.setRowFilter(completedFilter);
        return sorter;
    }
}
