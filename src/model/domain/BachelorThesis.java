package model.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

import controller.propertychangelistener.ChangeableProperties;
import model.enums.CascadeMode;

/**
 * stores information about a BachelorThesis
 * 
 */
public class BachelorThesis implements ChangeableProperties {
	// Descriptors passed to PropertyChangedEvents
	public static final String TOPIC = "topic";
	public static final String AUTHOR = "author";
	public static final String FIRST_REVIEW = "firstReview";
	public static final String SECOND_REVIEW = "secondReview";

	protected final PropertyChangeSupport propertyChangeSupport;

	private final String topic;
	private final Author author;
	private final FirstReview firstReview;

	private Optional<SecondReview> secondReview;

	/**
	 * Creates a BachelorThesis
	 * 
	 * @param topic        Topic of the BachelorThesis
	 * @param author       Author of the BachelorThesis (Student)
	 * @param firstReview  First Review made by a Reviewer
	 * @param secondReview Second Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author, Reviewer firstReviewer) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.secondReview = Optional.empty();
		this.topic = topic;
		this.author = author;
		this.firstReview = new FirstReview(firstReviewer, this);
		firstReviewer.addFirstReviewerReview(this.firstReview);
	}

	public String getTopic() {
		return topic;
	}

	public Author getAuthor() {
		return author;
	}

	public FirstReview getFirstReview() {
		return firstReview;
	}

	public Optional<SecondReview> getSecondReview() {
		return secondReview;
	}

	/**
	 * Creates a new SecondReview instance and adds it to this thesis as well as the
	 * referenced Reviewer.
	 * 
	 * This Method should only be used during the import
	 * 
	 * @param reviewer
	 */
	public void setSecondReviewer(Reviewer reviewer) {
		this.setSecondReview(new SecondReview(reviewer, this), CascadeMode.CASCADE);
	}

	/**
	 * Removes the secondReviewer reference from this reviewer
	 */
	public void removeSecondReview() {
		this.setSecondReview(null, CascadeMode.STOP);
	}

	/**
	 * Sets the secondReview. If the {@link CascadeMode} is set to CASCADE, the
	 * thesis is added to the reviewer referenced in the given review. Notifies
	 * observers.
	 * 
	 * @param secondReview
	 * @param cascadeMode
	 */
	public void setSecondReview(SecondReview secondReview, CascadeMode cascadeMode) {
		Optional<SecondReview> old = this.secondReview;
		this.secondReview = Optional.ofNullable(secondReview);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEW, old, this.secondReview);
		if (cascadeMode == CascadeMode.CASCADE && this.secondReview.isPresent()) {
			secondReview.getReviewer().addSecondReview(secondReview, CascadeMode.STOP);
		}
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

}
