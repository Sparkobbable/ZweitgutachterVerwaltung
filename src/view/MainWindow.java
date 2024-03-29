package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import util.Log;
import view.panels.prototypes.AbstractViewPanel;

/**
 * {@link JFrame} that displays an {@link AbstractViewPanel}, swapping between ViewPanels by using a {@link CardLayout}.
 */
public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel mainContainer;
	private Map<Integer, AbstractViewPanel> availableViews;
	private CardLayout cardLayout;

	public MainWindow() {
		this.mainContainer = new JPanel();
		this.cardLayout = new CardLayout();
		this.availableViews = new HashMap<>();
		this.mainContainer.setLayout(cardLayout);

		this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
		this.setMinimumSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension dimension = MainWindow.this.getSize();
				Dimension minDimension = MainWindow.this.getMinimumSize();
				if(dimension.width<minDimension.width) {
					dimension.width=minDimension.width;
				}
		        if(dimension.height<minDimension.height) {
		        	dimension.height=minDimension.height;
		        }
		        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
				scheduler.schedule(() -> MainWindow.this.setSize(dimension), ViewProperties.RESIZING_TIMER, TimeUnit.MILLISECONDS);
			}
		});
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("thesisSpace");
		this.setBackground(Color.PINK);
		// center
		this.setLocationRelativeTo(null);
		this.setIconImage(getApplicationIcon());
		this.add(mainContainer);
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
	 * Switch to a view that has already been registered.
	 * 
	 * @param viewId Id of the view that shall be shown.
	 */
	public void switchToView(Integer viewId) {
		if (!this.availableViews.containsKey(viewId)) {
			throw new IllegalArgumentException(String.format("View %s not registered for this window.", viewId));
		}
		cardLayout.show(mainContainer, viewId.toString());
	}

	/**
	 * Registers a view that can be shown later. The first view that is registered
	 * will be the home screen shown on startup, unless
	 * {@link #switchToView(Integer)} is called beforehand.
	 * 
	 * @param view View to be registered.
	 */
	public void registerView(AbstractViewPanel view) {
		if (this.availableViews.containsKey(view.getViewId())) {
			Log.info(this.getClass().getName(),
					"View %s is already registered in MainWindow. It will not be registered again.",
					view.getClass().getName());
		} else {
			Log.info(this.getClass().getName(), "Registering view %s.", view.getClass().getName());
			this.availableViews.put(view.getViewId(), view);
			this.mainContainer.add(view, view.getViewId().toString());
		}

	}
}
