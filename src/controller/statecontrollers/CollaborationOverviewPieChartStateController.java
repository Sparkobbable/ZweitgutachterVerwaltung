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
import view.widgets.ComboBoxPanel;
import view.widgets.PieChart;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#COLLABORATION_PIECHART}
 */
public class CollaborationOverviewPieChartStateController extends AbstractCollaborationOverviewStateController {

	public CollaborationOverviewPieChartStateController(View view, Controller controller,
			Model model) {
		super(ApplicationState.COLLABORATION_PIECHART, view, controller, model);
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION,
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.CHOOSE_REVIEW_FILTER,
				(params) -> this.switchData((ComboBoxMode) params[0].get()));
	}
	
	/**
	 * Switches between different data in percentage which
	 * will get presented in the {@link CollaborationTable} or {@link PieChart}.
	 * 
	 * @param reviewerFilter - selected dataMode from {@link ComboBoxPanel} in {@link CollaborationOptionsPanel}
	 */
	@Override
	protected void switchData(ComboBoxMode reviewerFilter) {
		this.reviewerFilter = reviewerFilter;
		if (this.model.getApplicationState() != this.state) {
			return;
		}
		switch(reviewerFilter) {
		case FIRSTREVIEWER:
			this.model.setCollaboratingReviewers(this.getReviewerRatio(this.setCollaborationFirstReviewers()));
			break;
		case SECONDREVIEWER:
			this.model.setCollaboratingReviewers(this.getReviewerRatio(this.setCollaborationSecondReviewers()));
			break;
		case ALLREVIEWER:
			this.model.setCollaboratingReviewers(this.getReviewerRatio(this.setCollaborationAllReviewers()));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}
	
	private HashMap<Reviewer, Double> getReviewerRatio(ArrayList<Reviewer> reviewers) {
		double total = reviewers.size();
		HashMap<Reviewer, Double> result = new HashMap<>();
		
		for(Reviewer currentReviewer : reviewers) {
			if(!result.containsKey(currentReviewer)) {
				double ratio = this.countReviewerperArrayList(reviewers, currentReviewer) / total;
				result.put(currentReviewer, ratio);
			}
		}
		return result;
	}
	
	private double countReviewerperArrayList(ArrayList<Reviewer> reviewers, Reviewer reviewer) {
		double number = 0.0;
		for(Reviewer currentReviewer : reviewers) {
			if(currentReviewer.equals(reviewer)) {
				number++;
			}
		}
		return number;
	}
}
