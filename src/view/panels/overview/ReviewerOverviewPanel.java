package view.panels.overview;

import static view.tableModels.ReviewerOverviewTableModel.NAME;
import static view.tableModels.ReviewerOverviewTableModel.OCCUPATION;
import static view.tableModels.ReviewerOverviewTableModel.THESIS_COUNT;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.TableRowSorter;

import controller.events.EventSource;
import controller.search.ReviewerSearchStrategy;
import controller.search.SearchStrategy;
import model.Model;
import model.domain.Reviewer;
import model.enums.EventId;
import view.eventsources.TableClickEventSource;
import view.panels.editor.ReviewerEditorPanel;
import view.panels.editor.ThesesAssignmentPanel;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.AbstractDataTableModel;
import view.tableModels.DividedProgressRenderer;
import view.tableModels.OccupationComparator;
import view.tableModels.ReviewerOverviewTableModel;

/**
 * {@link OverviewPanel} that is responsible for displaying an overview of a list of {@link Reviewer}.
 * <p>
 * It is responsible for searching reviewers as well as linking to the
 * {@link ReviewerEditorPanel}
 */
public class ReviewerOverviewPanel extends OverviewPanel<Reviewer> {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a view containing a table presenting the list of {@link Reviewer} and buttons for
	 * interacting with the data
	 * 
	 * @param model Needs the model as the data access
	 */
	public ReviewerOverviewPanel(Model model) {
		super(model, "Dozentenübersicht");
		this.actionPanel = new ReviewerOverviewActionPanel(() -> getSelectedElements());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();

		this.observe(this.model);
		this.model.getReviewers().forEach(this::observe);

		this.onPropertyChange(Model.REVIEWERS, (evt) -> updateTableModel());
		this.onPropertyChange(Reviewer.OCCUPATION, (evt) -> updateTableModel());

		this.tableModel.updateData();

	}
	
	@Override
	protected List<EventSource> getEventSources() {
		return new LinkedList<EventSource>(List.of(this.actionPanel,
				new TableClickEventSource(EventId.EDIT, this.table, 2, () -> getSelectedElements())));
	}

	@Override
	protected AbstractDataTableModel<Reviewer> createTableModel() {
		return new ReviewerOverviewTableModel(List.of(NAME, THESIS_COUNT, OCCUPATION),
				List.of(r -> this.searchField.matchesSearch(r)), model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createUIElements() {
		super.createUIElements();
		this.table.getColumnModel().getColumn(2).setCellRenderer(new DividedProgressRenderer());
		((TableRowSorter<ReviewerOverviewTableModel>) this.table.getRowSorter()).setComparator(2, new OccupationComparator());
	}

	@Override
	protected SearchStrategy<Reviewer> createSearchStrategy() {
		return new ReviewerSearchStrategy();
	}

}
