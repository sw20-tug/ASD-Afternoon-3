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

    @Test
    public void createFlatInvalidSize() {
        Flat flat = new Flat("Chaos WG", 0, "Graz");

        FlatCreator creator = new FlatCreator();
        assertFalse(creator.validateFlat(flat));
    }

    @Test
    public void createFlatInvalidName() {
        Flat flat = new Flat("", 2, "Graz");

        FlatCreator creator = new FlatCreator();
        assertFalse(creator.validateFlat(flat));
    }

    @Test
    public void createFlatInvalidAddress() {
        Flat flat = new Flat("Chaos WG", 2, "");

        FlatCreator creator = new FlatCreator();
        assertFalse(creator.validateFlat(flat));
    }

    @Test
    public void createFlatNameWithInvalidCharacters() {
        Flat flat = new Flat("Chaos WG????", 2, "Graz");

        FlatCreator creator = new FlatCreator();
        assertFalse(creator.validateFlat(flat));
    }

    @Test
    public void setFlatAsCurrentFlat() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        FlatCreator creator = new FlatCreator();
        flat.setIsCurrent(true);
        flat = creator.updateFlat(flat);
        assertTrue(flat.isCurrent());
    }

}