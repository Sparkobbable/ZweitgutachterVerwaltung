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
		this.registerEvent(EventId.ANALYSIS_CHOOSE_PRESENTATION, 
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.ANALYSIS_CHOOSE_DATA, 
				(params) -> this.switchData((ComboBoxMode) params[0].get()));	
	}
	
	/**
	 * Switches between different data in percentage which
	 * will get presented in the {@link PieChart}, {@link BarChart} or {@link ReviewerOverviewTableModel}.
	 * 
	 * @param reviewerFilter - selected dataMode from {@link ComboBoxPanel} in {@link AnalysisOptionsPanelOptionsPanel}
	 */
	@Override
	protected void switchData(ComboBoxMode reviewerFilter) {
		this.reviewerFilter = reviewerFilter;
		if (this.model.getApplicationState() != this.state) {
			System.out.println("skip");
			return;
		}
		System.out.println("Data:" + reviewerFilter);
		switch (reviewerFilter) {
		case FIRSTREVIEWER:
			this.model.setAnalyseReviewers(this.getAllFirstReviewers());
			break;
		case SECONDREVIEWER:
			this.model.setAnalyseReviewers(this.getAllSecondReviewers());
			break;
		case ALLREVIEWER:
			this.model.setAnalyseReviewers(this.model.getReviewers());
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}
}
