package view;

import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import model.CurrentData;
import view.table.OverviewTable;

public class MainWindow extends JFrame {
	private JMenuBar menuBar;
	private JPanel homePanel;
	private JScrollPane reviewerOverview;
	private CurrentData data;
	
	/**
	 * Initializes the Window
	 * @param data Needs the DataAccess of the Application
	 */
	public void init(CurrentData data) {
		this.data = data;
		
		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Anwendungsname");
		
		this.homePanel = new JPanel();
		this.homePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Home", TitledBorder.LEFT, TitledBorder.TOP));
		
		this.menuBar = new JMenuBar();
		
		JButton bGutachter = new JButton("Gutachter anzeigen");
		JButton bImport = new JButton("Importiere Erstgutachter");
		JButton bAnalyse = new JButton("Analyse");
		this.homePanel.add(bGutachter);
		this.homePanel.add(bImport);
		this.homePanel.add(bAnalyse);
		this.add(this.homePanel);
		
		bGutachter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				openReviewerOverview();
			}
		});
		
		this.setVisible(true);
		
//		this.menu = new JMenu();
//		this.menu.add(new JMenuItem());
	}
	
	/**
	 * Opens a Overview View to visualize the BachelorThesis per Reviewer
	 */
	public void openReviewerOverview() {
		new OverviewTable().openOverview(this);
	}

	public CurrentData getData() {
		return data;
	}

	public JMenuBar getMenubar() {
		return menuBar;
	}

	public void setMenubar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JPanel getHomePanel() {
		return homePanel;
	}

	public void setHomePanel(JPanel homePanel) {
		this.homePanel = homePanel;
	}

	public JScrollPane getReviewerOverview() {
		return reviewerOverview;
	}

	public void setReviewerOverview(JScrollPane reviewerOverview) {
		this.reviewerOverview = reviewerOverview;
		this.add(reviewerOverview);
	}
	
}
