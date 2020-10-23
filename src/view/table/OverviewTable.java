package view.table;

import static util.SystemUtils.logInfo;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import view.MainWindow;

public class OverviewTable {
	
	/**
	 * Creates a new view of a overview table
	 * @param window Needs access to the MainWindow
	 */
	public void openOverview(MainWindow window) {
		logInfo("Starting ReviewerOverview");
		window.getHomePanel().setVisible(false);
		JTable overviewTable = new JTable(new ReviewerOverviewTableModel(window.getData()));
		JScrollPane reviewerOverview = new JScrollPane(overviewTable);
		reviewerOverview.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dozentenübersicht", TitledBorder.LEFT, TitledBorder.TOP));
		window.setReviewerOverview(reviewerOverview);
		window.getReviewerOverview().setVisible(true);
	}
}
