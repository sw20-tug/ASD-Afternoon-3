package at.tugraz.asdafternoon3.data;

import javax.persistence.*;
import java.util.Objects;
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

    public Flat(Flat flat)
    {
        this.id = flat.getId();
        this.name = flat.getName();
        this.size = flat.getSize();
        this.address = flat.getAddress();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return size == flat.size &&
                isCurrent == flat.isCurrent &&
                Objects.equals(name, flat.name) &&
                Objects.equals(address, flat.address);
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
}
