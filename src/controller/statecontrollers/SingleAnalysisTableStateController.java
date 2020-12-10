package controller.statecontrollers;


import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;

public class SingleAnalysisTableStateController extends AbstractSingleAnalysisStateController {


	public SingleAnalysisTableStateController(View view, Controller controller, Model model) {
		super(ApplicationState.SINGLE_ANALYSIS_TABLE, view, controller, model);
		
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION, 
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.CHOOSE_REVIEW_FILTER, 
				(params) -> this.switchData((ComboBoxMode) params[0].get()));
	}
}
