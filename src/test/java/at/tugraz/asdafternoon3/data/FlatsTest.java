package at.tugraz.asdafternoon3.data;

import at.tugraz.asdafternoon3.TestUtils;
import org.hibernate.SessionFactory;
import org.junit.*;

import java.lang.invoke.MethodHandles;

import static org.junit.Assert.*;

public class FlatsTest {

    protected SessionFactory database;

    @Before
    public void setUp() throws Exception {
        database = TestUtils.createTestDatabase(MethodHandles.lookup().lookupClass().getName());
    }

    @After
    public void tearDown() throws Exception {
        TestUtils.closeTestDatabase(MethodHandles.lookup().lookupClass().getName());
        database = null;
    }

    @Test
    public void createFlatValid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.addFlat(flat);
        } catch (Exception e) {
            assertFalse(e.toString(), false);
        }
    }

    @Test
    public void createFlatInvalid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.addFlat(flat);
        } catch (Exception e) {
            fail("Flat is added for the first time");
        }
        try {
            flats.addFlat(flat);
        } catch (Exception e) {
            return;
        }
        fail("Added same flat twice");
    }

    @Test
    public void updateFlatValid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.addFlat(flat);
            flat.setAddress("Spielfeld");
            flats.updateFlat(flat);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void updateFlatInvalid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flat flat2 = new Flat("Krabbler WG", 10, "Spielfeld");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.addFlat(flat2);
            flats.updateFlat(flat);
        } catch (Exception e) {
            return;
        }
        fail("Flat is illegally updated");
    }

    @Test
    public void removeFlatValid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.addFlat(flat);
            flats.removeFlat(flat.getId());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void removeFlatInvalid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flat flat2 = new Flat("Krabbler WG", 10, "Spielfeld");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.addFlat(flat);
            flats.removeFlat(flat2.getId());
        } catch (Exception e) {
            return;
        }
        fail("Flat illegally removed");
    }

    @Test
    public void getFlatByIDValid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.addFlat(flat);
            flats.getFlatByID(flat.getId());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void getFlatByIDInvalid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        Flats flats = null;
        try {
            flats = new Flats(database);
        } catch (Exception e) {
            fail(e.toString());
        }

        try {
            flats.getFlatByID(99);
        } catch (Exception e) {
            return;
        }
        fail("Not existing flat found");
    }
}