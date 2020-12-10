package controller.statecontrollers;

import controller.Controller;
import model.Model;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;
import view.tableModels.ReviewerOverviewTableModel;
import view.widgets.ComboBoxPanel;
import view.widgets.PieChart;

public class AnalysisBarChartHorizontalStateController extends AbstractAnalysisStateController {

	public AnalysisBarChartHorizontalStateController(View view, Controller controller, Model model) {
		super(ApplicationState.ANALYSE_BARCHARTHORIZONTAL, view, controller, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION, 
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.CHOOSE_REVIEW_FILTER, 
				(params) -> this.switchData((ComboBoxMode) params[0].get()));	
	}
}
