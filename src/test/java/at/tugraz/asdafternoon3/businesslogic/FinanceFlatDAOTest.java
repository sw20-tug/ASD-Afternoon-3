package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.FinanceFlat;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FinanceFlatDAOTest extends DAOTest {

    @Test
    public void createFinanceFlatValid() {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 800, flat);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertTrue(creator.validate(financeFlat));
    }

    @Test
    public void createFinanceFlatInvalidPrice() {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", -700, flat);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertFalse(creator.validate(financeFlat));
    }

    @Test
    public void createFinanceFlatInvalidTitle() {
        Flat flat = generateTestFlat();
        FinanceFlat financeFlat = new FinanceFlat("", 1000, flat);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertFalse(creator.validate(financeFlat));
    }

    @Test
    public void createFinanceFlatNoFlat() {
        FinanceFlat financeFlat = new FinanceFlat("Rental fee", 900, null);

        FinanceFlatDAO creator = new FinanceFlatDAO(null);
        assertFalse(creator.validate(financeFlat));
    }

    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }
}