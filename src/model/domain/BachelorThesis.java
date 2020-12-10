package model.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Optional;

import controller.propertychangelistener.ChangeableProperties;

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
	public static final String COMMENT = "commant";

	protected final PropertyChangeSupport propertyChangeSupport;

	private final String topic;
	private final Author author;
	private final FirstReview firstReview;
	private final String comment;
	private Optional<SecondReview> secondReview;

	/**
	 * Creates a BachelorThesis
	 * 
	 * @param topic        Topic of the BachelorThesis
	 * @param author       Author of the BachelorThesis (Student)
	 * @param firstReview  First Review made by a Reviewer
	 * @param secondReview Second Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author, Reviewer firstReviewer, String comment) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.secondReview = Optional.empty();
		this.topic = topic;
		this.author = author;
		this.firstReview = new FirstReview(firstReviewer, this);

		firstReviewer.addFirstReviewerReview(this.firstReview);
		this.comment = comment;
	}

	public String getTopic() {
		return topic;
	}

	public Author getAuthor() {
		return author;
	}

	public String getComment() {
		return comment;
	}

	public FirstReview getFirstReview() {
		return firstReview;
	}

	public Optional<SecondReview> getSecondReview() {
		return secondReview;
	}

	/**
	 * Removes the secondReviewer reference from this reviewer
	 */
	public void removeSecondReview() {
		this.setSecondReview(null);
	}

	/**
	 * Sets the secondReview. If the {@link CascadeMode} is set to CASCADE, the
	 * thesis is added to the reviewer referenced in the given review. Notifies
	 * observers.
	 * 
	 * @param secondReview
	 */
	public void setSecondReview(SecondReview secondReview) {
		Optional<SecondReview> old = this.secondReview;
		this.secondReview = Optional.ofNullable(secondReview);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEW, old, this.secondReview);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}
	
	public boolean isThesisInList(List<BachelorThesis> thesesList) {
		return thesesList.stream().filter(thesisInList -> thesisInList.getTopic().equals(this.getTopic())).findAny().isPresent();
	}

}
