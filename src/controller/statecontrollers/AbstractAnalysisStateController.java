package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.Controller;
import model.Model;
import model.Pair;
import model.domain.FirstReview;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import model.enums.ReviewStatus;
import view.View;
import view.panels.analyse.AnalysisPanel;
import view.tableModels.AnalysisReviewerTableModel;
import view.tableModels.ReviewerOverviewTableModel;
import view.widgets.BarChart;
import view.widgets.ComboBoxPanel;
import view.widgets.PieChart;

/**
 * Abstract ApplicationStateController which includes all methods that are
 * needed for every AnalysisStateController. They are divided because they apply
 * to different ApplicationStates. Although they have methods in common. They
 * inherit them from this class.
 */
public abstract class AbstractAnalysisStateController extends AbstractStateController {

	protected ComboBoxMode reviewerFilter;

	public AbstractAnalysisStateController(ApplicationState applicationState, View view, Controller controller,
			Model model) {
		super(applicationState, view, controller, model);
		this.reviewerFilter = ComboBoxMode.FIRSTREVIEWER;
		this.registerEvent(EventId.INITIALIZE, (params) -> this.initialize());
	}

	private void initialize() {
		this.switchData(this.reviewerFilter);
	}

	/**
	 * Switches between different graphics including
	 * {@link AnalysisReviewerTableModel}, {@link PieChart} and
	 * {@link BarChart}within the {@link AnalysisPanel}.
	 * 
	 * @param params - Selected presentationMode from comboBox in
	 *               AnalysisOptionsPanel
	 */
	protected void switchPresentation(ComboBoxMode params) {
		switch (params) {
		case TABLE:
			this.switchState(ApplicationState.ANALYSE_TABLE);
			break;
		case PIECHART:
			this.switchState(ApplicationState.ANALYSE_PIECHART);
			break;
		case BARCHART:
			this.switchState(ApplicationState.ANALYSE_BARCHART);
			break;
		case BARCHARTHORIZONTAL:
			this.switchState(ApplicationState.ANALYSE_BARCHARTHORIZONTAL);
			break;
		default:
			throw new IllegalArgumentException("Invalid PresentationMode from ComboBox");
		}
	}

	/**
	 * Switches between different data in percentage which will get presented in the
	 * {@link PieChart}, {@link BarChart} or {@link ReviewerOverviewTableModel}.
	 * 
	 * @param reviewerFilter - selected dataMode from {@link ComboBoxPanel} in
	 *                       {@link AnalysisOptionsPanelOptionsPanel}
	 */
	protected void switchData(ComboBoxMode reviewerFilter) {
		this.reviewerFilter = reviewerFilter;
		if (this.model.getApplicationState() != this.state) {
			return;
		}
		switch (reviewerFilter) {
		case FIRSTREVIEWER:
			this.model.setAnalyseReviewers(this.getReviewCount(this.getAllFirstReviewers()));
			break;
		case SECONDREVIEWER:
			this.model.setAnalyseReviewers(this.getReviewCount(this.getAllSecondReviewers()));
			break;
		case ALLREVIEWER:
			this.model.setAnalyseReviewers(this.getReviewCount(this.getAllReviewers()));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}

	/**
	 * Get all Reviewer, which supervise at least one {@link FirstReview}
	 * 
	 * @return ArrayList<Reviewer>
	 */
	protected ArrayList<Reviewer> getAllFirstReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		for (Reviewer currentReviewer : this.model.getReviewers()) {
			for (Review currentReview : currentReviewer.getFirstReviews()) {
				if (!result.contains(currentReviewer)) {
					result.add(currentReview.getReviewer());
				}
			}
		}
		return result;
	}

	/**
	 * Get all Reviewer, which supervise at least one {@link SecondReview}
	 * 
	 * @return ArrayList<Reviewer>
	 */
	protected ArrayList<Reviewer> getAllSecondReviewers() {
		ArrayList<Reviewer> result1 = new ArrayList<>();
		;
		for (Reviewer currentReviewer : this.model.getReviewers()) {
			for (SecondReview currentReview : currentReviewer.getUnrejectedSecondReviews()) {
				if (currentReview.getStatus() == ReviewStatus.APPROVED) {
					if (!result1.contains(currentReviewer)) {
						result1.add(currentReview.getReviewer());
					}
				}
			}
		}
		return result1;
	}

	/**
	 * Get all Reviewer, which supervise at least one {@link Review}
	 * 
	 * @return ArrayList<Reviewer>
	 */
	protected ArrayList<Reviewer> getAllReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		result.addAll(this.getAllFirstReviewers());
		result.addAll(this.getAllSecondReviewers());
		return result;
	}

	/**
	 * Count the amount of supervised {@link FirstReview} & {@link SecondReview} and
	 * create the corresponding data structure
	 * 
	 * @param List<Reviewer reviewers
	 * @return Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> ReviewCount
	 */
	protected Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> getReviewCount(List<Reviewer> reviewers) {
		HashMap<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> result = new HashMap<>();
		switch (this.reviewerFilter) {
		case FIRSTREVIEWER:
			reviewers.forEach(reviewer -> result.put(reviewer,
					(new Pair<Optional<Integer>, Optional<Integer>>(Optional.of(reviewer.getFirstReviewCount()),
							Optional.empty()))));
			break;
		case SECONDREVIEWER:
			reviewers.forEach(
					reviewer -> result.put(reviewer, new Pair<Optional<Integer>, Optional<Integer>>(Optional.empty(),
							Optional.of(reviewer.getApprovedSecondReviewCount()))));
			break;
		case ALLREVIEWER:
			reviewers.forEach(reviewer -> result.put(reviewer,
					new Pair<Optional<Integer>, Optional<Integer>>(Optional.of(reviewer.getFirstReviewCount()),
							Optional.of(reviewer.getApprovedSecondReviewCount()))));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode for currentDataStatus");
		}
		return result;
	}
}
