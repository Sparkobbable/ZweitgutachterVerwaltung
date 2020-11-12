package controller.statecontrollers;

import java.util.logging.Logger;

import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.enums.PresentationMode;
import view.View;

public class CollaborationOverviewStateController extends AbstractStateController {

	public CollaborationOverviewStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.COLLABORATION, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION_FOR_COLLABORATION, (params) -> switchPresentation((PresentationMode) params[0].get()));
		
	}
	
	private void switchPresentation(PresentationMode params) {
		
		System.out.println("test");
		PresentationMode selectedPresentationMode = params;
		
		
		
	}

}
