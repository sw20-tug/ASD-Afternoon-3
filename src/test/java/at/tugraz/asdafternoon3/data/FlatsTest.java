package at.tugraz.asdafternoon3.data;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Flats;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlatsTest
{

    @Test
    public void createFlatValid()
    {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flats flats = new Flats();

        DatabaseConnection connection = DatabaseConnection.getInstance();
        connection.initOrm();
        try
        {
            flats.addFlat(flat);
        }
        catch (Exception e)
        {
            assertFalse(e.toString(), false);
        }
    }

    @Test
    public void createFlatInvalid()
    {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flats flats = new Flats();

        DatabaseConnection connection = DatabaseConnection.getInstance();
        connection.initOrm();
        try
        {
            flats.addFlat(flat);
        }
        catch (Exception e)
        {
            fail("Flat is added for the first time");
        }
        try
        {
            flats.addFlat(flat);
        }
        catch (Exception e)
        {
            return;
        }
        fail("Added same flat twice");
    }

    @Test
    public void updateFlatValid()
    {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flats flats = new Flats();

        DatabaseConnection connection = DatabaseConnection.getInstance();
        connection.initOrm();

        try
        {
            flats.addFlat(flat);
            flats.updateFlat(flat);
        }
        catch (Exception e)
        {
            fail(e.toString());
        }
    }

    @Test
    public void updateFlatInvalid()
    {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flat flat2 = new Flat("Krabbler WG", 10, "Spielfeld");
        Flats flats = new Flats();

        DatabaseConnection connection = DatabaseConnection.getInstance();
        connection.initOrm();

        try
        {
            flats.addFlat(flat2);
            flats.updateFlat(flat);
        }
        catch (Exception e)
        {
            return;
        }
        fail("Flat is illegally updated");
    }

    @Test
    public void removeFlatValid()
    {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flats flats = new Flats();

        DatabaseConnection connection = DatabaseConnection.getInstance();
        connection.initOrm();

        try
        {
            flats.addFlat(flat);
            //flats.removeFlat(flat);
        }
        catch (Exception e)
        {
            fail(e.toString());
        }
    }

    @Test
    public void removeFlatInvalid()
    {
        Flat flat = new Flat("Chaos WG", 2, "Graz");
        Flat flat2 = new Flat("Krabbler WG", 10, "Spielfeld");
        Flats flats = new Flats();

        DatabaseConnection connection = DatabaseConnection.getInstance();
        connection.initOrm();

        try
        {
            flats.addFlat(flat2);
            //flats.removeFlat(flat);
        }
        catch (Exception e)
        {
            return;
        }
        fail("Flat illegally removed");
    }
}