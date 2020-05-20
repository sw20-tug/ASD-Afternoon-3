package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningIntervall;
import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Test;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class CleaningScheduleDAOTest extends DAOTest {

    private Flat testflat;
    private Roommate testmate;

    public CleaningScheduleDAOTest() {
        this.testflat = new Flat("ChaosWG", 22, "Ragnitzstraße 102");
        this.testmate = new Roommate("Mark Weizenberg", 23, testflat);
        FlatDAO flatDAO = new FlatDAO(database);
        RoommateDAO roommateDAO = new RoommateDAO(database);

        try {
            flatDAO.create(testflat);
            roommateDAO.create(testmate);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @After
    public void after() {
        String stringQuery = "DELETE FROM CleaningSchedule ";
        try (Session session = database.openSession()) {
            Query query = session.createQuery(stringQuery);
            Transaction t = session.beginTransaction();
            query.executeUpdate();
            t.commit();
        }
    }

    @Test
    public void validateSchedule() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("Partykeller", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(null);
        assertTrue(creator.validate(cleaningschedule));
    }

    @Test
    public void invalidSchedule() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(null);
        assertFalse(creator.validate(cleaningschedule));

        cleaningschedule.setName("Partykeller");
        cleaningschedule.setStartTime(null);
        assertFalse(creator.validate(cleaningschedule));

        cleaningschedule.setStartTime(currentDateAndTime);
        cleaningschedule.setRoommate(null);
        assertFalse(creator.validate(cleaningschedule));

        cleaningschedule.setRoommate(testmate);
        cleaningschedule.setIntervall(null);
        assertFalse(creator.validate(cleaningschedule));
    }

    @Test
    public void create() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("Keller", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(database);
        try {
            assertNotNull(creator.create(cleaningschedule));
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void update() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("Keller", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(database);
        try {
            assertNotNull(creator.create(cleaningschedule));
            cleaningschedule.setName("Balkon");
            assertNotNull(creator.update(cleaningschedule));
            System.out.println(creator.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
        ;
    }

    @Test
    public void getAll() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("Keller", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(database);
        try {
            assertNotNull(creator.create(cleaningschedule));
            assertNotEquals(0, creator.getAll().size());
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void count() throws Exception {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("Keller", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(database);
        try {
            assertNotNull(creator.create(cleaningschedule));
            assertTrue(creator.count().equals(1L));
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void delete() throws Exception {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("Keller", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(database);
        try {
            assertNotNull(creator.create(cleaningschedule));
            creator.delete(cleaningschedule);
            assertTrue(creator.count().equals(0L));
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}
