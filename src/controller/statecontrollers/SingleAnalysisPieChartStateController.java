package controller.statecontrollers;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;

public class SingleAnalysisPieChartStateController extends AbstractSingleAnalysisStateController {

	public SingleAnalysisPieChartStateController(View view, Controller controller, Model model) {
		super(ApplicationState.SINGLE_ANALYSIS_PIECHART, view, controller, model);
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION, 
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.CHOOSE_REVIEW_FILTER, 
				(params) -> this.switchData((ComboBoxMode) params[0].get()));
	}
}
