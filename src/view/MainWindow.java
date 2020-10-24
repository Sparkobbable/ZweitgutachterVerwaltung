package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel mainContainer;
	private Map<String, AbstractView<?>> availableViews; // TODO can/shall we remove this?
	private CardLayout cardLayout;

	static {
		updateLookAndFeel();
	}

	public MainWindow() {
		this.mainContainer = new JPanel();
		this.cardLayout = new CardLayout();
		this.availableViews = new HashMap<>();
		this.mainContainer.setLayout(cardLayout);
	}

	/**
	 * Initializes the Window
	 * 
	 * @param data Needs the DataAccess of the Application
	 */
	public void init() {

		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("thesisSPACE");
		this.setBackground(Color.PINK);
		mainContainer.setBackground(Color.BLACK);

		this.add(mainContainer);
		this.setVisible(true); // TODO maybe extract to controller to allow initialization before children's initialization?

	}

	/**
	 * Updates the Look&Feel to match the native Look&Feel.
	 * <p>
	 * TODO decide if we can use this without testing on a Mac (no)
	 */
	private static void updateLookAndFeel() {
		try {
			String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(systemLookAndFeelClassName);
			Logger.getLogger(MainWindow.class.getName())
					.info(String.format("Successfully Updated LookAndFeel to %s", systemLookAndFeelClassName));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
			// use default look & feel
		}
	}

	/**
	 * Switch to a view that has already been registered.
	 * 
	 * @param viewId Id of the view that shall be shown.
	 */
	public void switchToView(String viewId) {
		if (!this.availableViews.containsKey(viewId)) {
			throw new IllegalArgumentException(String.format("View %s not registered for this window.", viewId));
		}
		cardLayout.show(mainContainer, viewId);
	}

	/**
	 * Registers a view that can be shown later. The first view that is registered
	 * will be the home screen shown on startup, unless
	 * {@link #switchToView(String)} is called beforehand.
	 * 
	 * @param view View to be registered.
	 */
	public void registerView(AbstractView<?> view) {
		if (this.availableViews.containsKey(view.getId())) {
			throw new IllegalArgumentException(
					String.format("A View with id %s is already registered for this window.", view.getId()));
		}
		Logger.getLogger(MainWindow.class.getName()).info(String.format("Registering view %s", view.getId()));
		this.availableViews.put(view.getId(), view);
		this.mainContainer.add(view, view.getId());
	}

}
