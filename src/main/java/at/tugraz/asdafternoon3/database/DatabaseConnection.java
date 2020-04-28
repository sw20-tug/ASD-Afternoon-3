package at.tugraz.asdafternoon3.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConnection {

    private static DatabaseConnection conn = new DatabaseConnection();
    private SessionFactory sessionFactory;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        return conn;
    }

    public void initOrm() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO: Rethink the decision to use this method
        // Don't do this..
        // sessionFactory.close();
    }
}
