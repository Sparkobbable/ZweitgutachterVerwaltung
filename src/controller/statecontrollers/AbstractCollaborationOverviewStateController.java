package controller.statecontrollers;

import java.util.ArrayList;
import java.util.Optional;

import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.enums.PresentationMode;
import view.View;

public abstract class AbstractCollaborationOverviewStateController extends AbstractStateController {

	public AbstractCollaborationOverviewStateController(ApplicationState applicationState, View view, ApplicationStateController applicationStateController,
			Model model) {
		super(applicationState, view, applicationStateController, model);
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION_FOR_COLLABORATION,
				(params) -> switchPresentation((PresentationMode) params[0].get()));
	}
	
	private void switchPresentation(PresentationMode params) {

		if (params == PresentationMode.PIECHART) {
			switchState(ApplicationState.COLLABORATION_PIECHART);
		}
		if (params == PresentationMode.TABLE) {
			switchState(ApplicationState.COLLABORATION_TABLE);
		}
	}
	
	public ArrayList<Reviewer> getSecondReviewersforReviewer() {
		ArrayList<Reviewer> result = new ArrayList<>();
		Optional<Reviewer> optionalReviewer = this.model.getSelectedReviewer();
		if(optionalReviewer.isEmpty()) {
			return result;
		} else {
			Reviewer currentReviewer = optionalReviewer.get();
			ArrayList<BachelorThesis> supervisedTheses = currentReviewer.getSupervisedThesis();

			for(BachelorThesis thesis : supervisedTheses) {
				
				if(thesis.getFirstReview().isPresent()) {
					
					Reviewer rev = thesis.getFirstReview().get().getReviewer();
					if(rev.equals(currentReviewer) && thesis.getSecondReview().isPresent()) {
						result.add(thesis.getFirstReview().get().getReviewer());
					} 
				}
			}
			return result;
		}
	}
	
	public ArrayList<Reviewer> getFirstReviewersforReviewer() {
		ArrayList<Reviewer> result = new ArrayList<>();
		Optional<Reviewer> optionalReviewer = this.model.getSelectedReviewer();
		if(optionalReviewer.isEmpty()) {
			return result;
		} else {
			Reviewer currentReviewer = optionalReviewer.get();
			ArrayList<BachelorThesis> supervisedTheses = currentReviewer.getSupervisedThesis();

			for(BachelorThesis thesis : supervisedTheses) {
				
				if(thesis.getFirstReview().isPresent()) {
					
					Reviewer rev = thesis.getFirstReview().get().getReviewer();
					if(!rev.equals(currentReviewer) && 
							thesis.getSecondReview().isPresent()) {
						result.add(rev);
					}
				}
			}
			return result;
		}
	}
	
	public ArrayList<Reviewer> getFirstandSecondReviewersforReviewer() {
		ArrayList<Reviewer> result = new ArrayList<>();
		Optional<Reviewer> optionalReviewer = this.model.getSelectedReviewer();
		if(optionalReviewer.isEmpty()) {
			return result;
		} else {
			Reviewer currentReviewer = optionalReviewer.get();
			ArrayList<BachelorThesis> supervisedTheses = currentReviewer.getSupervisedThesis();

			for(BachelorThesis thesis : supervisedTheses) {
				
				if(thesis.getFirstReview().isPresent()) {
					
					Reviewer rev = thesis.getFirstReview().get().getReviewer();
					if(rev.equals(currentReviewer)) {
						
						if(thesis.getSecondReview().isPresent()) {
							result.add(thesis.getFirstReview().get().getReviewer());
						}
					} else {
						result.add(rev);
					}
				}
			}
			return result;
		}
	}
}
