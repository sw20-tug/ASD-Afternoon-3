package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class FlatDAO extends DAO<Flat> {

    public FlatDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(Flat object) {
        if (object.getName().length() == 0) {
            return false;
        }

        if (object.getAddress().length() == 0) {
            return false;
        }

        if (object.getSize() == 0) {
            return false;
        }

        if (!object.getName().toLowerCase().matches("[a-z0-9 ]*")) {
            return false;
        }

        return true;
    }

    @Override
    public Flat create(Flat object) throws Exception {
        if (!validate(object)) {
            return null;
        }

        ensureUniqueCurrentFlat(object);

        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.persist(object);
            t.commit();
        }

        return object;
    }

    @Override
    public Flat update(Flat object) throws Exception {
        if (!validate(object)) {
            return null;
        }

        ensureUniqueCurrentFlat(object);

        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.merge(object);
            t.commit();
        }

        return object;
    }

    @Override
    public List<Flat> getAll() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Flat> cr = cb.createQuery(Flat.class);
            Root<Flat> root = cr.from(Flat.class);
            cr.select(root);
            Query<Flat> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Long count() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<Flat> root = cr.from(Flat.class);
            cr.select(cb.count(root));
            Query<Long> query = session.createQuery(cr);
            return query.getSingleResult();
        }
    }

    public Flat getCurrentFlat() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Flat> cr = cb.createQuery(Flat.class);
            Root<Flat> root = cr.from(Flat.class);
            cr.select(root);
            cr.where(cb.isTrue(root.get("isCurrent")));
            Query<Flat> query = session.createQuery(cr);
            List<Flat> result = query.getResultList();

            if (result.size() == 0) {
                return null;
            }

            if (result.size() > 1) {
                throw new Exception("more than one flats set as current");
            }

            return result.get(0);
        }
    }

    private void ensureUniqueCurrentFlat(Flat flat) throws Exception {
        if (flat.isCurrent()) {
            Flat possible_current_flat = getCurrentFlat();
            if (possible_current_flat != null) {
                possible_current_flat.setIsCurrent(false);
                update(possible_current_flat);
            }
        }
    }
}
