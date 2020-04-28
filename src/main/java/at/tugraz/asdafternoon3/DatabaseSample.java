package at.tugraz.asdafternoon3;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Set;

public class DatabaseSample {

    public static void main(String[] args) {
        DatabaseSample sample = new DatabaseSample();
        sample.createSample();
    }

    private void createSample() {
        try (SessionFactory factory = DatabaseConnection.getInstance().getSessionFactory()) {
            Flat flat = new Flat("Test", 5, "Graz");
            Session session = factory.openSession();

            session.beginTransaction();
            session.persist(flat);

            System.out.println("created id " + flat.getId());

            Roommate r1 = new Roommate("Anna", flat);
            Roommate r2 = new Roommate("Paul", flat);

            session.persist(r1);
            session.persist(r2);

            session.getTransaction().commit();

            System.out.println("created roommates " + r1.getId() + ", " + r2.getId());
            session.close();

            // --------

            Session session1 = factory.openSession();
            Flat flat1 = session1.get(Flat.class, flat.getId());

            Set<Roommate> list = flat1.getRoommates();
            for (Roommate roommate : list) {
                System.out.println("roommate = " + roommate.getName());
            }

            session1.close();
        }
    }
}
