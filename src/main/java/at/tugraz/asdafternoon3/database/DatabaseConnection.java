package at.tugraz.asdafternoon3.database;

import at.tugraz.asdafternoon3.data.Flat;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String databaseUrl = "jdbc:sqlite:flat.db"; // TODO: Check path
    private static DatabaseConnection conn = new DatabaseConnection();

    // TODO: optimize
    private Dao<Flat, Integer> flatDao;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        return conn;
    }

    public void initOrm() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
            flatDao = DaoManager.createDao(connectionSource, Flat.class);
            TableUtils.createTableIfNotExists(connectionSource, Flat.class);
            System.out.println("Created flat table");
            connectionSource.close();
        } catch (SQLException| IOException e) {
            e.printStackTrace();
        }
    }

    public Dao<Flat, Integer> getFlatDao() {
        return flatDao;
    }
}
