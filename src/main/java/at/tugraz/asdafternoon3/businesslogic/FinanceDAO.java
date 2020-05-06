package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Finance;
import at.tugraz.asdafternoon3.data.Roommate;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FinanceDAO extends DAO<Finance> {

    public FinanceDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(Finance object) {
        int costs = object.getCosts();
        if (costs < 0) {
            return false;
        }

        int nameLength = object.getName().length();
        if(nameLength <= 0) {
            return false;
        }

        if (object.getFlat() == null) {
            return false;
        }

        return true;
    }

    @Override
    public Finance create(Finance object) throws Exception {
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
    public Finance update(Finance object) throws Exception {
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
    public List<Finance> getAll() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Finance> cr = cb.createQuery(Finance.class);
            Root<Finance> root = cr.from(Finance.class);
            cr.select(root);
            Query<Finance> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Long count() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<Finance> root = cr.from(Finance.class);
            cr.select(cb.count(root));
            Query<Long> query = session.createQuery(cr);
            return query.getSingleResult();
        }
    }

    @Override
    public void delete(Finance object) throws Exception {
        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.delete(object);
            t.commit();
        }
    }
}
