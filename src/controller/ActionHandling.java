package controller;

import static model.constants.ButtonId.BACK;
import static model.constants.ButtonId.EDIT;
import static model.constants.ButtonId.SHOW_REVIEWERS;
import static model.constants.ButtonId.DELETE;

import java.util.Objects;

import model.constants.ButtonId;
import view.HomePanel;
import view.MainWindow;
import view.table.OverviewTable;

/**
 * Provides actionhandling for different UI-Components (currently only for buttons)
 */
public class ActionHandling {
	private static ActionHandling initialized;
	private Controller controller;
	private HomePanel homePanel;
	private OverviewTable overviewTablePanel;
	private MainWindow window;
	
	private OverviewReviewerController overviewReviewerController;

	private ActionHandling(Controller controller) {
		this.controller = controller;
		this.homePanel = controller.homePanel;
		this.window = controller.window;
		this.overviewTablePanel = controller.overviewTablePanel;
		this.overviewReviewerController = controller.overviewReviewerController;
	}

	/**
	 * Uses a singleton to instantiate this class. 
	 * @param controller Needs the controller to address ui-components and methods
	 * @return Returns the actionhandling-access
	 */
	public static ActionHandling getActionHandling(Controller controller) {
		if (Objects.isNull(initialized)) {
			return new ActionHandling(controller);
		}
		return initialized;
	}
	
	/**
	 * Sets the programmed button handlers.
	 */
	public void setButtonHandlers() {
		this.homePanel.onButtonClick(SHOW_REVIEWERS, () -> this.controller.switchToView(this.overviewTablePanel));
		this.overviewTablePanel.getActions().onButtonClick(EDIT, () -> this.overviewReviewerController.switchToEdit()); 
		this.overviewTablePanel.getActions().onButtonClick(DELETE, () -> this.overviewReviewerController.deleteEntry());
		//TODO implement navigation stack (?) , this is for demonstration only
		this.window.getMenuHandler().onMenuClick(BACK, () -> this.controller.switchToView(this.homePanel)); 
	}
}
