package model.data;

import java.beans.PropertyChangeSupport;
import java.util.Optional;

/**
 * stores information about a BachelorThesis
 * 
 */
public class BachelorThesis {
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
	public BachelorThesis(String topic, Author author, FirstReview firstReview, Optional<SecondReview> secondReview) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.firstReview = firstReview;
		this.secondReview = secondReview;
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

	//Probably not used anymore
	
//	public void setFirstReview(Review firstReview) {
//		Optional<Review> old = this.firstReview;
//		this.firstReview = Optional.of(firstReview);
//		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEW, old, this.firstReview);
//	}

	public void setSecondReview(SecondReview secondReview) {
		Optional<SecondReview> old = this.secondReview;
		this.secondReview = Optional.of(secondReview);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEW, old, this.secondReview);
	}
}
