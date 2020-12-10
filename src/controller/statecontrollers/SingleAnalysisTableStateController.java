package controller.statecontrollers;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import view.View;

public class SingleAnalysisTableStateController extends AbstractSingleAnalysisStateController {

	public SingleAnalysisTableStateController(View view, Controller controller, Model model) {
		super(ApplicationState.SINGLE_ANALYSIS_TABLE, view, controller, model);
		
	}
	
	@Override
	protected void registerEvents() {
	}

	@Override
	protected void switchData(ComboBoxMode reviewerFilter) {
		// TODO Auto-generated method stub
		
	}

}
