package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningSchedule;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;


import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CalenderExport {
    private final List<CleaningSchedule> entries;

    public CalenderExport(List<CleaningSchedule> entries) {
        this.entries = new ArrayList<>();
        if (entries != null) {
            this.entries.addAll(entries);
        }
    }

    public String export() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Flat//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        for (CleaningSchedule entry : entries) {
            Recur recur = new Recur(Recur.Frequency.WEEKLY, Integer.MAX_VALUE);
            RRule rrule = new RRule(recur);
            VEvent event = new VEvent();
            event.getProperties().add(new Uid(String.valueOf(entry.getId())));
            event.getProperties().add(rrule);
            event.getProperties().add(new Summary(entry.getName()));
            event.getProperties().add(new DtStart(new Date(entry.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())));
            calendar.getComponents().add(event);
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CalendarOutputter outputter = new CalendarOutputter();

        try {
            outputter.output(calendar, output);
        } catch (IOException e) {
            return "";
        }
        return new String(output.toByteArray());
    }

    public void export(String filename) throws IOException {
        File file = new File(filename);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            writer.write(export());
        }
    }

    public static void main(String[] args) {
        List<CleaningSchedule> list = new ArrayList<>();
        list.add(new CleaningSchedule("mario", LocalDateTime.of(2020, 05, 13, 12, 07, 01), null));
        list.add(new CleaningSchedule("niki", LocalDateTime.of(2020, 05, 15, 15, 07, 01), null));
        try {
            new CalenderExport(list).export("mycalendar.ics");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

