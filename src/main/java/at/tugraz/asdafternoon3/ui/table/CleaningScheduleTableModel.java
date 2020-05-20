package at.tugraz.asdafternoon3.ui.table;

import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.CleaningTaskCompleted;
import at.tugraz.asdafternoon3.data.Flat;

import javax.swing.event.TableModelEvent;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CleaningScheduleTableModel extends AbstractTableModel {

    private List<CleaningSchedule> cleaning_schedule = new ArrayList<>();

    public CleaningScheduleTableModel(List<CleaningSchedule> cleaning_schedule) {

        this.cleaning_schedule.addAll(cleaning_schedule);
    }


    @Override
    public int getRowCount() {
        return cleaning_schedule.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Name";
            case 1:
                return "Roommate";
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CleaningSchedule cs = cleaning_schedule.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cs.getName();
            case 1:
                return cs.getRoommate().getName();
            default:
                throw new IllegalArgumentException("Wrong column");
        }
    }


    public CleaningSchedule getElement(int rowIndex) {
        return cleaning_schedule.get(rowIndex);
    }


}
