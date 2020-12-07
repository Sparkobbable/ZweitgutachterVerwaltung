package model.domain;

/**
 * Author of a BachelorThesis
 *
 */
public class Author {

	public static final String NAME = "name";
	public static final String STUDY_GROUP = "studyGroup";
	public static final String COMPANY = "company";

	// data - Authors cannot be edited, yet
	private final String name;
	private final String studyGroup;
	private final String company;

	/**
	 * Creates an Author of a BachelorThesis
	 * 
	 * @param name Name of the Author
	 */
	public Author(String name, String studyGroup, String company) {
		this.name = name;
		this.studyGroup = studyGroup;
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public String getStudyGroup() {
		return studyGroup;
	}
	public String getCompany() {
		return company;
	}
}
