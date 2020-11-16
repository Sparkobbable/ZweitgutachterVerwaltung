package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import view.View;

public class CollaborationOverviewPieChartStateController extends AbstractCollaborationOverviewStateController {

	public CollaborationOverviewPieChartStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.COLLABORATION_PIECHART, view, applicationStateController, model);
	}

	
	public HashMap<Reviewer, Double> getReviewerRatio(ArrayList<Reviewer> reviewers) {
		double total = reviewers.size();
		HashMap<Reviewer, Double> result = new HashMap<>();
		
		for(Reviewer currentReviewer : reviewers) {
			double ratio = this.countReviewerperArrayList(reviewers, currentReviewer) / total;
			result.put(currentReviewer, ratio);
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
