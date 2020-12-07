package view.tableModels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.domain.Reviewer;

public class CollaborationTableModel extends AbstractDataTableModel<Reviewer> {

	private static final long serialVersionUID = 1L;
	private Model model;
	
	private static final Column<Reviewer, String> NAME = Column.of("Name", Reviewer::getName, String.class);
//	private final Column<Reviewer, Integer> THESIS_COUNT = Column.of("Anzahl gemeinsamer Bachelorarbeiten",
//			action, columnClass) TODO resolve count from HashMap or find good solution
	
	public CollaborationTableModel(List<Predicate<Reviewer>> filters, Model model) {
		super(List.of(NAME), filters);
		this.model = model;
	}

	@Override
	protected Collection<Reviewer> getUnfilteredData() {
		ArrayList<Reviewer> result = new ArrayList<>();
		this.model.getCollaboratingReviewers().forEach((a, b) -> result.add(a));
		return result;
	}
}
