package at.tugraz.asdafternoon3.database;

import at.tugraz.asdafternoon3.businesslogic.DAO;
import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseConnectionTest {

    @Test
    public void getInstance() {
        assertNotNull(DatabaseConnection.getInstance());
    }

    @Test
    public void createDao() throws Exception {
        DAO dao = DatabaseConnection.getInstance().createDao(FlatDAO.class);
        assertNotNull(dao);
        assertTrue(dao instanceof FlatDAO);
    }
}