package model.domain;

/**
 * Author of a BachelorThesis
 *
 */
public class Author {

	// data - Authors cannot be edited, yet
	private final String name;
	private final String studyGroup;

	/**
	 * Creates an Author of a BachelorThesis
	 * 
	 * @param name Name of the Author
	 */
	public Author(String name, String studyGroup) {
		this.name = name;
		this.studyGroup = studyGroup;
	}

	public String getName() {
		return name;
	}

	public String getStudyGroup() {
		return studyGroup;
	}
}
