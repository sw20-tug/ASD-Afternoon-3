package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

public class FlatCreator {

    public FlatCreator() {
    }

    public boolean validateFlat(Flat flat) {
        if (flat.getName().length() == 0) {
            return false;
        }

        if (flat.getAddress().length() == 0) {
            return false;
        }

        if (flat.getSize() == 0) {
            return false;
        }

        // TODO: Name should not contain any special characters
        String str;
        str = flat.getName().toLowerCase();

        char[] charArray = str.toCharArray();
        for (int i=0; i < charArray.length; i++)
        {
            char ch = charArray[i];
            if (!((ch >= 'A' && ch <= 'z') || ch == ' '))
            {
                return false;
            }
        }

        return true;
    }

    public Flat createFlat(Flat flat) throws SQLException {
        Dao<Flat, Integer> dao = DatabaseConnection.getInstance().getFlatDao();
        dao.create(flat);
        return flat;
    }

    public Flat updateFlat(Flat flat) throws SQLException {
        Dao<Flat, Integer> dao = DatabaseConnection.getInstance().getFlatDao();
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
}
