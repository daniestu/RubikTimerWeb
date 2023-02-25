package dao.contracts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Persistencia<T> {
	public T add(T modelo) throws IOException, SQLException;
	public List<T> getAll() throws IOException, SQLException;
}
