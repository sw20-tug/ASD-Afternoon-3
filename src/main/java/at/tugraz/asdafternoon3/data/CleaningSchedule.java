package at.tugraz.asdafternoon3.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CleaningSchedule extends DatabaseObject {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private LocalDateTime startTime;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Roommate cleaner;

    public CleaningSchedule() {
        this.name = "";
        this.id = 0;
        this.startTime = null;
        this.cleaner = null;
    }

    public CleaningSchedule(String name, LocalDateTime startTime,
                            Roommate cleaner) {
        this.name = name;
        this.startTime = startTime;
        this.cleaner = cleaner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Roommate getCleaner() {
        return cleaner;
    }

    public void setCleaner(Roommate cleaner) {
        this.cleaner = cleaner;
    }

    @Override
    public String toString() {
        return "CleaningSchedule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", cleaner=" + cleaner +
                '}';
    }
}
