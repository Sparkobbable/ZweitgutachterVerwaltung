package controller.statecontrollers;

import java.util.ArrayList;

import controller.Controller;
import model.Model;
import model.domain.Review;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
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
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.ANALYSIS_CHOOSE_DATA, 
				(params) -> this.switchData((ComboBoxMode) params[0].get()));	
	}

	@Override
	protected void switchData(ComboBoxMode comboBoxMode) {
		this.reviewerFilter = comboBoxMode;
		if (this.model.getApplicationState() != this.state) {
			System.out.println("skip");
			return;
		}
		System.out.println("Data:" + comboBoxMode);
		switch (comboBoxMode) {
		case FIRSTREVIEWER:
			this.model.setAnalyseReviewers(this.getAllFirstReviewers());
			break;
		case SECONDREVIEWER:
			this.model.setAnalyseReviewers(this.getAllSecondReviewers());
			break;
		case ALLREVIEWER:
			this.model.setAnalyseReviewers(this.model.getReviewers());
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}
	
	private ArrayList<Reviewer> getAllFirstReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		for(Reviewer currentReviewer : this.model.getReviewers()) {
			for(Review currentReview : currentReviewer.getFirstReviews()) {
				if(!result.contains(currentReview.getReviewer())) {
					result.add(currentReview.getReviewer());
				}
			}
		}
		return result;
	}
	
	private ArrayList<Reviewer> getAllSecondReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		for(Reviewer currentReviewer : this.model.getReviewers()) {
			for(Review currentReview : currentReviewer.getUnrejectedSecondReviews()) {
				if(!result.contains(currentReview.getReviewer())) {
					result.add(currentReview.getReviewer());
				}
			}
		}
		return result;
	}
}
