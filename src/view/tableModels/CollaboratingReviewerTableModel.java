package view.tableModels;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.domain.Reviewer;
import view.panels.collaboration.CollaborationPanel;

/**
 * TableModel for the {@link CollaborationPanel}}
 */
public class CollaboratingReviewerTableModel extends AbstractDataTableModel<Reviewer> {

	private static final long serialVersionUID = 1L;

	private Model model;
	
	private static final Column<Reviewer, String> NAME = Column.of("Name", Reviewer::getName, String.class);
//	private final Column<Reviewer, Integer> COLLABORATION_COUNT = Column.of("Anzahl gemeinsamer Bachelorarbeiten",
//			r -> r.getReviewsWithselectedReviewerCount(this.model.getSelectedReviewer()), Integer.class);
	//TODO find a solution for the problem, that the column needs to be static, but can not access the model being static
	public CollaboratingReviewerTableModel(List<Column<Reviewer, ?>> columns, List<Predicate<Reviewer>> filters) {
		super(List.of(NAME), filters);
	}

	@Override
	protected Collection<Reviewer> getUnfilteredData() {
		return this.model.getReviewers();
	}

}
