package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	private JMenu menu;
	
	public void init() {
		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		
		JButton bGutachter = new JButton("Gutachter anzeigen");
		JButton bImport = new JButton("Importiere Erstgutachter");
		JButton bAnalyse = new JButton("Analyse");
		panel.add(bGutachter);
		panel.add(bImport);
		panel.add(bAnalyse);
		this.add(panel);
		
		this.setVisible(true);
		
//		this.menu = new JMenu();
//		this.menu.add(new JMenuItem());
	}
}
