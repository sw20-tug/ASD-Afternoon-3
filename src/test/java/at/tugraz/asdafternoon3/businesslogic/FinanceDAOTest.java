package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Finance;
import at.tugraz.asdafternoon3.data.Roommate;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class FinanceDAOTest extends DAOTest {

    @Test
    public void createFinanceValid() {
        Flat flat = generateTestFlat();
        Roommate ownerRoommate = generateTestRoommate(flat);
        Finance finance = new Finance("Sofa", 200, ownerRoommate, flat);

        FinanceDAO creator = new FinanceDAO(null);
        assertTrue(creator.validate(finance));
    }

    @Test
    public void createFinanceInvalidCosts() {
        Flat flat = generateTestFlat();
        Roommate ownerRoommate = generateTestRoommate(flat);
        Finance finance = new Finance("Sofa", -10, ownerRoommate, flat);

        FinanceDAO creator = new FinanceDAO(null);
        assertFalse(creator.validate(finance));
    }

    @Test
    public void createFinanceInvalidName() {
        Flat flat = generateTestFlat();
        Roommate ownerRoommate = generateTestRoommate(flat);
        Finance finance = new Finance("", 10, ownerRoommate, flat);

        FinanceDAO creator = new FinanceDAO(null);
        assertFalse(creator.validate(finance));
    }

    @Test
    public void createFinanceNoCost() {
        Flat flat = generateTestFlat();
        Roommate ownerRoommate = generateTestRoommate(flat);
        Finance finance = new Finance("Sofa", 0, ownerRoommate, flat);

        FinanceDAO creator = new FinanceDAO(null);
        assertFalse(creator.validate(finance));
    }

    @Test
    public void createFinanceNoFlat() {
        Roommate ownerRoommate = generateTestRoommate(null);
        Finance finance = new Finance("Sofa", 300, ownerRoommate, null);

        FinanceDAO creator = new FinanceDAO(null);
        assertFalse(creator.validate(finance));
    }

    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }

    private Roommate generateTestRoommate(Flat flat) {

        return new Roommate("Andi Goldberger", 20, flat);
    }
}