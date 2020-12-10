package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS_TO_REVIEWER;
import static model.enums.EventId.SHOW_COLLABORATION;

import java.util.List;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.commands.reviewer.AddBachelorThesisCommand;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#THESIS_ASSIGNMENT}
 */
public class ThesesAssignmentStateController extends AbstractStateController {

	public ThesesAssignmentStateController(View view, Controller controller, Model model) {
		super(ApplicationState.THESIS_ASSIGNMENT, view, controller, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(ADD_THESIS_TO_REVIEWER,
				(params) -> this.addSecondReviewThesis((List<BachelorThesis>) params[0].get()));
		this.registerEvent(SHOW_COLLABORATION, (params) -> this.switchState(ApplicationState.COLLABORATION_TABLE));
	}

	private void addSecondReviewThesis(List<BachelorThesis> bachelorThesesToAdd) {

		Reviewer reviewer = this.model.getSelectedReviewer().orElseThrow();

		if (bachelorThesesToAdd.size() + reviewer.getTotalReviewCount() > reviewer.getMaxSupervisedThesis()) {
			this.popupWarn(String.format(
					"Die Anzahl der gewählten Bachelorarbeiten überschreitet die maximale Anzahl an Arbeiten die der Dozent %s betreut.",
					reviewer.getName()));
			return;

		}
		bachelorThesesToAdd.forEach(bachelorThesis -> this
				.execute(new AddBachelorThesisCommand(reviewer, bachelorThesis, ApplicationState.REVIEWER_EDITOR)));
		switchToLastVisitedState();
	}

}
