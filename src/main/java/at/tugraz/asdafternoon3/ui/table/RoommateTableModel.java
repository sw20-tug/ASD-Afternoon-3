package at.tugraz.asdafternoon3.ui.table;

import at.tugraz.asdafternoon3.businesslogic.RoommateDAO;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.database.DatabaseConnection;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;


public class RoommateTableModel implements TableModel {

    private final List<Roommate> roommateList = new ArrayList<>();
    private final List<TableModelListener> listeners = new ArrayList<>();

    public RoommateTableModel(List<Roommate> roommateList) {
        this.roommateList.addAll(roommateList);
    }

    @Override
    public int getRowCount() {
        return roommateList.size();
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
                return "Age";
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
        Roommate roommate = roommateList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return roommate.getId();
            case 1:
                return roommate.getName();
            case 2:
                return roommate.getAge();
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Roommate roommate = roommateList.get(rowIndex);

        switch (columnIndex) {
            case 1:
                roommate.setName((String) aValue);
            case 2:
                if (aValue instanceof Integer) {
                    roommate.setAge((Integer) aValue);
                }
        }

        // TODO: Make this nicer, but we need to update this in the database
        try {
            DatabaseConnection.getInstance().createDao(RoommateDAO.class).update(roommate);
        } catch (Exception ex) {
            // TODO: reset values
            System.err.println(ex.getMessage());
        }

        TableModelEvent event = new TableModelEvent(
                this, rowIndex, rowIndex, columnIndex, TableModelEvent.UPDATE);

        emitTableChanged(event);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public void addRoommate(Roommate roommate) {
        int rowIndex = roommateList.size();
        roommateList.add(roommate);

        TableModelEvent event = new TableModelEvent(
                this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);

        emitTableChanged(event);
    }

    public void removeRoommate(int rowIndex) {
        roommateList.remove(rowIndex);

        TableModelEvent event = new TableModelEvent(
                this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

        emitTableChanged(event);
    }

    private void emitTableChanged(TableModelEvent event) {
        for (TableModelListener listener : listeners) {
            listener.tableChanged(event);
        }
    }

    public Roommate getRoommateAtIndex(int rowIndex) {
        return roommateList.get(rowIndex);
    }
}
