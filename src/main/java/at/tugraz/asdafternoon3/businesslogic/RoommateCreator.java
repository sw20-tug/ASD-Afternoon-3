package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class RoommateCreator extends Creator<Roommate> {

    public RoommateCreator() {
    }


    @Override
    public boolean validate(Roommate object) {
        int count = object.getName().split(" ").length;
        if (count < 2) {
            return false;
        }

        if (object.getFlat() == null) {
            return false;
        }

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
    public Roommate create(Roommate object) throws SQLException {
        DatabaseConnection.getInstance().getRoommateDao().create(object);
        return object;
    }
}
