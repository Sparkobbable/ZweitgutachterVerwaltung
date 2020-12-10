package controller.statecontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.Controller;
import model.Model;
import model.Pair;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import model.enums.ReviewStatus;
import view.View;
import view.panels.analyse.AnalysisPanel;
import view.widgets.BarChart;
import view.widgets.PieChart;

/**
 * Abstract ApplicationStateController which includes all methods that are
 * needed for every AnalysisStateController. They are divided because they
 * apply to different ApplicationStates. Although they have methods in common.
 * They inherit them from this class.
 */
public abstract class AbstractAnalysisStateController extends AbstractStateController {

	protected ComboBoxMode reviewerFilter;
	protected ComboBoxMode currentDataStatus;
	
	public AbstractAnalysisStateController(ApplicationState applicationState,
			View view, Controller controller, Model model) {
		super(applicationState, view, controller, model);
		this.reviewerFilter = ComboBoxMode.FIRSTREVIEWER;
		this.currentDataStatus = ComboBoxMode.FIRSTREVIEWER;
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
	 * {@link PieChart} and {@link BarChart}within the {@link AnalysisPanel}.
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
		case BARCHARTHORIZONTAL:
			this.switchState(ApplicationState.ANALYSE_BARCHARTHORIZONTAL);
			break;
		default:
			throw new IllegalArgumentException("Invalid PresentationMode from ComboBox");
		}
	}
	
	protected abstract void switchData(ComboBoxMode reviewerFilter);

	protected ArrayList<Reviewer> getAllFirstReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		for(Reviewer currentReviewer : this.model.getReviewers()) {
			for(Review currentReview : currentReviewer.getFirstReviews()) {
				if(!result.contains(currentReviewer)) {
					result.add(currentReview.getReviewer());
				}
			}
		}
		return result;
	}
	
	protected ArrayList<Reviewer> getAllSecondReviewers() {
		ArrayList<Reviewer> result1 = new ArrayList<>();;
		for(Reviewer currentReviewer : this.model.getReviewers()) {
			for(SecondReview currentReview : currentReviewer.getUnrejectedSecondReviews()) {
				if(currentReview.getStatus() == ReviewStatus.APPROVED) {
					if(!result1.contains(currentReviewer)) {
						result1.add(currentReview.getReviewer());
					}
				}
			}
		}
		return result1;
	}
	
	protected ArrayList<Reviewer> getAllReviewers() {
		ArrayList<Reviewer> result = new ArrayList<>();
		result.addAll(this.getAllFirstReviewers());
		result.addAll(this.getAllSecondReviewers());
		return result;
	}
	
	protected Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> getReviewCount(List<Reviewer> reviewers) {
		HashMap<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> result = new HashMap<>();
		switch(this.currentDataStatus) {
		case FIRSTREVIEWER:
			reviewers.forEach(reviewer -> result.put(reviewer, 
					(new Pair<Optional<Integer>, Optional<Integer>>(Optional.of(reviewer.getFirstReviewCount()), Optional.empty()))));
			break;
		case SECONDREVIEWER:
			reviewers.forEach(reviewer -> result.put(reviewer, 
					new Pair<Optional<Integer>, Optional<Integer>>(Optional.empty(), Optional.of(reviewer.getApprovedSecondReviewCount()))));
			break;
		case ALLREVIEWER:
			reviewers.forEach(reviewer -> result.put(reviewer, 
					new Pair<Optional<Integer>, Optional<Integer>>(Optional.of(reviewer.getFirstReviewCount()), Optional.of(reviewer.getApprovedSecondReviewCount()))));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode for currentDataStatus");
		}
		return result;
	}
}
