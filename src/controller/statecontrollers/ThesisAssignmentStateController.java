package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS_TO_REVIEWER;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import controller.UndoRedo.AddBachelorThesisCommand;
import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import model.data.SecondReview;
import model.enums.ApplicationState;
import model.enums.ReviewStatus;
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
		this.registerEvent(ADD_THESIS_TO_REVIEWER,
				(params) -> this.addSecondReviewThesis((List<BachelorThesis>) params[0].get()));
	}

	private void addSecondReviewThesis(List<BachelorThesis> bachelorThesesToAdd) {

		Reviewer reviewer = this.model.getSelectedReviewer().orElseThrow();

		if (bachelorThesesToAdd.size() + reviewer.getTotalReviewCount() > reviewer.getMaxSupervisedThesis()) {
			this.view.alert(String.format(
					"Die Anzahl der gewählten Bachelorarbeiten überschreitet die maximale Anzahl an Arbeiten die der Dozent %s betreut.",
					reviewer.getName()), JOptionPane.WARNING_MESSAGE);
			return;

		}
		bachelorThesesToAdd.forEach(bachelorThesis -> this.commandExecutionController
				.execute(new AddBachelorThesisCommand(reviewer, bachelorThesis)));
		switchToLastVisitedState();
	}
}
