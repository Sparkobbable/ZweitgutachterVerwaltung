package controller.statecontrollers;

import model.Model;
import model.enums.ApplicationState;
import view.View;

public class CollaborationOverviewTableStateController extends AbstractCollaborationOverviewStateController {

	public CollaborationOverviewTableStateController(View view, ApplicationStateController applicationStateController,
			Model model) {
		super(ApplicationState.COLLABORATION_TABLE, view, applicationStateController, model);
	}


}
