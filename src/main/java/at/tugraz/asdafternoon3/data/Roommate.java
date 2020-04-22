package at.tugraz.asdafternoon3.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "roommates")
public class Roommate extends DatabaseObject {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField()
    private String name;

    @DatabaseField(canBeNull = false, foreign = true)
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

