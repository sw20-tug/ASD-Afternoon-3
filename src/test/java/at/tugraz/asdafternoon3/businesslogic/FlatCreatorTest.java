package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import org.junit.Test;
import static org.junit.Assert.*;

public class FlatCreatorTest {

    @Test
    public void createFlatValid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        FlatCreator creator = new FlatCreator();
        assertTrue(creator.validateFlat(flat));

        // TODO: Mocking
        // creator.createFlat(flat);
    }
}