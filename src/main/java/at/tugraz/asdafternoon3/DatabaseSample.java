package at.tugraz.asdafternoon3;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import org.sqlite.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseSample {

    private static final String databaseUrl = "jdbc:sqlite:flat.db"; // TODO: Check path
    private Connection con;

    public static void main(String[] args) {
        DatabaseSample sample = new DatabaseSample();
        sample.init();
        sample.initOrm();
    }

    private void init() {
        try {
            this.con = DriverManager.getConnection(databaseUrl);
            DatabaseMetaData meta = con.getMetaData();
            ResultSet tables = meta.getTables(null, null, "flats", null);
            if (tables.next()) {
                System.out.println("Database and tables found, continuing.");
            } else {
                migrate();
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initOrm() {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
            // TODO
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void migrate() throws SQLException, IOException {
        // Run SQL files to create the necessary tables
        Path path = Paths.get(getClass().getClassLoader().getResource("migrations/flats.sql").getFile());
        String sql = StringUtils.join(Files.readAllLines(path), " ");

        Statement query = this.con.createStatement();
        query.executeUpdate(sql);
        System.out.println("Table flats created");
    }
}
