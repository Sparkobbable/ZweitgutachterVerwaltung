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

	/**
	 * @return Serving a singletion of this class to prevent loss of performance
	 */
	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}

	/**
	 * Loads an image from the given image name
	 * @param name	Name of the image to be loaded
	 * @return		The {@link ImageIcon} presenting the loaded image
	 */
	public ImageIcon loadImageIcon(String name) {
		return new ImageIcon(this.getClass().getResource(IMAGE_FOLDER + this.enforceSuffix(name, DEFAULT_SUFFIX)));
	}

	private String enforceSuffix(String name, String suffix) {
		return name.endsWith(suffix) ? name : name + suffix;
	}

}
