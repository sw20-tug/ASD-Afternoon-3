package at.tugraz.asdafternoon3;

import at.tugraz.asdafternoon3.data.Flat;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.TableUtils;
import org.sqlite.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseSample {

    private static final String databaseUrl = "jdbc:sqlite:flat.db"; // TODO: Check path

    public static void main(String[] args) {
        DatabaseSample sample = new DatabaseSample();
        sample.initOrm();
    }

    private void initOrm() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
            Dao<Flat, Integer> flatDao = DaoManager.createDao(connectionSource, Flat.class);
            TableUtils.createTableIfNotExists(connectionSource, Flat.class);

            Flat flat = new Flat("Test", 5, "Graz");
            flatDao.create(flat);

            System.out.println("created id " + flat.getId());

            connectionSource.close();

        } catch (SQLException|IOException e) {
            e.printStackTrace();
        }
    }
}
