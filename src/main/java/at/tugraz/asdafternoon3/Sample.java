package at.tugraz.asdafternoon3;

public class Sample {

    private String test;

    public Sample(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    public int doStuff() {
        return test.length();
    }
}
