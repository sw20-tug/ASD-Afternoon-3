package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.ShoppingListItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ShoppingListItemDAO extends DAO<ShoppingListItem> {

    public ShoppingListItemDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(ShoppingListItem object) {
        if(object.getItem().length() <= 0) {
            return false;
        }

        if(object.getFlat() == null) {
            return false;
        }

        return true;
    }

    @Override
    public ShoppingListItem create(ShoppingListItem object) throws Exception {
        if (!validate(object)) {
            return null;
        }

        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.persist(object);
            t.commit();
        }

        return object;
    }

    @Override
    public ShoppingListItem update(ShoppingListItem object) throws Exception {
        if (!validate(object)) {
            return null;
        }

        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.merge(object);
            t.commit();
        }

        return object;
    }

    @Override
    public List<ShoppingListItem> getAll() throws Exception {
        return null;
    }

    @Override
    public Long count() throws Exception {
        return null;
    }

    @Override
    public void delete(ShoppingListItem object) throws Exception {
        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.delete(object);
            t.commit();
        }
    }
}
