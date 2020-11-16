package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

import model.ChangeableProperties;
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

	private String topic;
	private Author author;
	private FirstReview firstReview;
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
		this.setFirstReview(new FirstReview(firstReviewer, this), CascadeMode.CASCADE);
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

	public void setTopic(String topic) {
		String old = this.topic;
		this.topic = topic;
		this.propertyChangeSupport.firePropertyChange(TOPIC, old, this.topic);

	}

	public void setAuthor(Author author) {
		Author old = this.author;
		this.author = author;
		this.propertyChangeSupport.firePropertyChange(AUTHOR, old, this.author);
	}

	/**
	 * Creates a new SecondReview instance and adds it to this thesis as well as the
	 * referenced Reviewer.
	 * 
	 * @param reviewer
	 */
	public void setSecondReviewer(Reviewer reviewer) {
		this.setSecondReview(new SecondReview(reviewer, this), CascadeMode.CASCADE);
	}

	/**
	 * Sets the secondReview. If the {@link CascadeMode} is set to CASCADE, the
	 * thesis is added to the reviewer referenced in the given review. Notifies
	 * observers.
	 * 
	 * @param secondReview
	 * @param cascadeMode
	 */
	void setSecondReview(SecondReview secondReview, CascadeMode cascadeMode) {
		Optional<SecondReview> old = this.secondReview;
		this.secondReview = Optional.of(secondReview);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEW, old, this.secondReview);
		if (cascadeMode == CascadeMode.CASCADE) {
			secondReview.getReviewer().addSecondReviewerReview(secondReview, CascadeMode.STOP);
		}
	}

	/**
	 * Sets the secondReview. If the {@link CascadeMode} is set to CASCADE, the
	 * thesis is added to the reviewer referenced in the given review. Notifies
	 * observers.
	 * 
	 * @param review
	 * @param cascadeMode
	 */
	void setFirstReview(FirstReview review, CascadeMode cascadeMode) {
		FirstReview old = this.firstReview;
		this.firstReview = review;
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEW, old, this.firstReview);
		if (cascadeMode == CascadeMode.CASCADE) {
			review.getReviewer().addFirstReviewerReview(review, CascadeMode.STOP);
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
