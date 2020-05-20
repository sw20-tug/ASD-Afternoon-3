package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.FinanceFlat;
import jdk.jshell.spi.ExecutionControl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FinanceFlatDAO extends DAO<FinanceFlat> {

    public FinanceFlatDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(FinanceFlat object) {
        int price = object.getPrice();
        if (price < 0) {
            return false;
        }

        int titleLength = object.getTitle().length();
        if(titleLength <= 0) {
            return false;
        }

        if (object.getFlat() == null) {
            return false;
        }

        return true;
    }

    @Override
    public FinanceFlat create(FinanceFlat object) throws Exception {
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
    public FinanceFlat update(FinanceFlat object) throws Exception {
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
    public List<FinanceFlat> getAll() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<FinanceFlat> cr = cb.createQuery(FinanceFlat.class);
            Root<FinanceFlat> root = cr.from(FinanceFlat.class);
            cr.select(root);
            Query<FinanceFlat> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Long count() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<FinanceFlat> root = cr.from(FinanceFlat.class);
            cr.select(cb.count(root));
            Query<Long> query = session.createQuery(cr);
            return query.getSingleResult();
        }
    }

    @Override
    public void delete(FinanceFlat object) throws Exception {
        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.delete(object);
            t.commit();
        }
    }
}
