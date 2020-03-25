package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class FlatCreator {

    public FlatCreator() {
    }

    public boolean validateFlat(Flat flat) {
        return true;
    }

    public Flat createFlat(Flat flat) throws SQLException {
        Dao<Flat, Integer> dao = DatabaseConnection.getInstance().getFlatDao();
        dao.create(flat);
        return flat;
    }
}
