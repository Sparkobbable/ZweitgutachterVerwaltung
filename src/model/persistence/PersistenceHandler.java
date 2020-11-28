package model.persistence;

public interface PersistenceHandler {

	public void save();
	public void load() throws Exception;
}
