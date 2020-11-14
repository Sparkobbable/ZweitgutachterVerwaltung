package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Author of a BachelorThesis
 *
 */
public class Author {
	// references
	protected final PropertyChangeSupport propertyChangeSupport;

	// descriptors
	public static final String NAME = "name";
	public static final String STUDY_GROUP = "studyGroup";

	// data
	private String name;
	private String studyGroup;

	/**
	 * Creates an Author of a BachelorThesis
	 * 
	 * @param name Name of the Author
	 */
	public Author(String name, String studyGroup) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.name = name;
		this.studyGroup = studyGroup;
	}

	public String getName() {
		return name;
	}

	public String getStudyGroup() {
		return studyGroup;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		// Fire PropertyChange after property has changed
		this.propertyChangeSupport.firePropertyChange(NAME, old, name);
	}

	public void setStudyGroup(String studyGroup) {
		String old = this.studyGroup;
		this.studyGroup = studyGroup;
		// Fire PropertyChange after property has changed
		this.propertyChangeSupport.firePropertyChange(STUDY_GROUP, old, studyGroup);
	}

	/*
	 * -----------------------------------------------------------------------------
	 * | Delegate methods to the responsible Objects
	 * -----------------------------------------------------------------------------
	 */

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		this.propertyChangeSupport.addPropertyChangeListener(pcl);
	}
}
