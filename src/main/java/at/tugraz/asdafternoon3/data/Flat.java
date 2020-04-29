package at.tugraz.asdafternoon3.data;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Flat extends DatabaseObject {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private Integer size;

    @Column
    private String address;

    @OneToMany(mappedBy = "flat")
    private Set<Roommate> roommates;

    @Column
    private boolean isCurrent;

    public Flat() {
        this.name = "";
        this.size = 0;
        this.address = "";
        this.id = 0;
        this.isCurrent = false;
    }

    public Flat(String name, int size, String address) {
        this.name = name;
        this.size = size;
        this.address = address;
        this.isCurrent = false;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean state) {
        isCurrent = state;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
    }

    public Set<Roommate> getRoommates() {
        return roommates;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", address='" + address + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Flat flat = (Flat)obj;
        if(flat.id == this.id){
            return true;
        }
        else{
            return false;
        }

    }
}
