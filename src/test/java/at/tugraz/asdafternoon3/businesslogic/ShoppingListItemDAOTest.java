package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Roommate;
import at.tugraz.asdafternoon3.data.ShoppingListItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ShoppingListItemDAOTest extends DAOTest {

    @Test
    public void createShoppingListItemValid() {
        Flat flat = generateTestFlat();
        ShoppingListItem item = new ShoppingListItem("Milch", flat);

        ShoppingListItemDAO dao = new ShoppingListItemDAO(null);
        assertTrue(dao.validate(item));
    }

    @Test
    public void createShoppingListItemInvalid() {
        Flat flat = generateTestFlat();
        ShoppingListItem item = new ShoppingListItem("", flat);

        ShoppingListItemDAO dao = new ShoppingListItemDAO(null);
        assertFalse(dao.validate(item));
    }

    @Test
    public void createShoppingListToFlat() {
        Flat flat = generateTestFlat();
        ShoppingListItem item = new ShoppingListItem("Milch", flat);

        try {
            new FlatDAO(database).create(flat);
            new ShoppingListItemDAO(database).create(item);

            List<ShoppingListItem> list = new FlatDAO(database).getShoppingList(flat);
            assertEquals("Milch", list.get(0).getItem());

        } catch (Exception e) {
            e.printStackTrace();
            assert(false);
        }
    }

    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }
}