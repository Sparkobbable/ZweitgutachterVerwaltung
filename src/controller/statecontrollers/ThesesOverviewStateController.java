package controller.statecontrollers;

import static model.enums.EventId.SELECT;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.Controller;
import controller.commands.base.BatchCommand;
import controller.commands.base.Command;
import controller.commands.reviewer.AddBachelorThesisCommand;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import util.Log;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ThesesOverviewStateController extends AbstractStateController {

	public ThesesOverviewStateController(View view, Controller controller, Model model) {
		super(ApplicationState.THESES_OVERVIEW, view, controller, model);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerEvents() {
		this.registerEvent(SELECT,
				(params) -> addSecondReviewer((List<BachelorThesis>) params[0].get(), (Reviewer) params[1].get()));
		this.registerEvent(EventId.NAVIGATE, (params) -> navigate((Optional<Reviewer>) params[0].get()));
	}

	private void navigate(Optional<Reviewer> reviewer) {
		if (reviewer.isPresent()) {
			this.model.setSelectedReviewer(reviewer.get());
			this.switchState(ApplicationState.REVIEWER_EDITOR);
		}
	}

	private void addSecondReviewer(List<BachelorThesis> selectedTheses, Reviewer reviewer) {

		if (selectedTheses.stream().map(BachelorThesis::getSecondReview).anyMatch(Optional::isPresent)) {
			// geht net
			Log.warning(this, "One or more BachelorTheses already have a second Reviewer.");
			return;
		}

		if (reviewer.getMaxSupervisedThesis() - reviewer.getTotalReviewCount() < selectedTheses.size()) {
			// geht net
			Log.warning(this, "Max occupation reached.");
			return;
		}

		List<Command> commands = selectedTheses.stream()
				.map(bt -> new AddBachelorThesisCommand(reviewer, bt, ApplicationState.THESES_OVERVIEW))
				.collect(Collectors.toList());
		this.execute(new BatchCommand(commands));
	}
}
