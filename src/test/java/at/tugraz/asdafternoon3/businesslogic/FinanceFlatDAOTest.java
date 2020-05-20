package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.FinanceFlat;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FinanceFlatDAOTest extends DAOTest {

    @Test
    public void validateFinanceFlatValid() {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertTrue(creator.validate(financeFlat));
    }

    @Test
    public void validateFinanceFlatInvalidPrice() {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", -700, flat);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertFalse(creator.validate(financeFlat));
    }

    @Test
    public void validateFinanceFlatInvalidTitle() {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("", 1000, flat);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertFalse(creator.validate(financeFlat));
    }

    @Test
    public void validateFinanceFlatNoFlat() {
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 900, null);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertFalse(creator.validate(financeFlat));
    }

    @Test
    public void createFinanceFlatValid() throws Exception {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FlatDAO flatCreator = new FlatDAO(database);
        assertNotNull(flatCreator.create(flat));

        FinanceFlatDAO creator = new FinanceFlatDAO(database);
        assertNotNull(creator.create(financeFlat));
        assertNotEquals(0, financeFlat.getId());
    }

    @Test
    public void createFinanceFlatInvalid() {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FinanceFlatDAO creator = new FinanceFlatDAO(database);
        boolean wasInCatch = false;
        try {
            assertNotNull(creator.create(financeFlat));
        } catch (Exception e) {
            wasInCatch = true;
        }
        assertTrue(wasInCatch);
    }

    @Test
    public void updateFinanceFlat() throws Exception {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FlatDAO flatCreator = new FlatDAO(database);
        assertNotNull(flatCreator.create(flat));

        FinanceFlatDAO creator = new FinanceFlatDAO(database);
        assertNotNull(creator.create(financeFlat));
        assertNotEquals(0, financeFlat.getId());

        financeFlat.setPrice(71);

        assertNotNull(creator.update(financeFlat));
        assertEquals(71, financeFlat.getPrice());
    }

    @Test
    public void countFinanceFlat() throws Exception {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FlatDAO flatCreator = new FlatDAO(database);
        assertNotNull(flatCreator.create(flat));

        FinanceFlatDAO creator = new FinanceFlatDAO(database);

        Long fFlatCount = creator.count();
        assertNotNull(creator.create(financeFlat));
        assertNotEquals(0, financeFlat.getId());
        assertTrue(fFlatCount == creator.count() - 1);
    }

    @Test
    public void deleteFinanceFlat() throws Exception {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FlatDAO flatCreator = new FlatDAO(database);
        assertNotNull(flatCreator.create(flat));

        FinanceFlatDAO creator = new FinanceFlatDAO(database);
        assertNotNull(creator.create(financeFlat));
        assertNotEquals(0, financeFlat.getId());

        Long fFlatCount = creator.count();
        creator.delete(financeFlat);
        assertTrue(fFlatCount == creator.count() + 1);
    }

    @Test
    public void getAllFinanceFlat() throws Exception {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FlatDAO flatCreator = new FlatDAO(database);
        assertNotNull(flatCreator.create(flat));

        FinanceFlatDAO creator = new FinanceFlatDAO(database);

        List<FinanceFlat> fFlatList = creator.getAll();
        assertNotNull(creator.create(financeFlat));
        assertNotEquals(0, financeFlat.getId());
        assertTrue(fFlatList.size() == creator.getAll().size() - 1);
    }

    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }
}