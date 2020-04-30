package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.TestUtils;
import at.tugraz.asdafternoon3.data.Flat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class FlatDAOTest extends DAOTest {

    @Test
    public void createFlatValid() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        FlatDAO creator = new FlatDAO(null);
        assertTrue(creator.validate(flat));
    }

    @Test
    public void createFlatInvalidSize() {
        Flat flat = new Flat("Chaos WG", 0, "Graz");

        FlatDAO creator = new FlatDAO(null);
        assertFalse(creator.validate(flat));
    }

    @Test
    public void createFlatInvalidName() {
        Flat flat = new Flat("", 2, "Graz");

        FlatDAO creator = new FlatDAO(null);
        assertFalse(creator.validate(flat));
    }

    @Test
    public void createFlatInvalidAddress() {
        Flat flat = new Flat("Chaos WG", 2, "");

        FlatDAO creator = new FlatDAO(null);
        assertFalse(creator.validate(flat));
    }

    @Test
    public void createFlatNameWithInvalidCharacters() {
        Flat flat = new Flat("Chaos WG????", 2, "Graz");

        FlatDAO creator = new FlatDAO(null);
        assertFalse(creator.validate(flat));
    }

    @Test
    public void createFlatMock() throws Exception {
        Flat flat = new Flat("Chaos WG", 2, "Graz");

        SessionFactory databaseMock = TestUtils.mockDatabase();
        Session sessionMock = TestUtils.mockSession(databaseMock);

        FlatDAO creator = new FlatDAO(databaseMock);
        assertNotNull(creator.create(flat));
        Mockito.verify(sessionMock).persist(flat);
    }

    @Test
    public void setFlatAsCurrentFlat() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        FlatDAO creator = new FlatDAO(database);
        try {
            creator.create(flat);

            flat.setIsCurrent(true);

            flat = creator.update(flat);
            assertTrue(flat.isCurrent());
            flat.setIsCurrent(false);

            flat = creator.update(flat);
            assertFalse(flat.isCurrent());
            //TODO:Delete flat
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void getCurrentFlat() {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flat current_flat;

        FlatDAO creator = new FlatDAO(database);

        try {
            creator.create(flat);
            current_flat = creator.getCurrentFlat();
            assertNull(current_flat);

            flat.setIsCurrent(true);
            creator.update(flat);
            current_flat = creator.getCurrentFlat();
            assertTrue(current_flat != null && current_flat.isCurrent());
            //TODO:Delete flat
        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }

    @Test
    public void deleteFlat() {
        Flat flat = new Flat("Test", 3, "Leibnitz");

        FlatDAO creator = new FlatDAO(database);

        try {
            creator.create(flat);

            creator.delete(flat);
            boolean value = creator.getAll().contains(flat);

            assertFalse(value);

        } catch (Exception e) {
            e.printStackTrace();
            assert (false);
        }
    }


}