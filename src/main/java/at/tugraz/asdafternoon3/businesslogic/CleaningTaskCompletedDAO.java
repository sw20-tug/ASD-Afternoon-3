package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningTaskCompleted;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CleaningTaskCompletedDAO extends DAO<CleaningTaskCompleted> {

    public CleaningTaskCompletedDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(CleaningTaskCompleted object) {
        if (object.getCleaningSchedule() == null)
            return false;
        if (object.getCompleted() == null)
            return false;
        return true;
    }

    @Override
    public CleaningTaskCompleted create(CleaningTaskCompleted object) throws Exception {
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
    public CleaningTaskCompleted update(CleaningTaskCompleted object) throws Exception {
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
    public List<CleaningTaskCompleted> getAll() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CleaningTaskCompleted> cr = cb.createQuery(CleaningTaskCompleted.class);
            Root<CleaningTaskCompleted> root = cr.from(CleaningTaskCompleted.class);
            cr.select(root);
            Query<CleaningTaskCompleted> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Long count() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<CleaningTaskCompleted> root = cr.from(CleaningTaskCompleted.class);
            cr.select(cb.count(root));
            Query<Long> query = session.createQuery(cr);
            return query.getSingleResult();
        }
    }

    @Override
    public void delete(CleaningTaskCompleted object) throws Exception {
        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.delete(object);
            t.commit();
        }
    }
}