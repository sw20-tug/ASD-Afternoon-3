package at.tugraz.asdafternoon3.data;

import javax.persistence.*;

@Entity
public class Finance extends DatabaseObject {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private int costs;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Roommate ownerRoommate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Flat flat;

    public Finance(String name, int costs, Roommate roommate, Flat flat) {
        this.name = name;
        this.costs = costs;
        this.ownerRoommate = roommate;
        this.flat = flat;
    }

    public Finance(){
        this.name = "";
        this.costs = 0;
        this.ownerRoommate = null;
        this.flat = null;
    }

    public int getCosts() {
        return costs;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Roommate getOwnerRoommate() {
        return ownerRoommate;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public void setOwnerRoommate(Roommate ownerRoommate) {
        this.ownerRoommate = ownerRoommate;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }
}

