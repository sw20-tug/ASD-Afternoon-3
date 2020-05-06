package at.tugraz.asdafternoon3.data;

import javax.persistence.*;

@Entity
public class ShoppingListItem extends DatabaseObject {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String item;

    @Column
    private Boolean completed;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Flat flat;

    public ShoppingListItem() {
        this.item = "";
        this.flat = null;
        this.completed = false;
    }

    public ShoppingListItem(String item, Flat flat) {
        this.item = item;
        this.flat = flat;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", flat=" + flat +
                '}';
    }
}

