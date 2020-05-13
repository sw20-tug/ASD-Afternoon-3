package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningSchedule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CleaningScheduleDAO extends DAO<CleaningSchedule> {
    protected CleaningScheduleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(CleaningSchedule object) {
        if (object == null) {
            return false;
        }
        if (object.getName() == null || object.getName().isEmpty()) {
            return false;
        }
        if (object.getStartTime() == null) {
            return false;
        }
        if (object.getCleaner() == null) {
            return false;
        }
        if (!"weekly".equals(object.getIntervall()) && !"monthly".equals(object.getIntervall()))
        {
            return false;
        }
        return true;
    }

    @Override
    public CleaningSchedule create(CleaningSchedule object) throws Exception {
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
    public CleaningSchedule update(CleaningSchedule object) throws Exception {
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
    public List<CleaningSchedule> getAll() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CleaningSchedule> cr = cb.createQuery(CleaningSchedule.class);
            Root<CleaningSchedule> root = cr.from(CleaningSchedule.class);
            cr.select(root);
            Query<CleaningSchedule> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Long count() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<CleaningSchedule> root = cr.from(CleaningSchedule.class);
            cr.select(cb.count(root));
            Query<Long> query = session.createQuery(cr);
            return query.getSingleResult();
        }
    }

    @Override
    public void delete(CleaningSchedule object) throws Exception {
        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.delete(object);
            t.commit();
        }
    }
}
