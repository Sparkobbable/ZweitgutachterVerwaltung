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
	private Optional<Review> firstReview;
	private Optional<Review> secondReview;

	/**
	 * Base constructor for a BachelorThesis with a given first and second Reviewer
	 * <p>
	 * Should be called in all other constructors.
	 * 
	 * @param firstReview
	 * @param secondReview
	 */
	public BachelorThesis(Optional<Review> firstReview, Optional<Review> secondReview) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);

		this.firstReview = firstReview;
		this.secondReview = secondReview;
	}

	/**
	 * Creates a BachelorThesis
	 * 
	 * @param topic        Topic of the BachelorThesis
	 * @param author       Author of the BachelorThesis (Student)
	 * @param firstReview  First Review made by a Reviewer
	 * @param secondReview Second Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author, Optional<Review> firstReview, Optional<Review> secondReview) {
		this(firstReview, secondReview);
		this.topic = topic;
		this.author = author;
	}

	/**
	 * Creates a BachelorThesis with an unspecified first and second reviewer
	 * 
	 * @param topic       Topic of the BachelorThesis
	 * @param author      Author of the BachelorThesis (Student)
	 * @param firstReview First Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author) {
		this(topic, author, Optional.empty(), Optional.empty());
	}

	/**
	 * Empty constructor for creating a new BachelorThesis in editing-mode
	 */
	public BachelorThesis() {
		this(Optional.empty(), Optional.empty());
	}

	public String getTopic() {
		return topic;
	}

	public Author getAuthor() {
		return author;
	}

	public Optional<Review> getFirstReview() {
		return firstReview;
	}

	public Optional<Review> getSecondReview() {
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

	public void setFirstReview(Review firstReview) {
		Optional<Review> old = this.firstReview;
		this.firstReview = Optional.of(firstReview);
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEW, old, this.firstReview);
	}

	public void setSecondReview(Review secondReview) {
		Optional<Review> old = this.secondReview;
		this.secondReview = Optional.of(secondReview);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEW, old, this.secondReview);
	}
}
