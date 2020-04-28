package at.tugraz.asdafternoon3.database;

import at.tugraz.asdafternoon3.businesslogic.DAO;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.InvocationTargetException;

public class DatabaseConnection {

    private static DatabaseConnection conn = new DatabaseConnection();
    private SessionFactory sessionFactory;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        return conn;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public <T extends DAO> T createDao(Class<T> type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return type.getDeclaredConstructor(SessionFactory.class).newInstance(getSessionFactory());
    }
}
