package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningIntervall;
import at.tugraz.asdafternoon3.data.CleaningSchedule;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.util.TimeZoneCache;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class CalenderExportTest {

    private List<CleaningSchedule> generateSampleSchedule(int amount, CleaningIntervall intervall) {
        List<CleaningSchedule> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            list.add(new CleaningSchedule("task " + intervall + " " + i, LocalDateTime.now(), null, intervall));
        }
        return list;
    }

    @Test
    public void canExport() {
        CalenderExport export = new CalenderExport(generateSampleSchedule(5, CleaningIntervall.MONTHLY));
        String output = export.export();
        assertNotEquals(0, output.length());
    }

    @Test
    public void canParseString() throws IOException, ParserException {
        CalenderExport export = new CalenderExport(generateSampleSchedule(5, CleaningIntervall.MONTHLY));
        String output = export.export();

        StringReader sin = new StringReader(output);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(sin);
        assertNotNull(calendar);
    }

    @Test
    public void canParseFile() throws IOException, ParserException {
        CalenderExport export = new CalenderExport(generateSampleSchedule(5, CleaningIntervall.WEEKLY));
        Random randi = new Random();
        String filename = "test_cal_export_" + Math.abs(randi.nextInt()) + ".ics";
        export.export(filename);

        FileInputStream fin = new FileInputStream(filename);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        assertNotNull(calendar);
        fin.close();

        File file = new File(filename);
        assertTrue(file.delete());
    }

    @Test
    public void canParseEvent() {
        List<CleaningSchedule> list = generateSampleSchedule(1, CleaningIntervall.WEEKLY);
        CalenderExport export = new CalenderExport(list);
        String output = export.export();
        expectEvent(list.get(0), output);
    }

    @Test
    public void canParseEventMonthly() {
        List<CleaningSchedule> list = generateSampleSchedule(1, CleaningIntervall.MONTHLY);
        CalenderExport export = new CalenderExport(list);
        String output = export.export();
        expectEvent(list.get(0), output);
    }

    @Test
    public void canParseEventWithLib() throws IOException, ParserException {
        List<CleaningSchedule> schedule = generateSampleSchedule(1, CleaningIntervall.MONTHLY);
        CalenderExport export = new CalenderExport(schedule);
        String output = export.export();

        StringReader sin = new StringReader(output);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(sin);

        ComponentList<CalendarComponent> list = calendar.getComponents();
        assertEquals(1, list.size());

        CalendarComponent component = list.get(0);
        assertEquals(VEvent.class, component.getClass());

        VEvent event = (VEvent) component;
        PropertyList<Property> properties = event.getProperties();
        assertNotEquals(0, properties.size());

        boolean hasSummary = false;
        boolean hasRule = false;

        for (Property prop : properties) {
            if (prop instanceof Summary) {
                assertEquals(schedule.get(0).getName(), prop.getValue());
                hasSummary = true;
            }
            if (prop instanceof RRule) {
                assertEquals(Recur.Frequency.MONTHLY, ((RRule) prop).getRecur().getFrequency());
                hasRule = true;
            }
        }

        assertTrue(hasSummary);
        assertTrue(hasRule);
    }

    private void expectEvent(CleaningSchedule schedule, String output) {
        String[] lines = output.split("\\r?\\n");
        assertEquals("BEGIN:VCALENDAR", lines[0]);
        assertEquals("PRODID:-//Flat//iCal4j 1.0//EN", lines[1]);
        assertEquals("VERSION:2.0", lines[2]);
        assertEquals("CALSCALE:GREGORIAN", lines[3]);
        assertEquals("BEGIN:VEVENT", lines[4]);

        boolean hasSummary = false;
        boolean hasRule = false;

        for (int i = 5; i < lines.length - 2; i++) {
            if (lines[i].contains("SUMMARY")) {
                // Check if name is the same as supplied
                assertEquals(schedule.getName(), lines[i].split(":")[1]);
                hasSummary = true;
            }
            if (lines[i].contains("RRULE")) {
                Matcher matcher = Pattern.compile("RRULE:FREQ=([A-Z]*).*").matcher(lines[i]);
                assertTrue(matcher.find());
                assertEquals(schedule.getIntervall().toString(), matcher.group(1));
                hasRule = true;
            }
        }

        assertTrue(hasSummary);
        assertTrue(hasRule);

        assertEquals("END:VEVENT", lines[lines.length - 2]);
        assertEquals("END:VCALENDAR", lines[lines.length - 1]);
    }
}