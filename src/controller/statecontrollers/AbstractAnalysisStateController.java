package controller.statecontrollers;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

public abstract class AbstractAnalysisStateController extends AbstractStateController {

	protected String reviewerFilter;
	
	public AbstractAnalysisStateController(ApplicationState applicationState,
			View view, Controller controller, Model model) {
		super(applicationState, view, controller, model);
		this.registerEvent(EventId.INITIALIZE, (params) -> this.initialize());
	}
	
	private void initialize() {
		this.switchData(this.reviewerFilter);
	}
	
	protected void switchPresentation(String params) {
		switch(params) {
		case "Tabelle":
			this.switchState(ApplicationState.ANALYSE_TABLE);
			break;
		case "Tortendiagramm":
			this.switchState(ApplicationState.ANALYSE_PIECHART);
			break;
		case "Balkendiagramm":
			this.switchState(ApplicationState.ANALYSE_BARCHART);
			break;
		default:
			throw new IllegalArgumentException("Invalid PresentationMode from ComboBox");
		}
	}
	
	protected abstract void switchData(String reviewerFilter);

}
