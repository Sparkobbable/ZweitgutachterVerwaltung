package controller;

import static model.constants.ButtonId.SHOW_REVIEWERS;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import model.BachelorThesis;
import model.Reviewer;
import model.ReviewerList;
import view.AbstractView;
import view.HomePanel;
import view.MainWindow;
import view.table.OverviewTable;

public class Controller {
	private ReviewerList data;
	private MainWindow window;

	// TODO decide if all Views should be stored in a map and accessed by their IDs
	// (probably yes)
	private HomePanel homePanel;
	private OverviewTable overviewTablePanel;

	public Controller() {
		this.data = mockReviewerList();
		this.homePanel = new HomePanel("Homepanel#1");
		this.overviewTablePanel = new OverviewTable("OverviewTablePanel#1", data);

		this.window = new MainWindow();
		this.window.registerView(this.homePanel); // TODO move to constant class
		this.window.registerView(this.overviewTablePanel); // TODO move to constant class

		this.homePanel.onButtonClick(SHOW_REVIEWERS, () -> switchToView(overviewTablePanel));

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
