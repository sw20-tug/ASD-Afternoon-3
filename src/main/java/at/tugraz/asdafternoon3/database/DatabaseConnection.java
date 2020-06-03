package at.tugraz.asdafternoon3.database;

import at.tugraz.asdafternoon3.businesslogic.DAO;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.InvocationTargetException;

public class DatabaseConnection {

    private static DatabaseConnection conn;
    private SessionFactory sessionFactory;
    private Configuration defaultConfig;

    private DatabaseConnection(Configuration defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    public static DatabaseConnection getInstance(Configuration defaultConfig) {
        if(conn == null) {
            conn = new DatabaseConnection(defaultConfig);
        }
        return conn;
    }

    public static DatabaseConnection getInstance(){
        return getInstance(new Configuration());
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = defaultConfig.configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    @SuppressWarnings("rawtypes")
    public <T extends DAO> T createDao(Class<T> type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return type.getDeclaredConstructor(SessionFactory.class).newInstance(getSessionFactory());
    }
}
