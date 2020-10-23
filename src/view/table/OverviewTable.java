package view.table;

import static util.SystemUtils.logInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import view.MainWindow;

public class OverviewTable {
	private MainWindow window;
	
	/**
	 * Creates a new view of a overview table
	 * @param window Needs access to the MainWindow
	 */
	public void openOverview(MainWindow window) {
		logInfo("Starting ReviewerOverview");
		this.window = window;
		
		window.getHomePanel().setVisible(false);
		
		//TODO: Funktioniert somehow nicht :(
		JMenu back = new JMenu("Zurück");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeOverview();
			}
		});
		window.getMenubar().add(back);
		window.add(window.getMenubar());
		
		JTable overviewTable = new JTable(new ReviewerOverviewTableModel(window.getData()));
		JScrollPane reviewerOverview = new JScrollPane(overviewTable);
		reviewerOverview.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dozentenübersicht", TitledBorder.LEFT, TitledBorder.TOP));
		window.setReviewerOverview(reviewerOverview);
		window.getReviewerOverview().setVisible(true);
	}

	protected void closeOverview() {
		this.window.getReviewerOverview().setVisible(false);
		this.window.getHomePanel().setVisible(true);
	}
}
