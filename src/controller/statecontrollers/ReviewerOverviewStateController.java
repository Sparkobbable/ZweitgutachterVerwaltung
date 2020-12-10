package controller.statecontrollers;

import static model.enums.EventId.DELETE;
import static model.enums.EventId.EDIT;
import static model.enums.EventId.CREATE;
import static model.enums.EventId.SHOW_COLLABORATION;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.commands.base.BatchCommand;
import controller.commands.base.Command;
import controller.commands.model.CreateReviewerCommand;
import controller.commands.model.DeleteReviewerCommand;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.exceptions.SupervisesThesisException;
import util.Log;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_OVERVIEW}
 */
public class ReviewerOverviewStateController extends AbstractStateController {

	public ReviewerOverviewStateController(View view, Controller controller, Model model) {
		super(ApplicationState.REVIEWER_OVERVIEW, view, controller, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EDIT, this::edit);
		this.registerEvent(DELETE, this::delete);
		this.registerEvent(SHOW_COLLABORATION, this::showCollaborations);
		this.registerEvent(CREATE, this::create);
	}

	private void create(Supplier<?>[] params) {
		this.create();
	}

	@SuppressWarnings("unchecked")
	private void showCollaborations(Supplier<?>[] params) {
		this.showCollaborationsForReviewers((List<Reviewer>) params[0].get());
	}

	@SuppressWarnings("unchecked")
	private void delete(Supplier<?>[] params) {
		this.deleteReviewers((List<Reviewer>) params[0].get());
	}

	@SuppressWarnings("unchecked")
	private void edit(Supplier<?>[] params) {
		this.editReviewers((List<Reviewer>) params[0].get());
	}

	private void editReviewers(List<Reviewer> reviewers) {
		Optional<Reviewer> optSelectedReviewer = this.extractSingleSelectedReviewer(reviewers);
		if (optSelectedReviewer.isEmpty()) {
			return;
		}
		this.model.setSelectedReviewer(optSelectedReviewer.get());
		this.switchState(ApplicationState.REVIEWER_EDITOR);
	}
	
	private void create() {
		this.execute(new CreateReviewerCommand(this.model, ApplicationState.REVIEWER_OVERVIEW));
		this.model.setSelectedReviewer(this.model.getNewestReviewer());
		this.switchState(ApplicationState.REVIEWER_EDITOR);
	}

	private void showCollaborationsForReviewers(List<Reviewer> reviewers) {
		Optional<Reviewer> optSelectedReviewer = this.extractSingleSelectedReviewer(reviewers);
		if (optSelectedReviewer.isEmpty()) {
			return;
		}
		this.model.setSelectedReviewer(optSelectedReviewer.get());
		this.switchState(ApplicationState.COLLABORATION_TABLE);
	}

	private void deleteReviewers(List<Reviewer> reviewers) {
		List<Command> deleteCommands = new ArrayList<>();
		reviewers.forEach(r -> deleteCommands.add(new DeleteReviewerCommand(r, this.model, ApplicationState.REVIEWER_OVERVIEW)));
		try {
			this.execute(new BatchCommand(deleteCommands));
		} catch (SupervisesThesisException e) {
			this.popupError(String.format("%s betreut eine Bachelorarbeit als Erstgutachter und kann daher nicht gelöscht werden.", e.getReviewerName()));
		}
		
	}

	private Optional<Reviewer> extractSingleSelectedReviewer(List<Reviewer> selectedReviewers) {
		if (selectedReviewers.size() != 1) {
			Log.warning(this.getClass().getName(), "Only one reviewer can be edited at a time. ");
			return Optional.empty();
		}
		Reviewer selectedReviewer = selectedReviewers.get(0);
		return Optional.of(selectedReviewer);
	}
}
