package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningSchedule;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertTrue;

public class CleaningScheduleDAOTest extends DAOTest {

    private Flat testflat;
    private Roommate testmate;

    public CleaningScheduleDAOTest() {
        this.testflat = new Flat("ChaosWG", 22, "Ragnitzstraße 102");
        this.testmate = new Roommate("Mark Weizenberg", 23, testflat);
    }

    @Test
    public void validateSchedule() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateAndTime = LocalDateTime.of(currentDate, currentTime);

        CleaningSchedule cleaningschedule =
                new CleaningSchedule("Partykeller", currentDateAndTime, testmate);

        CleaningScheduleDAO creator = new CleaningScheduleDAO(null);
        assertTrue(creator.validate(cleaningschedule));

    }
}
