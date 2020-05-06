package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Finance;
import at.tugraz.asdafternoon3.data.Roommate;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FinanceDAOTest {

    @Test
    public void createFinanceValid() {
        Flat flat = generateTestFlat();
        Roommate ownerRoomate = generateTestRoommate(flat);
        Finance finance = new Finance("Sofa", 200, ownerRoomate, flat);

        FinanceDAO creator = new FinanceDAO(null);
        assertTrue(creator.validate(finance));
    }

    @Test
    public void createFinanceInvalidCosts() {
        Flat flat = generateTestFlat();
        Roommate roommate = generateTestRoommate(flat);
        Finance finance = new Finance("Sofa", -10, roommate, flat);

        FinanceDAO creator = new FinanceDAO(null);
        assertFalse(creator.validate(finance));
    }

    @Test
    public void createFinanceNoFlat() {
        Roommate roommate = generateTestRoommate(null);
        Finance finance = new Finance("Sofa", 300, roommate, null);

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