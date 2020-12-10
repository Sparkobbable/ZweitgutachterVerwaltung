package controller.statecontrollers;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;
import view.panels.analyse.AnalysisPanel;
import view.widgets.BarChart;
import view.widgets.PieChart;

/**
 * Abstract ApplicationStateController which includes all methods that are
 * needed for every SingleAnalysisStateController. They are divided because they
 * apply to different ApplicationStates. Although they have methods in common.
 * They inherit them from this class.
 */
public abstract class AbstractSingleAnalysisStateController extends AbstractStateController{

	protected ComboBoxMode reviewerFilter;
	
	public AbstractSingleAnalysisStateController(ApplicationState state, View view, Controller controller,
			Model model) {
		super(state, view, controller, model);
		this.reviewerFilter = ComboBoxMode.FIRSTREVIEWER;
		this.registerEvent(EventId.INITIALIZE, (params) -> this.initialize());
	}
	
	/**
	 * Initializes the data set for the selected Reviewer. Gets invoked when the
	 * panel gets opened.
	 */
	private void initialize() {
		this.switchData(this.reviewerFilter);
	}
	
	/**
	 * Switches between different graphics including {@link CollaborationTable}, 
	 * {@link PieChart} and {@link BarChart}within the {@link SingleAnalysisPanel}.
	 * 
	 * @param params - Selected presentationMode from comboBox in
	 *               {@link AnalysisOptionsPanel}
	 */
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

	@Override
	protected void registerEvents() {
		// TODO Auto-generated method stub
		
	}
	
	protected abstract void switchData(ComboBoxMode reviewerFilter);

}
