package view.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.Observable;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EventSource;
import model.Model;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.actions.OverviewTableActions;
import view.eventsources.TableClickEventSource;

@SuppressWarnings("deprecation")
public class OverviewPanel extends AbstractView {

	private static final long serialVersionUID = 1L; // TODO remove ?!

	private Model reviewers;

	// UI-components
	private ReviewerOverviewTableModel tableModel;
	private JTable reviewerOverviewTable;
	private JScrollPane reviewerOverviewScrollPane;
	private OverviewTableActions actions;

	/**
	 * Creates a view containing a table presenting the reviewers and buttons for
	 * interacting with the data
	 * 
	 * @param viewId    Unique viewId from {@link ViewId}
	 * @param reviewers Needs the reviewers as the data access
	 */
	public OverviewPanel(ViewId viewId, Model reviewers) {
		super(viewId, "Dozentenübersicht");
		this.reviewers = reviewers;
		this.actions = new OverviewTableActions(ViewId.ACTIONS, () -> getSelectedReviewerIds());

		this.createUIElements();
		this.registerEventSources();
		addObservables(this.reviewers);
	}

	@Override
	protected void createUIElements() {
		this.tableModel = new ReviewerOverviewTableModel(reviewers);
		this.reviewerOverviewTable = new JTable(this.tableModel);
		this.reviewerOverviewScrollPane = new JScrollPane(this.reviewerOverviewTable);
	}

	/**
	 * Initializes this component and loads data into the table
	 */
	@Override
	public void init() {
		super.init();
		this.setBackground(Color.YELLOW); // TODO only for component identification, remove before launch
		this.actions.init();
		this.reviewerOverviewTable.setFillsViewportHeight(true);

		this.setLayout(new BorderLayout());
		this.add(this.reviewerOverviewScrollPane, BorderLayout.CENTER);
		this.add(this.actions, BorderLayout.PAGE_END);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(actions,
				new TableClickEventSource(EventId.EDIT, reviewerOverviewTable, () -> getSelectedReviewerIds()));
	}

	protected int[] getSelectedReviewerIds() {
		return reviewerOverviewTable.getSelectedRows();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}

}
