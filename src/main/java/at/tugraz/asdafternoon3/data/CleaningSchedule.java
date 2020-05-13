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

    @Column
    private String intervall;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Roommate roommate;

    public CleaningSchedule() {
        this.name = "";
        this.id = 0;
        this.startTime = null;
        this.roommate = null;
        this.intervall = null;
    }

    public CleaningSchedule(String name, LocalDateTime startTime,
                            Roommate roommate, String intervall) {
        this.name = name;
        this.startTime = startTime;
        this.roommate = roommate;
        this.intervall = intervall;
    }

    public CleaningSchedule(int id, String name, LocalDateTime startTime,
                            Roommate roommate, String intervall) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.roommate = roommate;
        this.intervall = intervall;
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

    public Roommate getRoommate() {
        return roommate;
    }

    public void setRoommate(Roommate cleaner) {
        this.roommate = cleaner;
    }


    public String getIntervall() {
        return intervall;
    }

    public void setIntervall(String intervall) {
        this.intervall = intervall;
    }

    @Override
    public String toString() {
        return "CleaningSchedule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", intervall='" + intervall + '\'' +
                ", roommate=" + roommate +
                '}';
    }
}
