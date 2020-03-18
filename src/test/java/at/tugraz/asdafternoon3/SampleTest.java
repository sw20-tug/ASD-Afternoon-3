package at.tugraz.asdafternoon3;

import org.junit.Test;

import static org.junit.Assert.*;

public class SampleTest {

    @Test
    public void doStuff() {
        Sample sample = new Sample("1234");
        assertEquals(sample.doStuff(), 4);
    }
}