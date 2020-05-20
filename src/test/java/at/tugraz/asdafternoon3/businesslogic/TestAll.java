package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.Flat;
import at.tugraz.asdafternoon3.data.Finance;
import at.tugraz.asdafternoon3.data.Roommate;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class TestAll extends DAOTest {

    @Test
    public void testAll() throws Exception {

        // FLAT DAO TEST
        Flat flat = generateTestFlatFalse();
        FlatDAO dao = new FlatDAO(database);
        // Testing creating Flat
        dao.create(flat);
        assertFalse(dao.validate(flat));

        //// Testing updating Flat
        dao.update(flat);
        assertFalse(dao.validate(flat));


        // Testing get Roommates
        flat = generateTestFlat();
        dao = new FlatDAO(database);
        dao.create(flat);
        Roommate rm1 = generateTestRoommate(flat);
        RoommateDAO rDao = new RoommateDAO(database);

        rDao.create(rm1);
        List<Roommate> roommateList = dao.getRoommates(flat);
        Roommate rm2 = roommateList.get(0);
        assert (rm1.getName().equals(rm2.getName()));
        assert (rm1.getId() == rm2.getId());
        assert (rm1.getAge() == rm2.getAge());
        assert (rm1.getFlat().getName().equals(rm2.getFlat().getName()));

        // Testing get Finance
        flat = generateTestFlat();
        dao = new FlatDAO(database);
        dao.create(flat);
        Finance finance = new Finance("Sofa", 10, rm1, flat);
        FinanceDAO fDao = new FinanceDAO(database);

        fDao.create(finance);
        List<Finance> financeList = dao.getFinance(flat);
        Finance finance2 = financeList.get(0);
        assert (finance.getName().equals(finance2.getName()));
        assert (finance.getCosts() == finance2.getCosts());
        assert (finance.getId() == finance2.getId());
        assert (finance.getFlat().getName().equals(finance2.getFlat().getName()));


        // ROOMMATE DAO TEST

        Roommate roommate = generateTestRoommateFalse(flat);
        rDao = new RoommateDAO(database);

        // Testing creating invalid roommate
        rDao.create(roommate);
        assertFalse(rDao.validate(roommate));

        // testing update when invalid roommate
        rDao.update(roommate);
        assertFalse(rDao.validate(roommate));

        // testing creating valid roommate and update
        roommate = generateTestRoommate(flat);
        rDao = new RoommateDAO(database);
        rDao.create(roommate);
        rDao.update(roommate);
        assert(rDao.validate(roommate));

        roommateList = rDao.getAll();
        int size1 = roommateList.size();
        rDao.delete(roommate);
        roommateList = rDao.getAll();
        int size2 = roommateList.size();
        assert(size1 == (size2 + 1));


        financeList = fDao.getAll();
        size1 = financeList.size();
        fDao.delete(finance);
        financeList = fDao.getAll();
        size2 = financeList.size();
        assert(size1 == (size2 + 1));

        // testing creating valid finance and update
        flat = generateTestFlat();
        dao = new FlatDAO(database);
        dao.create(flat);
        finance = new Finance("Sofa", 10, rm1, flat);
        fDao = new FinanceDAO(database);

        fDao.create(finance);
        assert(fDao.validate(finance));

        fDao.update(finance);
        assert (fDao.validate(finance));


        finance = new Finance("", 10, rm1, flat);
        fDao.create(finance);
        assertFalse(fDao.validate(finance));

        fDao.update(finance);
        assertFalse(fDao.validate(finance));

    }




    private Flat generateTestFlat() {
        return new Flat("Chaos WG", 2, "Graz");
    }


    private Flat generateTestFlatFalse() {
        return new Flat("", 2, "Graz");
    }

    private Roommate generateTestRoommate(Flat flat) {

        return new Roommate("Andi Goldberger", 20, flat);
    }

    private Roommate generateTestRoommateFalse(Flat flat) {

        return new Roommate("AndiÄ", 20, flat);
    }
}