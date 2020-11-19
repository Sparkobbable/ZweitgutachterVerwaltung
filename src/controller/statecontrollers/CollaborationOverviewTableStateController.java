package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;

import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

public class CollaborationOverviewTableStateController extends AbstractCollaborationOverviewStateController {

	public CollaborationOverviewTableStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.COLLABORATION_TABLE, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION_FOR_COLLABORATION,
				(params) -> this.switchPresentation((String) params[0].get()));
		this.registerEvent(EventId.CHOOSE_DATA_FOR_COLLABORATION,
				(params) -> switchData((String) params[0].get()));
//		this.registerEvent(EventId.SHOW_COLLABORATION, 
//				(params) -> initialize());
	}
	
	@Override
	protected void initialize() {
		this.switchPresentation("Tabelle");
		this.switchData("Nur Erstgutachter");
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
			this.model.setCollaboratingReviewers(this.getReviewerCount(this.setCollaborationFirstReviewers()));
			break;
		case "Nur Zweitgutachter":
			this.model.setCollaboratingReviewers(this.getReviewerCount(this.setCollaborationSecondReviewers()));
			break;
		case "Zweit- & Erstgutachter":
			this.model.setCollaboratingReviewers(this.getReviewerCount(this.setCollaborationAllReviewers()));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}

	private HashMap<Reviewer, Double> getReviewerCount(ArrayList<Reviewer> reviewers) {
		HashMap<Reviewer, Double> result = new HashMap<>();
		
		for(Reviewer currentReviewer : reviewers) {
			if(!result.containsKey(currentReviewer)) {
				System.out.println(this.countReviewerperArrayList(reviewers, currentReviewer));
				result.put(currentReviewer, this.countReviewerperArrayList(reviewers, currentReviewer));
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
