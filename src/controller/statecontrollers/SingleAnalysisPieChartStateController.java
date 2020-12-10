package controller.statecontrollers;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import view.View;

public class SingleAnalysisPieChartStateController extends AbstractSingleAnalysisStateController {

	public SingleAnalysisPieChartStateController(View view, Controller controller, Model model) {
		super(ApplicationState.SINGLE_ANALYSIS_PIECHART, view, controller, model);
	}
	
	@Override
	protected void registerEvents() {
	}

	@Override
	protected void switchData(ComboBoxMode reviewerFilter) {
		// TODO Auto-generated method stub
		
	}

}
