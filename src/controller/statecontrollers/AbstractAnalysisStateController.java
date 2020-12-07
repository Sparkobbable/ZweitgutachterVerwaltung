package controller.statecontrollers;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;

public abstract class AbstractAnalysisStateController extends AbstractStateController {

	protected ComboBoxMode reviewerFilter;
	
	public AbstractAnalysisStateController(ApplicationState applicationState,
			View view, Controller controller, Model model) {
		super(applicationState, view, controller, model);
		this.reviewerFilter = ComboBoxMode.FIRSTREVIEWER;
		this.registerEvent(EventId.INITIALIZE, (params) -> this.initialize());
	}
	
	private void initialize() {
		this.switchData(this.reviewerFilter);
	}
	
	protected void switchPresentation(ComboBoxMode params) {
		switch(params) {
		case TABLE:
			this.switchState(ApplicationState.ANALYSE_TABLE);
			break;
		case PIECHART:
			this.switchState(ApplicationState.ANALYSE_PIECHART);
			break;
		case BARCHART:
			this.switchState(ApplicationState.ANALYSE_BARCHART);
			break;
		default:
			throw new IllegalArgumentException("Invalid PresentationMode from ComboBox");
		}
	}
	
	protected abstract void switchData(ComboBoxMode reviewerFilter);

}
