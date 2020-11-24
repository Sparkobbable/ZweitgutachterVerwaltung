package view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import controller.events.Action;
import controller.events.CompositeEventSource;
import controller.events.EventSource;
import controller.propertychangelistener.PropertyChangeManager;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.collaboration.CollaborationPanel;
import view.editor.ReviewerEditorPanel;
import view.editor.ThesisAssignmentPanel;
import view.overview.ReviewerOverviewPanel;
import view.overview.ThesesOverviewPanel;
import view.panelstructure.AbstractViewPanel;

// TODO JavaDoc
public class View implements EventSource {

	private Map<ApplicationState, AbstractViewPanel> viewsByApplicationStates;
	private CompositeEventSource eventSourceHandler;
	private Model model;

	private MainWindow window;
	private MenuBarHandler menuHandler;
	private PropertyChangeManager propertyChangeManager;
	
	//Color palette
	public static final Color button = new Color(62, 76, 84);

	public View(Model model) {
		this.model = model;
		this.window = new MainWindow();
		this.menuHandler = new MenuBarHandler();
		this.eventSourceHandler = new CompositeEventSource();
		this.eventSourceHandler.register(this.menuHandler);
		this.createViews();
		window.setJMenuBar(menuHandler);

		this.propertyChangeManager = new PropertyChangeManager();
		this.propertyChangeManager.onPropertyChange(Model.APPLICATION_STATE,
				(evt) -> switchState((ApplicationState) evt.getOldValue(), (ApplicationState) evt.getNewValue()));
		this.model.addPropertyChangeListener(propertyChangeManager);
	}

	private void createViews() {
		this.viewsByApplicationStates = new HashMap<>();

		this.registerView(ApplicationState.HOME, new HomePanel());
		this.registerView(ApplicationState.REVIEWER_OVERVIEW, new ReviewerOverviewPanel(model));
		this.registerView(ApplicationState.THESES_OVERVIEW, new ThesesOverviewPanel(model));
		this.registerView(ApplicationState.REVIEWER_EDITOR, new ReviewerEditorPanel(model));
		this.registerView(ApplicationState.FIRSTREVIEWER_IMPORT, new ImportfirstrewierPanel());
		this.registerView(ApplicationState.THESIS_ASSIGNMENT, new ThesisAssignmentPanel(model));
		this.registerView(ApplicationState.STATE_CHOOSER, new StateChooserPanel());

		CollaborationPanel collabPanel = new CollaborationPanel(model);
		this.registerView(ApplicationState.COLLABORATION_TABLE, collabPanel.atState(ViewState.TABLE));
		this.registerView(ApplicationState.COLLABORATION_PIECHART, collabPanel.atState(ViewState.PIECHART));

	}

	private void registerView(ApplicationState applicationState, AbstractViewPanel abstractView) {
		this.viewsByApplicationStates.put(applicationState, abstractView);
		this.eventSourceHandler.register(abstractView);
		this.window.registerView(abstractView);
	}

	/**
	 * Shows the window.
	 */
	public void setVisible() {
		window.setVisible(true);
	}

	private void switchState(ApplicationState oldState, ApplicationState newState) {
		viewsByApplicationStates.get(newState).prepare();
		window.switchToView(viewsByApplicationStates.get(newState).getViewId());
	}

	// TODO rmv
	/**
	 * 
	 * @param state
	 * @return A view of the view responsible for handling that state
	 */
	public AbstractViewPanel assumeState(ApplicationState state) {
		return viewsByApplicationStates.get(state);
	}

	/**
	 * 
	 * @param state
	 * @return A (different) view of the view responsible for handling that state
	 */
	// TODO return view of view instead of whole object?
	public AbstractViewPanel atState(ApplicationState state) {
		return viewsByApplicationStates.get(state);
	}

	/**
	 * @see AbstractViewPanel#alert(String, int)
	 * @param message     Message shown in the pop-upmessageType Must be part of the
	 *                    JOptionPane values
	 * @param messageType Must be part of the JOptionPane values
	 */
	public int alert(String message, int messageType) {
		return this.getCurrentAbstractPanel().alert(message, messageType);
	}

	private AbstractViewPanel getCurrentAbstractPanel() {
		return this.viewsByApplicationStates.get(this.model.getApplicationState());
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

	public void addEventHandler(ApplicationState state, EventId eventId, Action action) {
		this.viewsByApplicationStates.get(state).addEventHandler(eventId, action);
		;
	}

	public void setUndoable(boolean b) {
		this.menuHandler.setUndoable(b);
	}

	public void setRedoable(boolean b) {
		this.menuHandler.setRedoable(b);
		// TODO Auto-generated method stub

	}
}
