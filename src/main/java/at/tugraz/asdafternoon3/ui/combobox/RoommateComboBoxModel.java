package at.tugraz.asdafternoon3.ui.combobox;

import at.tugraz.asdafternoon3.data.Roommate;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class RoommateComboBoxModel extends AbstractListModel<Roommate> implements ComboBoxModel<Roommate> {

    private final List<Roommate> roommates = new ArrayList<>();
    private int selection = -1;

    public RoommateComboBoxModel(List<Roommate> roommates) {
        this.roommates.addAll(roommates);
    }


    @Override
    public void setSelectedItem(Object anItem) {
        selection = this.roommates.indexOf(anItem);
    }

    @Override
    public Roommate getSelectedItem() {
        return getElementAt(selection);
    }

    @Override
    public int getSize() {
        return roommates.size();
    }

    @Override
    public Roommate getElementAt(int index) {
        if (index < 0) {
            return null;
        }
        return roommates.get(index);
    }
}
