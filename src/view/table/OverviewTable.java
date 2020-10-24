package view.table;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.ReviewerList;
import model.constants.ViewID;
import view.AbstractView;
import view.actions.OverviewTableActions;

public class OverviewTable extends AbstractView<OverviewTable> {

	private static final long serialVersionUID = 1L; // TODO remove ?!

	// UI-components
	private JTable reviewerOverviewTable;
	private JScrollPane reviewerOverviewScrollPane;
	private OverviewTableActions actions;
	private ReviewerList reviewers;

	public OverviewTable(String id, ReviewerList reviewers) {
		super(id, "Dozentenübersicht");
		this.reviewers = reviewers;
		this.actions = new OverviewTableActions(ViewID.ACTIONS.getViewID());
	}

	@Override
	public void init() {
		super.init();
		this.setBackground(Color.YELLOW); // TODO only for component identification, remove before launch
		
		this.reviewerOverviewTable = new JTable(new ReviewerOverviewTableModel(reviewers));
		this.reviewerOverviewScrollPane = new JScrollPane(this.reviewerOverviewTable);
		this.reviewerOverviewTable.setFillsViewportHeight(true);
		this.reviewerOverviewScrollPane.setBackground(Color.PINK);
		this.setLayout(new GridLayout(1, 1)); // TODO replace with more elegant solution? @see
												// https://stackoverflow.com/questions/14259543/how-to-make-a-jpanel-expand-to-max-width-in-another-jpanel
		this.add(reviewerOverviewScrollPane);
		
		this.add(this.actions);
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
}
