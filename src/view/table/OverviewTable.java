package view.table;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.ReviewerList;
import model.constants.ViewID;
import view.AbstractView;
import view.actions.OverviewTableActions;

@SuppressWarnings("deprecation")
public class OverviewTable extends AbstractView<OverviewTable> {

	private static final long serialVersionUID = 1L; // TODO remove ?!

	// UI-components
	private JTable reviewerOverviewTable;
	private JScrollPane reviewerOverviewScrollPane;
	private OverviewTableActions actions;
	private ReviewerList reviewers;

	/**
	 * Creates a view containing a table presenting the reviewers and buttons for interacting with the data
	 * @param id Unique viewID from {@link ViewID} 
	 * @param reviewers Needs the reviewers as the data access
	 */
	public OverviewTable(String id, ReviewerList reviewers) {
		super(id, "Dozentenübersicht");
		this.reviewers = reviewers;
		//TODO doesnt work, why??
		this.reviewers.addObserver(new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				OverviewTable.this.reviewers = (ReviewerList) arg;
				OverviewTable.this.initTable();
			}
		});
		this.actions = new OverviewTableActions(ViewID.ACTIONS.getViewID());
	}

	/**
	 * Initializes this component and loads data into the table
	 */
	@Override
	public void init() {
		super.init();
		this.setBackground(Color.YELLOW); // TODO only for component identification, remove before launch
		
		initTable();
		this.reviewerOverviewScrollPane = new JScrollPane(this.reviewerOverviewTable);
		
		this.reviewerOverviewScrollPane.setBackground(Color.PINK);
		this.setLayout(new GridLayout(2, 1)); // TODO replace with more elegant solution? @see
												// https://stackoverflow.com/questions/14259543/how-to-make-a-jpanel-expand-to-max-width-in-another-jpanel
		this.add(reviewerOverviewScrollPane);
		
		this.add(this.actions);
	}
	
	private void initTable() {
		this.reviewerOverviewTable = new JTable(new ReviewerOverviewTableModel(reviewers));
		this.reviewerOverviewTable.setFillsViewportHeight(true);
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
