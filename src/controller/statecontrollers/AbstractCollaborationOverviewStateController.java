package controller.statecontrollers;

import java.util.ArrayList;

import controller.Controller;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import model.enums.ReviewType;
import view.View;
import view.panels.collaboration.CollaborationOptionsPanel;
import view.panels.collaboration.CollaborationPanel;
import view.panels.collaboration.CollaborationTable;
import view.widgets.PieChart;

/**
 * Abstract ApplicationStateController which includes all methods that are
 * needed for every collaborationStateController. They are divided because they
 * apply to different ApplicationStates. Although they have methods in common.
 * They inherit them from this class.
 */
public abstract class AbstractCollaborationOverviewStateController extends AbstractStateController {

	protected ComboBoxMode reviewerFilter;

	public AbstractCollaborationOverviewStateController(ApplicationState applicationState, View view,
			Controller controller, Model model) {
		super(applicationState, view, controller, model);
		this.registerEvent(EventId.INITIALIZE, (params) -> this.initialize());

	}

	/**
	 * Switches between different graphics including {@link CollaborationTable}} and
	 * {@link PieChart} within the {@link CollaborationPanel}.
	 * 
	 * @param params - Selected presentationMode from comboBox in
	 *               {@link CollaborationOptionsPanel}
	 */
	protected void switchPresentation(ComboBoxMode params) {
		switch (params) {
		case TABLE:
			this.switchState(ApplicationState.COLLABORATION_TABLE);
			break;
		case PIECHART:
			this.switchState(ApplicationState.COLLABORATION_PIECHART);
			break;
		default:
			throw new IllegalArgumentException("Invalid PresentationMode from ComboBox");
		}
	}

	/**
	 * Collects data for {@link CollaborationPanel} and saves it to the model. This
	 * method gets all FirstReviewers, which collaborate with the selected Reviewer.
	 * 
	 * @return ArrayList of collaborating reviewers
	 */
	protected ArrayList<Reviewer> setCollaborationFirstReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		this.model.getSelectedReviewer()
				.ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
						.filter(review -> review.getReviewType().equals(ReviewType.SECOND_REVIEW))
						.forEach(review -> result.add(review.getBachelorThesis().getFirstReview().getReviewer())));
		System.out.println("Reviewers:" + result.toString());
		return result;
	}

	/**
	 * Collects data for {@link CollaborationPanel} and saves it to the model. This
	 * method gets all SecondReviewers, which collaborate with the selected
	 * Reviewer.
	 * 
	 * @return ArrayList of collaborating reviewers
	 */
	protected ArrayList<Reviewer> setCollaborationSecondReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		this.model.getSelectedReviewer()
				.ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
						.filter(review -> review.getReviewType().equals(ReviewType.FIRST_REVIEW))
						.forEach(review -> review.getBachelorThesis().getSecondReview()
								.ifPresent(secondreview -> result.add(secondreview.getReviewer()))));
		System.out.println("Reviewers:" + result.toString());
		return result;
	}

	/**
	 * Collects data for {@link CollaborationPanel} and saves it to the model. This
	 * method gets all Reviewers, which collaborate with the selected Reviewer.
	 * 
	 * @return ArrayList of collaborating reviewers
	 */
	protected ArrayList<Reviewer> setCollaborationAllReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		this.model.getSelectedReviewer()
				.ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
						.filter(review -> review.getReviewType().equals(ReviewType.SECOND_REVIEW))
						.forEach(review -> result.add(review.getBachelorThesis().getFirstReview().getReviewer())));

		this.model.getSelectedReviewer()
				.ifPresent(reviewer -> reviewer.getAllSupervisedReviews().stream()
						.filter(review -> review.getReviewType().equals(ReviewType.FIRST_REVIEW))
						.forEach(review -> review.getBachelorThesis().getSecondReview()
								.ifPresent(secondreview -> result.add(secondreview.getReviewer()))));
		System.out.println("Reviewers:" + result.toString());
		return result;
	}

	/**
	 * Initializes the data set for the selected Reviewer. Gets invoked when the
	 * panel gets opened.
	 */
	protected void initialize() {
		if (this.reviewerFilter == null) {
			this.reviewerFilter = ComboBoxMode.FIRSTREVIEWER;
			System.out.println("(re)set");
		}
		this.switchData(this.reviewerFilter);
	}

	protected abstract void switchData(ComboBoxMode reviewerFilter);
}
