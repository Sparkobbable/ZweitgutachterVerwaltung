package controller.statecontrollers;

import static model.enums.EventId.SAVE_THESIS;

import java.util.function.Supplier;

import model.Model;
import model.data.BachelorThesis;
import model.enums.ApplicationState;
import view.View;

public class ThesisEditorStateController extends AbstractStateController {

	public ThesisEditorStateController(ApplicationState state, View view,
			ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.THESIS_EDITOR, view, applicationStateController, model);
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(SAVE_THESIS, (params) -> saveThesis(params));
	}

	private void saveThesis(Supplier<?>[] params) {
		BachelorThesis thesis = (BachelorThesis) params[0].get();
		this.model.getSelectedReviewer().get().addBachelorThesis(thesis);
		switchState(ApplicationState.REVIEWER_EDITOR);
	}

}
