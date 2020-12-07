package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Controller;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;
import view.panels.collaboration.CollaborationOptionsPanel;
import view.panels.collaboration.CollaborationTable;
import view.panels.collaboration.PieChart;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#COLLABORATION_TABLE}
 */
public class CollaborationOverviewTableStateController extends AbstractCollaborationOverviewStateController {

	public CollaborationOverviewTableStateController(View view, Controller controller,
			Model model) {
		super(ApplicationState.COLLABORATION_TABLE, view, controller, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION_FOR_COLLABORATION,
				(params) -> this.switchPresentation((String) params[0].get()));
		this.registerEvent(EventId.CHOOSE_DATA_FOR_TABLE,
				(params) -> switchData((String) params[0].get()));
		this.registerEvent(EventId.INITIALIZE,
				(params) -> this.initialize());
	}
	
	/**
	 * Initializes the data set for the selected Reviewer.
	 * Gets invoked when the panel gets opened.
	 */
	protected void initialize() {
		this.switchData("Nur Erstgutachter");
	}
	
	/**
	 * Switches between different data in counted which
	 * will get presented in the {@link CollaborationTable} or {@link PieChart}.
	 * 
	 * @param params - selected dataMode from comboBox in {@link CollaborationOptionsPanel}
	 */
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
