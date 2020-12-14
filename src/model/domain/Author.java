package model.domain;

/**
 * Author of a BachelorThesis
 *
 */
public class Author {

	public static final String NAME = "name";
	public static final String STUDY_GROUP = "studyGroup";
	public static final String COMPANY = "company";

	// data - Authors cannot be edited
	private final String name;
	private final String studyGroup;
	private final String company;

	/**
	 * Creates an author of a {@link BachelorThesis}
	 * @param name Name of the author
	 * @param studyGroup The studygroup in which the author is learning
	 * @param company The company the author is working for
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
