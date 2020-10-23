package view;

import javax.swing.JButton;

import view.table.OverviewTable;


/**
 * Basic home panel, allows navigating to different application sections
 */
public class HomePanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private JButton showReviewersButton;
	private JButton importButton;
	private JButton analyseButton;
	
	private MainWindow mainWindow; //TODO remove (preferred) or move to superclass
	
	public HomePanel(MainWindow mainWindow) {
		super("Home");
		this.mainWindow = mainWindow;
		
		showReviewersButton = new JButton("Gutachter anzeigen");
		importButton = new JButton("Importiere Erstgutachter");
		analyseButton = new JButton("Analyse");
		showReviewersButton.addActionListener(e -> openReviewerOverview());
	}
	
	public void init() {
		super.init();
		
		this.add(showReviewersButton);
		this.add(importButton);
		this.add(analyseButton);
	}

	
	/**
	 * Opens a Overview View to visualize the BachelorThesis per Reviewer
	 */
	public void openReviewerOverview() {
		new OverviewTable().openOverview(mainWindow);
	}
	
}
