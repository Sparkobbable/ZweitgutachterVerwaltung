package controller.statecontrollers;

import java.util.Optional;

import controller.Controller;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;
import view.tableModels.ReviewerOverviewTableModel;
import view.widgets.ComboBoxPanel;
import view.widgets.PieChart;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#ANALYSE_TABLE}
 */
public class AnalysisTableStateController extends AbstractAnalysisStateController {

	public AnalysisTableStateController(View view, Controller controller,
			Model model) {
		super(ApplicationState.ANALYSE_TABLE, view, controller, model);
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.CHOOSE_PRESENTATION, 
				(params) -> this.switchPresentation((ComboBoxMode) params[0].get()));
		this.registerEvent(EventId.CHOOSE_REVIEW_FILTER, 
				(params) -> this.switchData((ComboBoxMode) params[0].get()));	
		this.registerEvent(EventId.SELECT, 
				(params) -> this.showSingleAnalysis((Reviewer) params[0].get()));
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
			this.currentDataStatus = ComboBoxMode.FIRSTREVIEWER;
			this.model.setAnalyseReviewers(this.getReviewCount(this.getAllFirstReviewers()));
			break;
		case SECONDREVIEWER:
			this.currentDataStatus = ComboBoxMode.SECONDREVIEWER;
			this.model.setAnalyseReviewers(this.getReviewCount(this.getAllSecondReviewers()));
			break;
		case ALLREVIEWER:
			this.currentDataStatus = ComboBoxMode.ALLREVIEWER;
			this.model.setAnalyseReviewers(this.getReviewCount(this.getAllReviewers()));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}
	
	private void showSingleAnalysis(Reviewer reviewer) {
		Optional<Reviewer> optSelectedReviewer = Optional.of(reviewer);
		if(optSelectedReviewer.isEmpty()) {
			return;
		}
		this.model.setSelectedReviewer(reviewer);
		this.switchState(ApplicationState.SINGLE_ANALYSIS_TABLE);
	}
}
