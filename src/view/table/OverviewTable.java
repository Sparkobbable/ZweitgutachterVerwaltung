package view.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.Observable;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.EventSource;
import model.Model;
import model.enums.ViewId;
import view.AbstractView;
import view.actions.OverviewTableActions;

@SuppressWarnings("deprecation")
public class OverviewTable extends AbstractView {

	private static final long serialVersionUID = 1L; // TODO remove ?!

	// UI-components
	private JTable reviewerOverviewTable;
	private JScrollPane reviewerOverviewScrollPane;
	private OverviewTableActions actions;
	private Model reviewers;

	/**
	 * Creates a view containing a table presenting the reviewers and buttons for
	 * interacting with the data
	 * 
	 * @param viewId    Unique viewId from {@link ViewId}
	 * @param reviewers Needs the reviewers as the data access
	 */
	public OverviewTable(ViewId viewId, Model reviewers) {
		super(viewId, "Dozentenübersicht");
		this.reviewers = reviewers;
		this.createUIElements();
		this.registerEventSources();
		addObservables(this.reviewers);
	}

	/**
	 * Initializes this component and loads data into the table
	 */
	@Override
	public void init() {
		super.init();
		this.setBackground(Color.YELLOW); // TODO only for component identification, remove before launch
		this.actions.init();
		
		refreshElements();
	}
	
	private void refreshElements() {
		initTable();
		
		this.reviewerOverviewScrollPane = new JScrollPane(this.reviewerOverviewTable);

		this.reviewerOverviewScrollPane.setBackground(Color.PINK);

		this.setLayout(new BorderLayout());
		this.add(reviewerOverviewScrollPane, BorderLayout.CENTER);
		this.add(this.actions, BorderLayout.PAGE_END);
	}

	private void initTable() {
		this.reviewerOverviewTable = new JTable(new ReviewerOverviewTableModel(reviewers));
		this.reviewerOverviewTable.setFillsViewportHeight(true);
	}

	/**
	 * Accesses actions as buttons which are embedded in this view.
	 * 
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
		this.actions = new OverviewTableActions(ViewId.ACTIONS, () -> getSelectedReviewerId());
	}

	protected int getSelectedReviewerId() {
//		int nameColumn = ((AbstractTableModel) this.getReviewerOverviewTable().getModel()).findColumn("Name");
//		return IntStream.of(reviewerOverviewTable.getSelectedRows())
//				.mapToObj(selectedRow -> reviewers.findReviewerByName((String) this.getReviewerOverviewTable().getValueAt(selectedRow, nameColumn)))
//				.collect(Collectors.toList());
		return reviewerOverviewTable.getSelectedRow();
	}

	@Override
	public void update(Observable o, Object arg) {
		refreshElements();
		repaint();
	}

}
