package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlatDAOTest {

    @Test
    public void createFlatValid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        FlatDAO creator = new FlatDAO();
        assertTrue(creator.validate(flat));

        // TODO: Mocking
        // creator.createFlat(flat);
    }

    @Test
    public void createFlatInvalidSize() {
        Flat flat = new Flat("Chaos WG", 0, "Graz");

        FlatDAO creator = new FlatDAO();
        assertFalse(creator.validate(flat));
    }

    @Test
    public void createFlatInvalidName() {
        Flat flat = new Flat("", 2, "Graz");

        FlatDAO creator = new FlatDAO();
        assertFalse(creator.validate(flat));
    }

    @Test
    public void createFlatInvalidAddress() {
        Flat flat = new Flat("Chaos WG", 2, "");

        FlatDAO creator = new FlatDAO();
        assertFalse(creator.validate(flat));
    }

    @Test
    public void createFlatNameWithInvalidCharacters() {
        Flat flat = new Flat("Chaos WG????", 2, "Graz");

        FlatDAO creator = new FlatDAO();
        assertFalse(creator.validate(flat));
    }

    // TODO: Make tests compatible with CI
    /*
    @Test
    public void setFlatAsCurrentFlat() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        FlatCreator creator = new FlatCreator();
        try {
            creator.create(flat);

            flat.setIsCurrent(true);

            flat = creator.updateFlat(flat);
            assertTrue(flat.isCurrent());
            flat.setIsCurrent(false);

            flat = creator.updateFlat(flat);
            assertFalse(flat.isCurrent());
            //TODO:Delete flat
        }
        catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
    @Test
    public void getCurrentFlat() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flat current_flat;

        FlatCreator creator = new FlatCreator();

        try {
            creator.create(flat);
            current_flat = creator.getCurrentFlat();
            assertNull(current_flat );

            flat.setIsCurrent(true);
            creator.updateFlat(flat);
            current_flat = creator.getCurrentFlat();
            assertTrue(current_flat != null && current_flat.isCurrent());
            //TODO:Delete flat
        }
        catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }*/

}