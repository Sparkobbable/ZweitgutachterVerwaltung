package view.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.EventSource;
import model.Model;
import model.enums.ViewId;
import view.AbstractView;
import view.actions.OverviewTableActions;

public class OverviewTable extends AbstractView {

	private static final long serialVersionUID = 1L; // TODO remove ?!

	// UI-components
	private JTable reviewerOverviewTable;
	private JScrollPane reviewerOverviewScrollPane;
	private OverviewTableActions actions;
	private Model reviewers;

	public OverviewTable(ViewId viewId, Model reviewers) {
		super(viewId, "Dozentenübersicht");
		this.reviewers = reviewers;
		
	}

	@Override
	public void init() {
		super.init();
		this.setBackground(Color.YELLOW); // TODO only for component identification, remove before launch
		this.actions.init();
		
		this.reviewerOverviewTable = new JTable(new ReviewerOverviewTableModel(reviewers));
		this.reviewerOverviewScrollPane = new JScrollPane(this.reviewerOverviewTable);
		this.reviewerOverviewTable.setFillsViewportHeight(true);
		this.reviewerOverviewScrollPane.setBackground(Color.PINK);
		this.setLayout(new BorderLayout()); 
		this.add(reviewerOverviewScrollPane, BorderLayout.CENTER);
		this.add(this.actions, BorderLayout.PAGE_END);
		
	}

	/**
	 * Accesses actions as buttons which are embedded in this view.
	 * @return Returns a JPanel containing the actions
	 */
	public OverviewTableActions getActions() {
		return this.actions;
	}
	
	public JTable getReviewerOverviewTable() {
		return reviewerOverviewTable;
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(actions);
	}

	@Override
	protected void createUIElements() {
		this.actions = new OverviewTableActions(ViewId.ACTIONS);		
	}
}
