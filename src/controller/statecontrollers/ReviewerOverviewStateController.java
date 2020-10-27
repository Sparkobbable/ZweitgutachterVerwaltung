package controller.statecontrollers;

import static model.enums.EventId.EDIT;

import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import view.View;
import view.table.OverviewTable;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ReviewerOverviewStateController extends AbstractStateController {

	public ReviewerOverviewStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.REVIEWER_OVERVIEW, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EDIT, (params) -> switchToEdit(params));
	}

	/**
	 * Deletes the selected entry from the model
	 */
	public void deleteEntry() {
		// TODO modify model, not view
		int rowIdx = ((OverviewTable) this.view.assumeState(state)).getReviewerOverviewTable().getSelectedRow();
		this.data.removeIdx(rowIdx);
	}

	private void switchToEdit(Supplier<?>[] params) {
		// TODO check that one and only one row is selected
		Logger.getLogger(ReviewerOverviewStateController.class.getName())
				.info(String.format("Starting editmode on reviewer %s", params[0].get()));
//		((List<Reviewer>) params[0].get()).get(0).setSelected(true);
		data.getReviewers().get((int) params[0].get()).setSelected(true);;
		switchState(ApplicationState.REVIEWER_EDITOR);
	}
}
