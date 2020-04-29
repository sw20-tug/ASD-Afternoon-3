package at.tugraz.asdafternoon3.data;

import at.tugraz.asdafternoon3.businesslogic.FlatDAO;
import at.tugraz.asdafternoon3.database.DatabaseConnection;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Flats
{
    FlatDAO creator_;
    List<Flat> flats_ = new ArrayList<>();
    public Flats(SessionFactory sessionfactory) throws Exception
    {
        creator_ = new FlatDAO(sessionfactory);
        loadFlatsFromDatabase();
    }

    public void loadFlatsFromDatabase() throws Exception
    {
        flats_ = creator_.getAll();
    }

    public void updateFlat(Flat flat) throws Exception
    {
        int flat_to_update = -1;
        int counter = 0;

        for (Flat flat1 : flats_)
        {
            if (flat1.equals(flat))
            {
                throw new Exception("Update not possible: Flat with same information already exists");
            }
            if (flat1.getId() == flat.getId())
            {
                flat_to_update = counter;
            }
            counter++;
        }

        if (flat_to_update == -1)
        {
            throw new Exception("Cannot find flat with this ID");
        }

        flats_.set(flat_to_update, flat);
        creator_.update(flat);
    }

    public Flat getFlatByID(int id) throws Exception
    {
        for (Flat flat1 : flats_)
        {
            if (flat1.getId() == id)
            {
                return flat1;
            }
        }
        throw new Exception("Could not find flat with given ID");
    }

    public void addFlat(Flat flat) throws Exception
    {
        creator_.validate(flat);

        for (Flat flat1 : flats_)
        {
            if (flat1.equals(flat))
            {
                throw new Exception("Update not possible: Flat with same information already exists");
            }
        }

        flat = creator_.create(flat);
        flats_.add(new Flat(flat));
    }

    public void removeFlat(int id) throws Exception
    {
        int counter = 0;
        for (Flat flat1 : flats_)
        {
            if (flat1.getId() == id)
            {
                //delete(flat1);
                flats_.remove(counter);
                return;
            }
            counter++;
        }

        throw new Exception("Could not find flat to remove");
    }

}
