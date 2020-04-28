package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoommateDAOTest {

    @Test
    public void createRoommateValid() {
        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("Liki Norber", flat);

        RoommateDAO creator = new RoommateDAO();
        assertTrue(creator.validate(roommate));
    }

    @Test
    public void createRoommateInvalid() {
        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("icecreamforcrow", flat);

        RoommateDAO creator = new RoommateDAO();
        assertFalse(creator.validate(roommate));
    }

    @Test
    public void createRoommateInvalidCharacters() {
        Flat flat = generateTestFlat();
        Roommate roommate = new Roommate("blueöystercult", flat);

        RoommateDAO creator = new RoommateDAO();
        assertFalse(creator.validate(roommate));
    }

    @Test
    public void createRoommateNoFlat() {
        Roommate roommate = new Roommate("Andi Goldberger", null);

        RoommateDAO creator = new RoommateDAO();
        assertFalse(creator.validate(roommate));
    }

    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }
}