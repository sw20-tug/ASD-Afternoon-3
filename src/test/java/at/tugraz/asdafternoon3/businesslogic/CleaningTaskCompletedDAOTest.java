package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Test;

import javax.persistence.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class CleaningTaskCompletedDAOTest extends DAOTest {

    private CleaningSchedule cleaningSchedule;
    private LocalDateTime currentDateAndTime;
    private CleaningTaskCompletedDAO cleaningTaskCompletedDAO;

    public CleaningTaskCompletedDAOTest() {

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        this.currentDateAndTime = LocalDateTime.of(currentDate, currentTime);


        Flat testflat = new Flat("ChaosWG", 22, "Ragnitzstraße 102");
        Roommate testmate = new Roommate("Mark Weizenberg", 23, testflat);
        cleaningSchedule = new CleaningSchedule("Pool", currentDateAndTime, testmate, CleaningIntervall.WEEKLY);

        FlatDAO flatDAO = new FlatDAO(database);
        RoommateDAO roommateDAO = new RoommateDAO(database);
        CleaningScheduleDAO cleaningScheduleDAO = new CleaningScheduleDAO(database);
        cleaningTaskCompletedDAO = new CleaningTaskCompletedDAO(database);

        try {
            flatDAO.create(testflat);
            roommateDAO.create(testmate);
            cleaningScheduleDAO.create(cleaningSchedule);
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }

    }

    @After
    public void tearDown() throws Exception {
        String stringQuery = "DELETE FROM CleaningTaskCompleted ";
        try(Session session = database.openSession()) {
            Query query = session.createQuery(stringQuery);
            Transaction t = session.beginTransaction();
            query.executeUpdate();
            t.commit();
        }
    }

    @Test
    public void validate() {
        CleaningTaskCompleted cleaningTaskCompleted =
                new CleaningTaskCompleted(cleaningSchedule, currentDateAndTime.plusDays(2));

        assertTrue(cleaningTaskCompletedDAO.validate(cleaningTaskCompleted));

        cleaningTaskCompleted.setCleaningSchedule(null);
        assertFalse(cleaningTaskCompletedDAO.validate(cleaningTaskCompleted));

        cleaningTaskCompleted.setCleaningSchedule(cleaningSchedule);
        cleaningTaskCompleted.setCompleted(null);
        assertFalse(cleaningTaskCompletedDAO.validate(cleaningTaskCompleted));
    }

    @Test
    public void create()  {

        CleaningTaskCompleted cleaningTaskCompleted =
                new CleaningTaskCompleted(cleaningSchedule, currentDateAndTime.plusDays(2));

        try {
            assertNotNull(cleaningTaskCompletedDAO.create(cleaningTaskCompleted));
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void update() {
        CleaningTaskCompleted cleaningTaskCompleted =
                new CleaningTaskCompleted(cleaningSchedule, currentDateAndTime.plusDays(2));

        try {
            cleaningTaskCompletedDAO.create(cleaningTaskCompleted);
            cleaningTaskCompleted.setCompleted(currentDateAndTime.plusDays(3));
            assertNotNull(cleaningTaskCompletedDAO.update(cleaningTaskCompleted));
            System.out.println(cleaningTaskCompleted.getCompleted().toString());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void getAll() {
        CleaningTaskCompleted cleaningTaskCompleted =
                new CleaningTaskCompleted(cleaningSchedule, currentDateAndTime.plusDays(2));

        try {
            cleaningTaskCompletedDAO.create(cleaningTaskCompleted);

            assertNotEquals(0, cleaningTaskCompletedDAO.getAll().size());
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void count() throws Exception {
        CleaningTaskCompleted cleaningTaskCompleted =
                new CleaningTaskCompleted(cleaningSchedule, currentDateAndTime.plusDays(2));

        try {
            cleaningTaskCompletedDAO.create(cleaningTaskCompleted);

            assertTrue(cleaningTaskCompletedDAO.count().equals(1L));
        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    @Test
    public void delete() throws Exception {

        CleaningTaskCompleted cleaningTaskCompleted =
                new CleaningTaskCompleted(cleaningSchedule, currentDateAndTime.plusDays(2));

        try {
            cleaningTaskCompletedDAO.create(cleaningTaskCompleted);
            cleaningTaskCompletedDAO.delete(cleaningTaskCompleted);
            assertTrue(cleaningTaskCompletedDAO.count().equals(0L));

        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }


}