package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningSchedule;
import org.hibernate.SessionFactory;

import java.util.List;

public class CleaningScheduleDAO extends DAO<CleaningSchedule> {
    protected CleaningScheduleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(CleaningSchedule object) {
        return false;
    }

    @Override
    public CleaningSchedule create(CleaningSchedule object) throws Exception {
        return null;
    }

    @Override
    public CleaningSchedule update(CleaningSchedule object) throws Exception {
        return null;
    }

    @Override
    public List<CleaningSchedule> getAll() throws Exception {
        return null;
    }

    @Override
    public Long count() throws Exception {
        return null;
    }

    @Override
    public void delete(CleaningSchedule object) throws Exception {

    }
}
