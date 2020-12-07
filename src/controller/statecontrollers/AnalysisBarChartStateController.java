package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.Controller;
import model.Model;
import model.Pair;
import model.domain.Review;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

public class AnalysisBarChartStateController extends AbstractAnalysisStateController {

	public AnalysisBarChartStateController(View view, Controller controller,
			Model model) {
		super(ApplicationState.ANALYSE_BARCHART, view, controller, model);
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.ANALYSIS_CHOOSE_PRESENTATION, 
				(params) -> this.switchPresentation((String) params[0].get()));
		this.registerEvent(EventId.ANALYSIS_CHOOSE_DATA, 
				(params) -> this.switchData((String) params[0].get()));	
	}

	@Override
	protected void switchData(String reviewerFilter) {
		this.reviewerFilter = reviewerFilter;
		if (this.model.getApplicationState() != this.state) {
			System.out.println("skip");
			return;
		}
		System.out.println("Data:" + reviewerFilter);
		switch (reviewerFilter) {
		case "Nur Erstgutachter":
			this.model.setAnalyseReviewers(this.getReviewerCount(this.getAllFirstReviewers()));
			break;
		case "Nur Zweitgutachter":
			this.model.setAnalyseReviewers(this.getReviewerCount(this.getAllSecondReviewers()));
			break;
		case "Zweit- & Erstgutachter":
			this.model.setAnalyseReviewers(this.getReviewerCount(this.model.getReviewers()));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}
	
	private List<Reviewer> getAllFirstReviewers() {
		List<Reviewer> result = new ArrayList<>();
		for(Reviewer currentReviewer : this.model.getReviewers()) {
			for(Review currentReview : currentReviewer.getFirstReviews()) {
				if(!result.contains(currentReview.getReviewer())) {
					result.add(currentReview.getReviewer());
				}
			}
		}
		return result;
	}
	
	private List<Reviewer> getAllSecondReviewers() {
		List<Reviewer> result = new ArrayList<>();
		for(Reviewer currentReviewer : this.model.getReviewers()) {
			for(Review currentReview : currentReviewer.getUnrejectedSecondReviews()) {
				if(!result.contains(currentReview.getReviewer())) {
					result.add(currentReview.getReviewer());
				}
			}
		}
		return result;
	}
	
	private HashMap<Reviewer, Pair<Integer, Integer>> getReviewerCount(List<Reviewer> reviewers) {
		HashMap<Reviewer, Pair<Integer, Integer>> result = new HashMap<>();
		
		for(Reviewer currentReviewer : reviewers) {
			if(!result.containsKey(currentReviewer)) {
				result.put(currentReviewer, 
						new Pair<Integer, Integer>(
								currentReviewer.getFirstReviewCount(), currentReviewer.getSecondReviewCount()));
			}
		}
		return result;
	}
}
