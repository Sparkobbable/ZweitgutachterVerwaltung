package view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.events.Action;
import controller.events.CompositeEventSource;
import controller.events.EventSource;
import controller.propertychangelistener.PropertyChangeManager;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import util.Log;
import view.panels.HomePanel;
import view.panels.ImportfirstrewierPanel;
import view.panels.StateChooserPanel;
import view.panels.collaboration.CollaborationPanel;
import view.panels.editor.ReviewerEditorPanel;
import view.panels.editor.ThesisAssignmentPanel;
import view.panels.overview.ReviewerOverviewPanel;
import view.panels.overview.ThesesOverviewPanel;
import view.panels.prototypes.AbstractViewPanel;

/**
 * Handles and stores the panels. Shows and links them to their {@link ApplicationState}
 *
 */
public class View implements EventSource {

	private Map<ApplicationState, AbstractViewPanel> viewsByApplicationStates;
	private CompositeEventSource eventSourceHandler;
	private Model model;

	private MainWindow window;
	private MenuBarHandler menuHandler;
	private PropertyChangeManager propertyChangeManager;

	public View(Model model) {
		this.model = model;
		this.window = new MainWindow();
		this.menuHandler = new MenuBarHandler();
		this.eventSourceHandler = new CompositeEventSource();
		this.eventSourceHandler.register(this.menuHandler);
		this.createViews();
		this.window.setJMenuBar(this.menuHandler);

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
		this.window.setVisible(true);
	}

	private void switchState(ApplicationState oldState, ApplicationState newState) {
		this.viewsByApplicationStates.get(newState).prepare();
		this.window.switchToView(this.viewsByApplicationStates.get(newState).getViewId());
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
	}

	public void setUndoable(boolean b) {
		this.menuHandler.setUndoable(b);
	}

	public void setRedoable(boolean b) {
		this.menuHandler.setRedoable(b);
	}

	/*
	 * -----------------------------------------------------------------------------
	 * -- | static methods
	 * -----------------------------------------------------------------------------
	 * --
	 */

	static {
		updateLookAndFeel();
	}

	/**
	 * Updates the Look&Feel to match the native Look&Feel.
	 */
	private static void updateLookAndFeel() {
		try {
			String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(systemLookAndFeelClassName);
			Log.info(MainWindow.class.getName(), "Successfully Updated LookAndFeel to %s", systemLookAndFeelClassName);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			// use default look & feel
		}
	}
}
