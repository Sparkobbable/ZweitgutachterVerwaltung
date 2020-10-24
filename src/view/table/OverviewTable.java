package view.table;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.ReviewerList;
import view.AbstractView;

public class OverviewTable extends AbstractView<OverviewTable> {

	private static final long serialVersionUID = 1L; // TODO remove ?!

	// UI-components
	private JTable reviewerOverviewTable;
	private JScrollPane reviewerOverviewScrollPane;

	public OverviewTable(String id, ReviewerList reviewers) {
		super(id, "Dozentenübersicht");
		this.reviewerOverviewTable = new JTable(new ReviewerOverviewTableModel(reviewers));
		this.reviewerOverviewScrollPane = new JScrollPane(this.reviewerOverviewTable);
		this.reviewerOverviewScrollPane.setBackground(Color.PINK);
		this.setLayout(new GridLayout(1, 1)); // TODO replace with more elegant solution? @see
												// https://stackoverflow.com/questions/14259543/how-to-make-a-jpanel-expand-to-max-width-in-another-jpanel
	}

	@Override
	public void init() {
		super.init();
		this.setBackground(Color.YELLOW); // TODO only for component identification, remove before launch
		this.add(reviewerOverviewScrollPane);
	}
}
