package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS_TO_REVIEWER;

import java.util.List;
import javax.swing.JOptionPane;

import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.ReviewType;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#THESIS_ASSIGNMENT}
 */
public class ThesisAssignmentStateController extends AbstractStateController {

	public ThesisAssignmentStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.THESIS_ASSIGNMENT, view, applicationStateController, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(ADD_THESIS_TO_REVIEWER, (params) -> this.addSecondReviewThesis((List<BachelorThesis>) params[0].get()));
	}

	private void addSecondReviewThesis(List<BachelorThesis> bachelorThesesToAdd) {

		Reviewer reviewer = this.model.getSelectedReviewer().orElseThrow();

		if (bachelorThesesToAdd.size() + reviewer.getTotalReviewCount() > reviewer.getMaxSupervisedThesis()) {
			this.view.alert(String.format(
					"Die Anzahl der gewählten Bachelorarbeiten überschreitet die maximale Anzahl an Arbeiten die der Dozent %s betreut.",
					reviewer.getName()), JOptionPane.WARNING_MESSAGE);
			return;

		}
		bachelorThesesToAdd.forEach(bachelorThesis -> reviewer.addBachelorThesis(bachelorThesis, ReviewType.SECOND_REVIEW));
		switchToLastVisitedState();
	}
}
