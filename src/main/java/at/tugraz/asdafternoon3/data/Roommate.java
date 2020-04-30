package at.tugraz.asdafternoon3.data;

import javax.persistence.*;

@Entity
public class Roommate extends DatabaseObject {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private Integer age;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Flat flat;

    public Roommate() {
        this.name = "";
        this.id = 0;
        this.age = 0;
        this.flat = null;
    }

    public Roommate(String name, Integer age, Flat flat) {
        this.name = name;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
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

