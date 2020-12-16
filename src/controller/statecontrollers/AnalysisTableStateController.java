package controller.statecontrollers;

import java.util.Optional;

import controller.Controller;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.ComboBoxMode;
import model.enums.EventId;
import view.View;

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

	private void showSingleAnalysis(Reviewer reviewer) {
		Optional<Reviewer> optSelectedReviewer = Optional.of(reviewer);
		if(optSelectedReviewer.isEmpty()) {
			return;
		}
		this.model.setSelectedReviewer(reviewer);
		this.switchState(ApplicationState.SINGLE_ANALYSIS_TABLE);
	}
}
