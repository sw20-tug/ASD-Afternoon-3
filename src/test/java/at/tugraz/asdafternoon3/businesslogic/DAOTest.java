package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.TestUtils;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.lang.invoke.MethodHandles;

public abstract class DAOTest {

    protected static SessionFactory database;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        database = TestUtils.createTestDatabase(MethodHandles.lookup().lookupClass().getName());
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        database.close();
        TestUtils.closeTestDatabase(MethodHandles.lookup().lookupClass().getName());
        database = null;
    }

}
