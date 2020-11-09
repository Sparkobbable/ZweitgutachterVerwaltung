package controller.statecontrollers;

import static model.enums.EventId.ADD_THESIS_TO_REVIEWER;

import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import model.Model;
import model.data.BachelorThesis;
import model.data.Review;
import model.enums.ApplicationState;
import model.enums.ReviewStatus;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#THESIS_ASSIGNMENT}
 */
public class ThesisAssignmentStateController extends AbstractStateController {

	public ThesisAssignmentStateController(View view,
			ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.THESIS_ASSIGNMENT, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(ADD_THESIS_TO_REVIEWER, this::addThesis);
	}

	private void addThesis(Supplier<?>[] params) {
		int[] thesisIndices = (int[]) params[0].get();
		IntStream.of(thesisIndices).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder()).forEach(this::setThesis);
		switchToLastVisitedState();
	}

	private void setThesis(Integer idx) {
		BachelorThesis thesis = this.model.getThesisMissingSecReview().get(idx);
		this.model.getSelectedReviewer().get().addBachelorThesis(thesis);
		thesis.setSecondReview(new Review(this.model.getSelectedReviewer().get(), false, ReviewStatus.REQUESTED, thesis)); //TODO what exactly is the ReviewStatus? When is it set/changed?
	}

}
