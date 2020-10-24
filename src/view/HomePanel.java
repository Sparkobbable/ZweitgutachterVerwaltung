package view;

import static model.constants.ButtonId.ANALYSE;
import static model.constants.ButtonId.IMPORT_FIRST_REVIEWERS;
import static model.constants.ButtonId.SHOW_REVIEWERS;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Basic home panel, allows navigating to different application sections
 */
public class HomePanel extends AbstractView<HomePanel> {

	private static final long serialVersionUID = 1L;

	public HomePanel(String id) { //maybe allow constructor without parameter?
		super(id, "Home");
	}
	
	public void init() {
		super.init();
		this.setBackground(Color.GREEN); //TODO remove
		addButton(SHOW_REVIEWERS);
		addButton(IMPORT_FIRST_REVIEWERS);
		addButton(ANALYSE);
	}

	
	@Override
	protected void createButtons() {
		super.createButtons();
		buttons.put(SHOW_REVIEWERS, new JButton("Gutachter anzeigen"));
		buttons.put(IMPORT_FIRST_REVIEWERS, new JButton("Importiere Erstgutachter"));
		buttons.put(ANALYSE, new JButton("Analyse"));

	}
}
