package view.overview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EventSource;
import model.Model;
import model.data.Reviewer;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.actions.OverviewTableActions;
import view.eventsources.TableClickEventSource;
import view.tableModels.ReviewerOverviewTableModel;

public class OverviewPanel extends AbstractView {

	private static final long serialVersionUID = 1L; // TODO remove ?!

	private Model model;

	// UI-components
	private ReviewerOverviewTableModel tableModel;
	private JTable reviewerOverviewTable;
	private JScrollPane reviewerOverviewScrollPane;
	private OverviewTableActions actions;

	/**
	 * Creates a view containing a table presenting the reviewers and buttons for
	 * interacting with the data
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param model  Needs the model as the data access
	 */
	public OverviewPanel(ViewId viewId, Model model) {
		super(viewId, "Dozentenübersicht");
		this.model = model;
		this.actions = new OverviewTableActions(ViewId.ACTIONS, () -> getSelectedReviewerIds());

		this.setLayout(new BorderLayout());
		this.setBackground(Color.YELLOW); // TODO only for component identification, remove before launch

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeConsumers();
		addObservables(this.model);
	}

	private void addUIElements() {
		this.add(this.reviewerOverviewScrollPane, BorderLayout.CENTER);
		this.add(this.actions, BorderLayout.PAGE_END);
	}

	protected void createUIElements() {
		this.tableModel = new ReviewerOverviewTableModel(model);
		this.reviewerOverviewTable = new JTable(this.tableModel);
		this.reviewerOverviewScrollPane = new JScrollPane(this.reviewerOverviewTable);

		this.reviewerOverviewTable.setFillsViewportHeight(true);
		// TODO observe sorting behavior when bachelor thesis count >= 10
		this.reviewerOverviewTable.setAutoCreateRowSorter(true);

	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(actions,
				new TableClickEventSource(EventId.EDIT, reviewerOverviewTable, () -> getSelectedReviewerIds()));
	}

	protected int[] getSelectedReviewerIds() {
		return IntStream.of(this.reviewerOverviewTable.getSelectedRows())
				.map(this.reviewerOverviewTable::convertRowIndexToModel).toArray();
	}

	@SuppressWarnings("unchecked")
	private void initializePropertyChangeConsumers() {
		this.onPropertyChange("reviewers", (evt) -> updateReviewers((List<Reviewer>) evt.getNewValue()));
	}

	private void updateReviewers(List<Reviewer> reviewers) {
		this.tableModel.fireTableDataChanged();
		this.repaint();
	}

}
