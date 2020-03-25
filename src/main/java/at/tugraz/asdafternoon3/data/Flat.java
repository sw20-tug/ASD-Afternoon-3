package at.tugraz.asdafternoon3.data;

public class Flat {
    private String name;
    private int size;
    private String address;
    private int id;

    public Flat(String name, int size, String address) {
        this.name = name;
        this.size = size;
        this.address = address;
        this.id = 0;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getAddress() {
        return address;
    }

    public int getId() {
        return id;
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
