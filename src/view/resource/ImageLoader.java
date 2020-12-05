package view.resource;

import javax.swing.ImageIcon;

/**
 * Responsible for loading any Images from the file system.
 *
 */
public class ImageLoader {

	private static final String IMAGE_FOLDER = "images/";
	private static final String DEFAULT_SUFFIX = ".png";

	private static ImageLoader instance;

	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}

	public ImageIcon loadImageIcon(String name) {
		return new ImageIcon(this.getClass().getResource(IMAGE_FOLDER + this.enforceSuffix(name, DEFAULT_SUFFIX)));
	}

	private String enforceSuffix(String name, String suffix) {
		return name.endsWith(suffix) ? name : name + suffix;
	}

}
