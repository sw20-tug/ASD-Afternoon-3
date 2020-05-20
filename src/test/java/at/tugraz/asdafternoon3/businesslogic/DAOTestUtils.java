package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.TestUtils;
import at.tugraz.asdafternoon3.data.DatabaseObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class DAOTestUtils {

    public static<T extends DatabaseObject> void testCreate(Class<? extends DAO<T>> type, T test) throws Exception {
        SessionFactory databaseMock = TestUtils.mockDatabase();
        Session sessionMock = TestUtils.mockSession(databaseMock);

        DAO<T> creator = type.getDeclaredConstructor(SessionFactory.class).newInstance(databaseMock);
        assertNotNull(creator.create(test));
        Mockito.verify(sessionMock).persist(test);

        // Check if it does not create if validate does not work
        DAO<T> spyCreator = Mockito.spy(creator);
        Mockito.when(spyCreator.validate(test)).thenReturn(false);
        assertNull(spyCreator.create(test));
    }

    public static<T extends DatabaseObject> void testUpdate(Class<? extends DAO<T>> type, T test) throws Exception {
        SessionFactory databaseMock = TestUtils.mockDatabase();
        Session sessionMock = TestUtils.mockSession(databaseMock);

        DAO<T> creator = type.getDeclaredConstructor(SessionFactory.class).newInstance(databaseMock);
        assertNotNull(creator.update(test));
        Mockito.verify(sessionMock).merge(test);
    }

    public static<T extends DatabaseObject> void testDelete(Class<? extends DAO<T>> type, T test) throws Exception {
        SessionFactory databaseMock = TestUtils.mockDatabase();
        Session sessionMock = TestUtils.mockSession(databaseMock);

        DAO<T> creator = type.getDeclaredConstructor(SessionFactory.class).newInstance(databaseMock);
        creator.delete(test);
        Mockito.verify(sessionMock).delete(test);
    }
}
