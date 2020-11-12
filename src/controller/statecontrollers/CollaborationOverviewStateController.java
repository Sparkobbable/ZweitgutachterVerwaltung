package controller.statecontrollers;

import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.enums.PresentationMode;
import view.View;

public class CollaborationOverviewStateController extends AbstractStateController {

	public CollaborationOverviewStateController(View view, ApplicationStateController applicationStateController,
			Model model, ApplicationState applicationState) {
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

}
