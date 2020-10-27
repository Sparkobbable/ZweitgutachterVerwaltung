package controller.statecontrollers;

import java.util.function.Supplier;

import controller.Controller;
import model.Action;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#REVIEWER_EDITOR}
 */
public class ReviewerEditorStateController extends AbstractStateController {

	public ReviewerEditorStateController(View view,
			ApplicationStateController applicationStateController, Model data) {
		super(ApplicationState.REVIEWER_EDITOR, view, applicationStateController, data);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.VALUE_ENTERED, (value) -> updateName(value));
	}

	private void updateName(Supplier<?>[] value) {
		String newName = (String) value[0].get();
		// TODO update name
	}

}
