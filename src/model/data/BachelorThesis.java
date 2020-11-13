package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Optional;

import model.ChangeableProperties;
import model.enums.CascadeMode;
import model.enums.ReviewStatus;

/**
 * stores information about a BachelorThesis
 * 
 */
public class BachelorThesis implements ChangeableProperties{
	private PropertyChangeSupport propertyChangeSupport;

	// Descriptors passed to PropertyChangedEvents
	public static final String TOPIC = "topic";
	public static final String AUTHOR = "author";
	public static final String FIRST_REVIEW = "firstReview";
	public static final String SECOND_REVIEW = "secondReview";

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
		this.firstReview = new FirstReview(firstReviewer, this);
		this.secondReview = Optional.empty();
		this.topic = topic;
		this.author = author;
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

	public void setSecondReview(SecondReview secondReview, CascadeMode cascadeMode) {
		Optional<SecondReview> old = this.secondReview;
		this.secondReview = Optional.of(secondReview);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEW, old, this.secondReview);
		if (cascadeMode == CascadeMode.CASCADE) {
			secondReview.getReviewer().addSecondReviewerReview(secondReview, CascadeMode.STOP);
		}
	}
	
	public void setFirstReview(FirstReview review, CascadeMode cascadeMode) {
		FirstReview old = this.firstReview;
		this.firstReview = review;
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEW, old, this.secondReview);
		if (cascadeMode == CascadeMode.CASCADE) {
			review.getReviewer().addFirstReviewerReview(review, CascadeMode.STOP);
		}
	}
	

	public void setSecondReviewer(Reviewer secondReviewer, CascadeMode cascadeMode) {
		this.setSecondReview(new SecondReview(secondReviewer, this, ReviewStatus.REQUESTED), cascadeMode);
	}

	public void setSecondReviewer(Reviewer reviewer) {
		this.setSecondReviewer(reviewer, CascadeMode.CASCADE);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}

}
