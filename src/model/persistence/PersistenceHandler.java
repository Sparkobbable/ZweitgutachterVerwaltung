package model.persistence;

import java.util.List;

import com.sun.tools.javac.util.Pair;

import model.domain.BachelorThesis;
import model.domain.Reviewer;

public interface PersistenceHandler {

	public void save(List<Reviewer> reviewers, List<BachelorThesis> theses);
	public Pair<List<Reviewer>, List<BachelorThesis>> load();
}
