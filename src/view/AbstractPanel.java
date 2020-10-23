package view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class AbstractPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private String title;

	public AbstractPanel(String title) {
		this.title = title;
	}

	public void init() {
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT,
				TitledBorder.TOP));

	}
}
