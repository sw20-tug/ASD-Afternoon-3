package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.TestUtils;
import at.tugraz.asdafternoon3.data.Finance;
import at.tugraz.asdafternoon3.data.FinanceFlat;
import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

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
    public void mockTests() throws Exception {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        DAOTestUtils.testCreate(FlatDAO.class, flat);
        DAOTestUtils.testUpdate(FlatDAO.class, flat);
        DAOTestUtils.testDelete(FlatDAO.class, flat);
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

    @Test
    public void getFinanceTest() throws Exception {
        Flat flat = generateTestFlat();
        FlatDAO fDao = new FlatDAO(database);
        assertNotNull(fDao.create(flat));

        FinanceFlat financeFlat = new FinanceFlat("Test", 100, flat);
        FinanceFlatDAO creator = new FinanceFlatDAO(database);
        assertNotNull(creator.create(financeFlat));
        List<FinanceFlat> fList = fDao.getFinanceFlat(flat);
        assertEquals(1, fList.size());
        assertEquals("Test", fList.get(0).getTitle());
        assertEquals(100, fList.get(0).getPrice());
        assertEquals(financeFlat.getFlat().getId(), fList.get(0).getFlat().getId());
        assertEquals(financeFlat.getId(), fList.get(0).getId());
    }

    @Test
    public void countTest() throws Exception {
        Flat flat = generateTestFlat();
        FlatDAO fDao = new FlatDAO(database);
        assertNotNull(fDao.create(flat));
        assertEquals(1, (long) fDao.count());
    }

    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }


}