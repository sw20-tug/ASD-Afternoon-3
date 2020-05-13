package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.Roommate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoommateDAO extends DAO<Roommate> {

    public RoommateDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(Roommate object) {
        int count = object.getName().split(" ").length;
        if (count < 2) {
            return false;
        }

        if (object.getFlat() == null) {
            return false;
        }

        if (!object.getName().toLowerCase().matches("[a-z0-9 ]*")) {
            return false;
        }

        return true;
    }

    @Override
    public Roommate create(Roommate object) throws Exception {
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
    public Roommate update(Roommate object) throws Exception {
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
    public List<Roommate> getAll() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Roommate> cr = cb.createQuery(Roommate.class);
            Root<Roommate> root = cr.from(Roommate.class);
            cr.select(root);
            Query<Roommate> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    @Override
    public Long count() throws Exception {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<Roommate> root = cr.from(Roommate.class);
            cr.select(cb.count(root));
            Query<Long> query = session.createQuery(cr);
            return query.getSingleResult();
        }
    }

    @Override
    public void delete(Roommate object) throws Exception {
        try (Session session = openSession()) {
            Transaction t = session.beginTransaction();
            session.delete(object);
            t.commit();
        }
    }

    public List<CleaningSchedule> getCleaningSchedules(Roommate roommate) {
        try (Session session = openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CleaningSchedule> cr = cb.createQuery(CleaningSchedule.class);
            Root<CleaningSchedule> root = cr.from(CleaningSchedule.class);
            cr.select(root);
            cr.where(cb.equal(root.get("roommate"), roommate));
            Query<CleaningSchedule> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    public List<CleaningSchedule> getCompletedCleaningSchedules(Roommate roommate,
                                                       String intervall,
                                                       LocalDateTime start,
                                                       LocalDateTime end) {
        try (Session session = openSession()) {

            EntityManager em = session.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();

            List<Object[]> results = em.createQuery(
                    "SELECT cs.id, cs.name, cs.startTime, cs.roommate, cs.intervall " +
                            "FROM CleaningSchedule cs " +
                            "JOIN CleaningTaskCompleted ctc ON cs = ctc.cleaningSchedule "
                            ).
                            getResultList();


            List<CleaningSchedule> completedSchedules = new ArrayList<>();
            LocalDateTime timestamp;
            String currentIntervall;
            for (Object[] result : results) {
                System.out.println(result[0] + " " + result[1] + " - " + result[2]
                        + " - " + result[3]+ " - " + result[4]);

                timestamp = (LocalDateTime)result[2];
                if (timestamp.isBefore(start) || timestamp.isAfter(end))
                    continue;
                currentIntervall = (String)result[4];
                if (!intervall.equals(currentIntervall))
                    continue;

                completedSchedules.add(new CleaningSchedule(
                        (int)result[0], (String)result[1], (LocalDateTime)result[2],
                        (Roommate)result[3], (String)result[4]));
            }

            em.getTransaction().commit();
            em.close();

            return completedSchedules;
        }
    }
}
