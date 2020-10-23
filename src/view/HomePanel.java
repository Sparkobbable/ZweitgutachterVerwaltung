package view;

import static model.constants.ButtonId.ANALYSE;
import static model.constants.ButtonId.IMPORT_FIRST_REVIEWERS;
import static model.constants.ButtonId.SHOW_REVIEWERS;

import javax.swing.JButton;

import view.table.OverviewTable;

/**
 * Basic home panel, allows navigating to different application sections
 */
public class HomePanel extends AbstractPanel<HomePanel> {

	private static final long serialVersionUID = 1L;

	private MainWindow mainWindow; // TODO remove (preferred) or move to superclass

	public HomePanel(MainWindow mainWindow) {
		super("Home");
		this.mainWindow = mainWindow;
		onButtonClick(SHOW_REVIEWERS, this::openReviewerOverview);
	}
	
	public void init() {
		super.init();
		addButton(SHOW_REVIEWERS);
		addButton(IMPORT_FIRST_REVIEWERS);
		addButton(ANALYSE);
		
	}

	/**
	 * Opens a Overview View to visualize the BachelorThesis per Reviewer
	 */
	public void openReviewerOverview() {
		new OverviewTable().openOverview(mainWindow);
	}
	
	@Override
	protected void createButtons() {
		super.createButtons();
		buttons.put(SHOW_REVIEWERS, new JButton("Gutachter anzeigen"));
		buttons.put(IMPORT_FIRST_REVIEWERS, new JButton("Importiere Erstgutachter"));
		buttons.put(ANALYSE, new JButton("Analyse"));

	}
}
