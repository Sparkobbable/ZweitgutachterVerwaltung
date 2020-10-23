package view;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.ReviewerList;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private HomePanel homePanel;
	private JScrollPane reviewerOverview;
	@Deprecated
	/**
	 * TODO Remove this ASAP.
	 * 
	 * @deprecated the main window should not know the list of all reviewers (or,
	 *             any data at all).
	 * 
	 */
	private ReviewerList data;

	static {
		updateLookAndFeel();
	}

	public MainWindow() {
		this.homePanel = new HomePanel(this);
		this.menuBar = new JMenuBar();
	}

	/**
	 * Initializes the Window
	 * 
	 * @param data Needs the DataAccess of the Application
	 */
	public void init(ReviewerList data) {
		this.data = data;

		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("thesisSPACE");

		this.homePanel.init();
		this.add(this.homePanel);

		this.setVisible(true);

//		this.menu = new JMenu();
//		this.menu.add(new JMenuItem());
	}

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

	public ReviewerList getData() {
		return data;
	}

	public JMenuBar getMenubar() {
		return menuBar;
	}

	public void setMenubar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JPanel getHomePanel() {
		return homePanel;
	}

	public JScrollPane getReviewerOverview() {
		return reviewerOverview;
	}

	public void setReviewerOverview(JScrollPane reviewerOverview) {
		this.reviewerOverview = reviewerOverview;
		this.add(reviewerOverview);
	}

	public ReviewerList getReviewers() {
		return data;
	}

}
