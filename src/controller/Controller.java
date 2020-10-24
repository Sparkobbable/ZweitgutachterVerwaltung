package controller;

import static model.constants.ButtonId.SHOW_REVIEWERS;
import static model.constants.ButtonId.EDIT;
import static model.constants.ButtonId.BACK;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.BachelorThesis;
import model.Reviewer;
import model.ReviewerList;
import model.constants.ViewID;
import view.AbstractView;
import view.HomePanel;
import view.MainWindow;
import view.table.OverviewTable;
import view.table.ReviewerOverviewTableModel;

public class Controller {
	private ReviewerList data;
	private MainWindow window;

	// TODO decide if all Views should be stored in a map and accessed by their IDs
	// (probably yes)
	private HomePanel homePanel;
	private OverviewTable overviewTablePanel;

	public Controller() {
		this.data = mockReviewerList();
		this.homePanel = new HomePanel(ViewID.HOME.getViewID());
		this.overviewTablePanel = new OverviewTable(ViewID.OVERVIEW_TABLE.getViewID(), data);

		this.window = new MainWindow();
		this.window.registerView(this.homePanel); // TODO move to constant class
		this.window.registerView(this.overviewTablePanel); // TODO move to constant class

		this.homePanel.onButtonClick(SHOW_REVIEWERS, () -> switchToView(overviewTablePanel));
		this.overviewTablePanel.getActions().onButtonClick(EDIT, () -> switchToEdit()); 
		//TODO implement navigation stack (?) , this is for demonstration only
		this.window.getMenuHandler().onMenuClick(BACK, () -> switchToView(homePanel)); 

	}

	//TODO move to main.Main
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.initWindow();
	}

	/**
	 * Initializes and shows the window.
	 */
	private void initWindow() {
		homePanel.init();
//		overviewTablePanel.init(); //TODO initialize everything at the beginning or init lazily?
		window.init();
	}

	/**
	 * Opens a Overview View to visualize the BachelorThesis per Reviewer
	 */
	public void switchToView(AbstractView<?> view) {
		Logger.getLogger(Controller.class.getName()).info(String.format("Switched to View: %s", view.getId()));
		if (!view.isInitialized()) {
			view.init(); //TODO initialize everything at the beginning or init lazily?
		}
		window.switchToView(view.getId());
	}
	
	private void switchToEdit() {
		int selectedIdx = this.overviewTablePanel.getReviewerOverviewTable().getSelectedRow();
		String reviewerName = (String) this.overviewTablePanel.getReviewerOverviewTable().getValueAt(selectedIdx, ReviewerOverviewTableModel.REVIEWER_COLUMN);
		Reviewer reviewer = this.data.findReviewerByName(reviewerName);
		Logger.getLogger(Controller.class.getName()).info(String.format("Starting editmode on reviewer %s", reviewer.getName()));
		
		switchToView(this.homePanel); //TODO Implement new edit-view and pass the reviewer
	}

	/**
	 * Creates a sample list of reviewers
	 * <p>
	 * TODO add valid BachelorThesises TODO remove this before deploying to prod
	 */
	private ReviewerList mockReviewerList() {
		List<Reviewer> reviewerList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Reviewer reviewer = new Reviewer(String.format("Dozent #%d", i));
			int rand = (int) (Math.random() * 5);
			for (int j = 0; j < rand; j++) {
				reviewer.addBachelorThesis(new BachelorThesis(null, null, null));
			}
			reviewerList.add(reviewer);
		}
		return new ReviewerList(reviewerList);
	}
}
