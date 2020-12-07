package controller.statecontrollers;

import static model.enums.EventId.SELECT;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.commands.base.BatchCommand;
import controller.commands.base.Command;
import controller.commands.reviewer.AddBachelorThesisCommand;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Review;
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

		if (selectedTheses.isEmpty()) {
			this.view.alert("Keine Bachelor Theses ausgewählt", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (selectedTheses.stream().map(BachelorThesis::getSecondReview).anyMatch(Optional::isPresent)) {
			this.view.alert("Mindestens eine Bachelorarbeit hat bereits einen Reviewer", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (selectedTheses.stream().anyMatch(reviewer.getRejectedSecondReviews().stream().map(Review::getBachelorThesis)
				.collect(Collectors.toList())::contains)) {
			this.view.alert("Der Reviewer hat mindestens eine Bachelorarbeit abgelehnt", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (reviewer.getMaxSupervisedThesis() - reviewer.getTotalReviewCount() < selectedTheses.size()) {
			this.view.alert("Diese Aktion überschreitet die Maximalanzahl an Gutachten für diesen Reviwer",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (selectedTheses.stream().map(BachelorThesis::getFirstReview).map(Review::getReviewer)
				.anyMatch(firstReviewer -> reviewer == firstReviewer)) {
			this.view.alert("Erst- und Zweitgutachter können nicht identisch sein.",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		List<Command> commands = selectedTheses.stream()
				.map(bt -> new AddBachelorThesisCommand(reviewer, bt, ApplicationState.THESES_OVERVIEW))
				.collect(Collectors.toList());
		this.execute(new BatchCommand(commands));
	}
}
