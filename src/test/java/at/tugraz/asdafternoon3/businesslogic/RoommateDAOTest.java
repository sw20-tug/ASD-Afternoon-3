package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.CleaningTaskCompleted;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class RoommateDAOTest extends DAOTest {

    @Test
    public void createRoommateValid() {
        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("Liki Norber", 12, flat);

        RoommateDAO creator = new RoommateDAO(null);
        assertTrue(creator.validate(roommate));
    }

    @Test
    public void createRoommateInvalid() {
        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("icecreamforcrow", 21, flat);

        RoommateDAO creator = new RoommateDAO(null);
        assertFalse(creator.validate(roommate));
    }

    @Test
    public void createRoommateInvalidCharacters() {
        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("blueöystercult", 42, flat);

        RoommateDAO creator = new RoommateDAO(null);
        assertFalse(creator.validate(roommate));
    }

    @Test
    public void createRoommateNoFlat() {
        Roommate roommate = new Roommate("Andi Goldberger", 50, null);

        RoommateDAO creator = new RoommateDAO(null);
        assertFalse(creator.validate(roommate));
    }

    @Test
    public void getCleaningSchedules() {

        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("Andi Goldberger", 50, flat);

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningSchedule = new CleaningSchedule("Garten", currentDateAndTime, roommate, "weekly");

        FlatDAO flatDAO = new FlatDAO(database);
        RoommateDAO roommateDAO = new RoommateDAO(database);
        CleaningScheduleDAO cleaningScheduleDAO = new CleaningScheduleDAO(database);

        try {
            flatDAO.create(flat);
            roommateDAO.create(roommate);
            int size = roommateDAO.getCleaningSchedules(roommate, "weekly").size();
            cleaningScheduleDAO.create(cleaningSchedule);

            List<CleaningSchedule> cleaningScheduleList = roommateDAO.getCleaningSchedules(roommate, "weekly");
            System.out.println(cleaningScheduleList.toString());
            assertEquals(size + 1, cleaningScheduleList.size());

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void getUncompletedCleaningSchedules() {

        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("Andi Goldberger", 50, flat);

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningSchedule =
                new CleaningSchedule("Garten", currentDateAndTime,
                        roommate, "weekly");
        CleaningSchedule cleaningSchedule2 =
                new CleaningSchedule("Lounge", currentDateAndTime,
                        roommate, "monthly");

        CleaningTaskCompleted cleaningTaskCompleted =
                new CleaningTaskCompleted(cleaningSchedule, currentDateAndTime.plusHours(1));

        FlatDAO flatDAO = new FlatDAO(database);
        RoommateDAO roommateDAO = new RoommateDAO(database);
        CleaningScheduleDAO cleaningScheduleDAO = new CleaningScheduleDAO(database);
        CleaningTaskCompletedDAO cleaningTaskCompletedDAO = new CleaningTaskCompletedDAO(database);

        try {
            flatDAO.create(flat);
            roommateDAO.create(roommate);

            cleaningScheduleDAO.create(cleaningSchedule);
            cleaningScheduleDAO.create(cleaningSchedule2);

            cleaningTaskCompletedDAO.create(cleaningTaskCompleted);

            List<CleaningSchedule> completedCleaningSchedules =
                    roommateDAO.getCompletedCleaningSchedules(roommate, "monthly", currentDateAndTime.minusDays(1), currentDateAndTime.plusDays(1));
            System.out.println(completedCleaningSchedules.toString());
            assertEquals(0, completedCleaningSchedules.size());



            completedCleaningSchedules =
                    roommateDAO.getCompletedCleaningSchedules(roommate, "weekly", currentDateAndTime.minusDays(1), currentDateAndTime.plusDays(1));
            System.out.println(completedCleaningSchedules.toString());
            assertEquals(1, completedCleaningSchedules.size());

            completedCleaningSchedules =
                    roommateDAO.getCompletedCleaningSchedules(
                            roommate, "weekly",
                            currentDateAndTime.minusDays(4), currentDateAndTime.minusDays(3));
            System.out.println(completedCleaningSchedules.toString());
            assertEquals(0, completedCleaningSchedules.size());

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }
}