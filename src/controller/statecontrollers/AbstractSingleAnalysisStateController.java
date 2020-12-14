package controller.statecontrollers;

import java.util.Optional;

import controller.Controller;
import model.Model;
import model.Pair;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;
import view.panels.analyse.AnalysisOptionsPanel;
import view.panels.analyse.SingleAnalysisPanel;
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
			this.switchState(ApplicationState.SINGLE_ANALYSIS_TABLE);
			break;
		case PIECHART:
			this.switchState(ApplicationState.SINGLE_ANALYSIS_PIECHART);
			break;
		case BARCHART:
			this.switchState(ApplicationState.SINGLE_ANALYSIS_BARCHART);
			break;
		default:
			throw new IllegalArgumentException("Invalid PresentationMode from ComboBox");
		}
	}
	
	protected void switchData(ComboBoxMode reviewerFilter) {
		this.reviewerFilter = reviewerFilter;
		if (this.model.getApplicationState() != this.state) {
			return;
		}
		switch (reviewerFilter) {
		case FIRSTREVIEWER:
			this.model.setSingleReviews(this.getReviewCount(this.model.getSelectedReviewer()));
			break;
		case SECONDREVIEWER:
			this.model.setSingleReviews(this.getReviewCount(this.model.getSelectedReviewer()));
			break;
		case ALLREVIEWER:
			this.model.setSingleReviews(this.getReviewCount(this.model.getSelectedReviewer()));
			break;
		default:
			throw new IllegalArgumentException("Invalid DataMode from ComboBox");
		}
	}
	
	protected Pair<Optional<Integer>, Optional<Integer>> getReviewCount(Optional<Reviewer> optional) {
		if(optional.isPresent()) {
			Reviewer reviewer = optional.get();
			switch(this.reviewerFilter) {
			case FIRSTREVIEWER:
				return new Pair<Optional<Integer>, Optional<Integer>>(Optional.of(reviewer.getFirstReviewCount()), Optional.empty());
			case SECONDREVIEWER:
				return new Pair<Optional<Integer>, Optional<Integer>>(Optional.empty(), Optional.of(reviewer.getApprovedSecondReviewCount()));
			case ALLREVIEWER:
				return new Pair<Optional<Integer>, Optional<Integer>>(Optional.of(reviewer.getFirstReviewCount()), Optional.of(reviewer.getApprovedSecondReviewCount()));
			default:
				throw new IllegalArgumentException("Invalid DataMode for currentDataStatus");
			}
		} else {
			return new Pair<Optional<Integer>, Optional<Integer>>(Optional.empty(), Optional.empty());
		}
	}
}
