package view.widgets;

import java.awt.Color;

import javax.swing.JButton;

import view.ViewProperties;
import view.resource.ImageLoader;

/**
 * Factory class for {@link JButton}s that have some default properties
 *
 */
public class ButtonFactory {

	private static ButtonFactory instance;
	private ImageLoader imageLoader;
	
	public static ButtonFactory getInstance() {
		if (instance == null) {
			instance = new ButtonFactory();
		}
		return instance;
	}
	
	private ButtonFactory() {
		this.imageLoader = new ImageLoader();
	}
	
	public JButton createButton(String title) {
		JButton button = new JButton(title);
		this.scaleButton(button, ViewProperties.BUTTON_FONT_SIZE);
		return button;
	}

	public JButton createMenuButton(String title) {
		JButton button = new JButton(title);
		this.scaleButton(button, ViewProperties.MENU_BUTTON_FONT_SIZE);
		button.setOpaque(true);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusable(false);
		return button;
	}
	
	public JButton createImageButton(String imageName) {
		JButton button = new JButton(this.imageLoader.loadImageIcon(imageName));
		button.setBackground(ViewProperties.BUTTON_COLOR);

		return button;
	}
	
	public JButton createSeamlessImageButton(String imageName) {
		JButton button = createImageButton(imageName);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setForeground(Color.WHITE);
		return button;
	}
	

	private void scaleButton(JButton button, float size) {
		button.setFont(button.getFont().deriveFont(size));
	}
}
