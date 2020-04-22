package at.tugraz.asdafternoon3.businesslogic;

import at.tugraz.asdafternoon3.data.DatabaseObject;

import java.sql.SQLException;

public abstract class Creator<T extends DatabaseObject> {
    public abstract boolean validate(T object);
    public abstract T create(T object) throws Exception;
}