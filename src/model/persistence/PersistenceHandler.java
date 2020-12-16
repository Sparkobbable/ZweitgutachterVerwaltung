package model.persistence;

import java.util.List;

import model.Pair;
import model.domain.BachelorThesis;
import model.domain.Reviewer;

/**
 * Handler for saving and loading data
 * Features lists of {@link Reviewer} and {@link BachelorThesis}
 */
public interface PersistenceHandler {

	public void save(List<Reviewer> reviewers, List<BachelorThesis> theses);
	public Pair<List<Reviewer>, List<BachelorThesis>> load();
}
