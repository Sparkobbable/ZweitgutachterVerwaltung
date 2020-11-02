package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.enums.ViewId;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel mainContainer;
	private Map<ViewId, AbstractView> availableViews; // TODO can/shall we remove this?
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
	 */
	public void init() {

		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("thesisSPACE");
		this.setBackground(Color.PINK);
		// center
		this.setLocationRelativeTo(null);
		this.setIconImage(getApplicationIcon());

		mainContainer.setBackground(Color.BLACK);
		this.add(mainContainer);

		this.setVisible(true); // TODO maybe extract to controller to allow initialization before children's
								// initialization?

	}

	private Image getApplicationIcon() {
		try {
			URL resource = this.getClass().getResource("resource/images/diploma-icon.png");
			return ImageIO.read(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
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
	public void switchToView(ViewId viewId) {
		if (!this.availableViews.containsKey(viewId)) {
			throw new IllegalArgumentException(String.format("View %s not registered for this window.", viewId));
		}
		cardLayout.show(mainContainer, viewId.name());
	}

	/**
	 * Registers a view that can be shown later. The first view that is registered
	 * will be the home screen shown on startup, unless
	 * {@link #switchToView(String)} is called beforehand.
	 * 
	 * @param view View to be registered.
	 */
	public void registerView(AbstractView view) {
		if (this.availableViews.containsKey(view.getViewId())) {
			throw new IllegalArgumentException(
					String.format("A View with id %s is already registered for this window.", view.getViewId()));
		}
		Logger.getLogger(MainWindow.class.getName()).info(String.format("Registering view %s", view.getViewId()));
		this.availableViews.put(view.getViewId(), view);
		this.mainContainer.add(view, view.getViewId().name());
	}
}
