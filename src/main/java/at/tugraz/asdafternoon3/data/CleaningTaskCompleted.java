package at.tugraz.asdafternoon3.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CleaningTaskCompleted extends DatabaseObject{

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CleaningSchedule cleaningSchedule;

    @Column(nullable = false)
    private LocalDateTime completed;

    public CleaningTaskCompleted() {
        this.id = 0;
        this.cleaningSchedule = null;
        this.completed = null;
    }

    public CleaningTaskCompleted(CleaningSchedule cleaningSchedule, LocalDateTime completed) {
        this.id = 0;
        this.cleaningSchedule = cleaningSchedule;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CleaningSchedule getCleaningSchedule() {
        return cleaningSchedule;
    }

    public void setCleaningSchedule(CleaningSchedule cleaningSchedule) {
        this.cleaningSchedule = cleaningSchedule;
    }

    public LocalDateTime getCompleted() {
        return completed;
    }

    public void setCompleted(LocalDateTime completed) {
        this.completed = completed;
    }
}
