package view.tableModels;

import java.util.Collection;
import java.util.Collections;
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

	
	private static final List<Column<Reviewer, ?>> createColumns(Model model) {
		return List.of(Column.of("Name", reviewer -> reviewer.getName(), String.class),
				Column.of("Anzahl gemeinsamer Bachelorarbeiten", reviewer -> model.getCollaboratingReviewers().get(reviewer).intValue(), Integer.class));
	}
	
	public CollaboratingReviewerTableModel(List<Column<Reviewer, ?>> columns, List<Predicate<Reviewer>> filters, Model model) {
		super(columns, filters);
		this.model = model;
	}
	
	public CollaboratingReviewerTableModel(Model model) {
		this(createColumns(model), Collections.emptyList(), model);
	}

	@Override
	protected Collection<Reviewer> getUnfilteredData() {
		return this.model.getCollaboratingReviewers().keySet();
	}

}
