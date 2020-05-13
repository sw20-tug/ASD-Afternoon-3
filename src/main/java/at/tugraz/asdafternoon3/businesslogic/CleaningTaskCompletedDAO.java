package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.CleaningTaskCompleted;
import org.hibernate.SessionFactory;

import java.util.List;

public class CleaningTaskCompletedDAO extends DAO<CleaningTaskCompleted> {

    public CleaningTaskCompletedDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean validate(CleaningTaskCompleted object) {
        if (object.getCleaningSchedule() == null)
            return false;
        if(object.getCompleted() == null)
            return false;
        return true;
    }

    @Override
    public CleaningTaskCompleted create(CleaningTaskCompleted object) throws Exception {
        return null;
    }

    @Override
    public CleaningTaskCompleted update(CleaningTaskCompleted object) throws Exception {
        return null;
    }

    @Override
    public List<CleaningTaskCompleted> getAll() throws Exception {
        return null;
    }

    @Override
    public Long count() throws Exception {
        return null;
    }

    @Override
    public void delete(CleaningTaskCompleted object) throws Exception {

    }
}
