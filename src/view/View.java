package view;

import java.util.HashMap;
import java.util.Map;

import model.Model;
import model.enums.ApplicationState;
import model.enums.ViewId;
import view.editor.ReviewerEditor;
import view.table.OverviewTable;

// TODO JavaDoc
public class View {

	private Map<ApplicationState, AbstractView> viewsByApplicationStates;
	private Model data; // TODO change to Model model ?!
	private MainWindow window;

	public View(Model data) {
		this.data = data;
		this.window = new MainWindow();
		this.createViews();

	}

	private void createViews() {
		this.viewsByApplicationStates = new HashMap<>();
		this.registerView(ApplicationState.HOME, new HomePanel(ViewId.HOME));
		this.registerView(ApplicationState.REVIEWER_OVERVIEW, new OverviewTable(ViewId.OVERVIEW_TABLE, data));
		this.registerView(ApplicationState.REVIEWER_EDITOR, new ReviewerEditor(ViewId.EDITOR, "Dozenteneditor"));
		this.registerView(ApplicationState.JSON_CHOOSER, new JsonChooserPanel(ViewId.JSON_CHOOSER));
	}

	private void registerView(ApplicationState applicationState, AbstractView abstractView) {
		this.viewsByApplicationStates.put(applicationState, abstractView);
		this.window.registerView(abstractView);
	}

	/**
	 * [Initializes the views and] shows the window.
	 */
	public void setVisible() {
		// TODO remove init or leave it here and rename the method
		init();
		window.setVisible(true);
	}

	/**
	 * initializes all view components
	 */
	private void init() {
		viewsByApplicationStates.values().forEach(AbstractView::init);
		window.init();
	}



	public void switchState(ApplicationState state) {
		window.switchToView(viewsByApplicationStates.get(state).getViewId());
	}

	/**
	 * 
	 * @param state
	 * @return A view of the view responsible for handling that state
	 */
	//TODO return view of view instead of whole object?
	public AbstractView assumeState(ApplicationState state) {
		return viewsByApplicationStates.get(state);
	}
	
	/**
	 * 
	 * @param state
	 * @return A (different) view of the view responsible for handling that state
	 */
	//TODO return view of view instead of whole object?
	public AbstractView atState(ApplicationState state) {
		return viewsByApplicationStates.get(state);
	}

}
