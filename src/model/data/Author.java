package model.data;

import model.AbstractModel;

/**
 * Author of a BachelorThesis
 *
 */
public class Author extends AbstractModel {
	private String name;
	private String studyGroup;
	
	/**
	 * Creates an Author of a BachelorThesis
	 * @param name Name of the Author
	 * @param studyGroup StudyGroup of the Author
	 */
	public Author(String name, String studyGroup) {
		this.name = name;
		this.studyGroup = studyGroup;
	}

	/**
	 * Creates an Author of a BachelorThesis
	 * @param name Name of the Author
	 */
	public Author(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getStudyGroup() {
		return studyGroup;
	}

	public void setName(String name) {
		this.name = name;
	}
}
