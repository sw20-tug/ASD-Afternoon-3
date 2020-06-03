package at.tugraz.asdafternoon3.data;

import javax.persistence.*;

@Entity
public class FinanceFlat extends DatabaseObject {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String title;

    @Column
    private int price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Flat flat;


    public FinanceFlat() {
        this.title = "";
        this.price = 0;
        this.flat = null;
    }

    public FinanceFlat(String title, int price, Flat flat) {
        this.title = title;
        this.price = price;
        this.flat = flat;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public Flat getFlat() {
        return flat;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    @Override
    public String toString() {
        return "FinanceFlat{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", flat=" + flat +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        FinanceFlat fFlat = (FinanceFlat) obj;
        if (title.equals(fFlat.title) && price == fFlat.price && flat.getName().equals(fFlat.flat.getName())) {
            return true;
        }
        return false;
    }
}

