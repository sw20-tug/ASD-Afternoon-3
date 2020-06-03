package at.tugraz.asdafternoon3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mockito.Mockito;

import java.io.File;

public class TestUtils {

    public static SessionFactory createTestDatabase(String identifier) {
        Configuration config = new Configuration();
        config.configure();

        // Use the default configuration (as its fine) but change the connection
        config.setProperty("hibernate.connection.url", "jdbc:sqlite:flat_test_" + identifier + ".db");
        return config.buildSessionFactory();
    }

    public static void closeTestDatabase(String identifier) {
        String filename = "flat_test_" + identifier + ".db";

        File file = new File(filename);
        assert file.exists();
        assert file.delete();
    }

    public static SessionFactory mockDatabase() {
        return Mockito.mock(SessionFactory.class);
    }

    public static Session mockSession(SessionFactory mock) {
        Session sessionMock = Mockito.mock(Session.class);
        Transaction transactionMock = Mockito.mock(Transaction.class);

        Mockito.when(mock.openSession())
                .thenReturn(sessionMock);

        Mockito.when(sessionMock.beginTransaction())
                .thenReturn(transactionMock);

        return sessionMock;
    }
}
