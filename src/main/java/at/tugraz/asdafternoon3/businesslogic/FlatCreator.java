package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

public class FlatCreator extends Creator<Flat> {

    public FlatCreator() {
    }

    @Override
    public boolean validate(Flat object) {
        if (object.getName().length() == 0) {
            return false;
        }

        if (object.getAddress().length() == 0) {
            return false;
        }

        if (object.getSize() == 0) {
            return false;
        }

        // TODO: Name should not contain any special characters
        String str;
        str = object.getName().toLowerCase();

        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (!((ch >= 'A' && ch <= 'z') || ch == ' ')) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Flat create(Flat object) throws SQLException {
        Dao<Flat, Integer> dao = DatabaseConnection.getInstance().getFlatDao();
        if(!validateFlat(flat)){
            return null;
        }
        ensureUniqueCurrentFlat(flat);
        dao.create(object);
        return object;
    }

    public Flat updateFlat(Flat flat) throws Exception {
        Dao<Flat, Integer> dao = DatabaseConnection.getInstance().getFlatDao();

        if(!validateFlat(flat)){
            return null;
        }
        ensureUniqueCurrentFlat(flat);

        int lines_changed = dao.update(flat);

        if(lines_changed == 0)
        {
            throw new SQLDataException("Flat not found");
        }

        return flat;
    }


    public Flat getCurrentFlat() throws Exception {
        Dao<Flat, Integer> dao = DatabaseConnection.getInstance().getFlatDao();
        QueryBuilder<Flat, Integer> qb = dao.queryBuilder();
        qb.where().eq("is_current", true);
        List<Flat> current_flats = qb.query();
        if (current_flats.size() == 0)
        {
            return null;
        }
        if (current_flats.size() > 1)
        {
            throw new Exception("More than one flats set as current");
        }
        return current_flats.get(0);
    }

    private void ensureUniqueCurrentFlat(Flat flat) throws Exception {
        if(flat.isCurrent())
        {
            Flat possible_current_flat = getCurrentFlat();
            if(possible_current_flat != null )
            {
                possible_current_flat.setIsCurrent(false);
                updateFlat(possible_current_flat);
            }
        }
    }
}
