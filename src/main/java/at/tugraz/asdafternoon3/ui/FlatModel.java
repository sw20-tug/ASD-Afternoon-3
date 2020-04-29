package at.tugraz.asdafternoon3.ui;

import at.tugraz.asdafternoon3.data.Flat;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;


public class FlatModel extends AbstractTableModel {

    private List<Flat> flats = new ArrayList<>();
    private final List<TableModelListener> listeners = new ArrayList<>();
    public FlatModel(List<Flat> flats){

        this.flats.addAll(flats);
    }

    @Override
    public int getRowCount() {
        return flats.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch( columnIndex ) {
            case 0:
                return "Id";
            case 1:
                return "Name";
            case 2:
                return "Size";
            case 3:
                return "Adress";
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch( columnIndex ){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return Integer.class;
            case 3: return String.class;
            default: throw new IllegalArgumentException( "Wrong column" );
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flat flat = flats.get(rowIndex);
        switch ( columnIndex ){
            case 0: return flat.getId();
            case 1: return flat.getName();
            case 2: return flat.getSize();
            case 3: return flat.getAddress();
            default: throw new IllegalArgumentException( "Wrong column" );
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    public Flat getElement(int rowIndex) {
        return flats.get(rowIndex);
    }

    public void addFlat(Flat flat) {
        int rowIndex = flats.size();
        flats.add(flat);

        TableModelEvent event = new TableModelEvent(
                this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);

        emitTableChanged(event);
    }

    public void removeFlat(int rowIndex) {
        flats.remove(rowIndex);

        TableModelEvent event = new TableModelEvent(
                this, rowIndex, rowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

        emitTableChanged(event);
    }
    private void emitTableChanged(TableModelEvent event) {
        for (TableModelListener listener : listeners) {
            listener.tableChanged(event);
        }
    }



}
