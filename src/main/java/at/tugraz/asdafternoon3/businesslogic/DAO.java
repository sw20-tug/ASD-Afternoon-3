package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.DatabaseObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class DAO<T extends DatabaseObject> {

    protected final SessionFactory sessionFactory;

    protected DAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Opens a new session, to be used in the implementations
     *
     * @return a session
     */
    public Session openSession() {
        return sessionFactory.openSession();
    }

    /**
     * Validates the parameter of the object.
     *
     * @param object the object to validate
     * @return true if data is valid, otherwise false
     */
    public abstract boolean validate(T object);

    /**
     * Will attempt to persist the object. Does a validate
     * check first, if this fails then `null` is returned.
     *
     * @param object the object to persist
     * @return object with set id if success, otherwise `null`
     * @throws Exception if there are persist problems
     */
    public abstract T create(T object) throws Exception;

    /**
     * Will attempt to update the object. Does a validation
     * first and ensures uniqueness of the current flat flag.
     *
     * @param object the object to update
     * @return object
     * @throws Exception if there are update problems
     */
    public abstract T update(T object) throws Exception;

    /**
     * Get a list of all rows in the table
     *
     * @return list
     * @throws Exception if there a fetch problems
     */
    public abstract List<T> getAll() throws Exception;

    /**
     * Get a list of all rows in the table
     *
     * @return list
     * @throws Exception if there a fetch problems
     */
    public abstract Long count() throws Exception;
}

/*
    NOTICE: It would be better to have a new interface (or abstract class)
    for every sub-dao e.g. FlatDAO that specifies individual methods that
    will then be implemented by e.g. FlatDbImpl. Using this approach it
    would be possible to easily swap implementations and e.g. add a
    cloud solution.
 */