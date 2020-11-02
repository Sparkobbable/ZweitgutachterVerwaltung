package view;

import java.util.HashMap;
import java.util.Map;

import model.Action;
import model.EventSource;
import model.Model;
import model.data.CompositeEventSource;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.enums.ViewId;
import view.editor.ReviewerEditor;
import view.table.OverviewTable;

// TODO JavaDoc
public class View implements EventSource{

	private Map<ApplicationState, AbstractView> viewsByApplicationStates;
	private CompositeEventSource eventSourceHandler;
	private Model model;
	
	private MainWindow window;
	private MenuBarHandler menuHandler;

	public View(Model model) {
		this.model = model;
		this.window = new MainWindow();
		this.menuHandler = new MenuBarHandler();
		this.eventSourceHandler = new CompositeEventSource();
		this.eventSourceHandler.register(this.menuHandler);
		this.createViews();

	}

	private void createViews() {
		this.viewsByApplicationStates = new HashMap<>();

		this.registerView(ApplicationState.HOME, new HomePanel(ViewId.HOME));
		this.registerView(ApplicationState.REVIEWER_OVERVIEW, new OverviewTable(ViewId.OVERVIEW_TABLE, model));
		this.registerView(ApplicationState.REVIEWER_EDITOR, new ReviewerEditor(ViewId.EDITOR, "Dozenteneditor", model));
	}

	private void registerView(ApplicationState applicationState, AbstractView abstractView) {
		this.viewsByApplicationStates.put(applicationState, abstractView);
		this.eventSourceHandler.register(abstractView);
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
		menuHandler.init();
		window.init();
		window.setJMenuBar(menuHandler);
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

	/**
	 * @return this for linguistically meaningful API calls
	 */
	public View atAnyState() {
		return this;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * -- | Delegate methods to the responsible Objects
	 * -----------------------------------------------------------------------------
	 * --
	 */

	@Override
	public void addEventHandler(EventId eventId, Action action) {
		this.eventSourceHandler.addEventHandler(eventId, action);
	}

	@Override
	public boolean canOmit(EventId eventId) {
		return this.eventSourceHandler.canOmit(eventId);
	}


}
