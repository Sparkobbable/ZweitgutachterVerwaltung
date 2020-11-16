package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;

import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

public class CollaborationOverviewPieChartStateController extends AbstractCollaborationOverviewStateController {

	public CollaborationOverviewPieChartStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.COLLABORATION_PIECHART, view, applicationStateController, model);
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION_FOR_COLLABORATION,
				(params) -> this.switchPresentation((String) params[0].get()));
		this.registerEvent(EventId.CHOOSE_DATA_FOR_COLLABORATION,
				(params) -> switchData((String) params[0].get()));
	}
	
	private void switchPresentation(String params) {

		switch(params) {
		case "Tabelle":
			this.switchState(ApplicationState.COLLABORATION_TABLE);
			break;
		case "Tortendiagramm":
			this.switchState(ApplicationState.COLLABORATION_PIECHART);
			break;
		default:
			throw new IllegalArgumentException("Invalid PresentationMode from ComboBox");
		}
	}
	
	private void switchData(String params) {
		System.out.println("Data:" + params);
		switch(params) {
		case "Nur Erstgutachter":
			this.model.setCollaboratingReviewers(this.getReviewerRatio(this.setCollaborationFirstReviewers()));
			break;
		case "Nur Zweitgutachter":
			this.model.setCollaboratingReviewers(this.getReviewerRatio(this.setCollaborationSecondReviewers()));
			break;
		case "Zweit- & Erstgutachter":
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
				System.out.println(ratio);
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
