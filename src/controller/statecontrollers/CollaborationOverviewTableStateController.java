package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Controller;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.enums.ComboBoxMode;
import view.View;
import view.panels.collaboration.CollaborationOptionsPanel;
import view.widgets.PieChart;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#COLLABORATION_TABLE}
 */
public class CollaborationOverviewTableStateController extends AbstractCollaborationOverviewStateController {

	public CollaborationOverviewTableStateController(View view, Controller controller, Model model) {
		super(ApplicationState.COLLABORATION_TABLE, view, controller, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION,
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.CHOOSE_REVIEW_FILTER, (params) -> switchData((ComboBoxMode) params[0].get()));
	}

	/**
	 * Switches between different data in counted which will get presented in the
	 * {@link CollaborationTable} or {@link PieChart}.
	 * 
	 * @param reviewerFilter - selected dataMode from comboBox in
	 *                       {@link CollaborationOptionsPanel}
	 */
	protected void switchData(ComboBoxMode reviewerFilter) {
		this.reviewerFilter = reviewerFilter;
		if (this.model.getApplicationState() != this.state) {
			System.out.println("skip");
			return;
		}
		System.out.println("Data:" + reviewerFilter);
		switch (reviewerFilter) {
		case FIRSTREVIEWER:
			this.model.setCollaboratingReviewers(this.getReviewerCount(this.setCollaborationFirstReviewers()));
			break;
		case SECONDREVIEWER:
			this.model.setCollaboratingReviewers(this.getReviewerCount(this.setCollaborationSecondReviewers()));
			break;
		case ALLREVIEWER:
			this.model.setCollaboratingReviewers(this.getReviewerCount(this.setCollaborationAllReviewers()));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}

	private HashMap<Reviewer, Double> getReviewerCount(ArrayList<Reviewer> reviewers) {
		HashMap<Reviewer, Double> result = new HashMap<>();

		for (Reviewer currentReviewer : reviewers) {
			if (!result.containsKey(currentReviewer)) {
				System.out.println(this.countReviewerperArrayList(reviewers, currentReviewer));
				result.put(currentReviewer, this.countReviewerperArrayList(reviewers, currentReviewer));
			}
		}
		return result;
	}

	private double countReviewerperArrayList(ArrayList<Reviewer> reviewers, Reviewer reviewer) {
		double number = 0.0;
		for (Reviewer currentReviewer : reviewers) {
			if (currentReviewer.equals(reviewer)) {
				number++;
			}
		}
		return number;
	}
}
