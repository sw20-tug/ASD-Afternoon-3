package at.tugraz.asdafternoon3.data;

import javax.persistence.*;

@Entity
public class Roommate extends DatabaseObject {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Flat flat;

    public Roommate() {
        this.name = "";
        this.id = 0;
        this.flat = null;
    }

    public Roommate(String name, Flat flat) {
        this.name = name;
        this.flat = flat;
    }

    public String getName() {
        return name;
    }

    public Flat getFlat() {
        return flat;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Roommate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flat=" + flat +
                '}';
    }
}

