package model.data;

import java.util.Optional;

import model.AbstractModel;

@SuppressWarnings("deprecation")
/**
 * stores information about a BachelorThesis
 * 
 */
public class BachelorThesis extends AbstractModel {
	private String topic;
	private Author author;
	private Optional<Review> firstReview;
	private Optional<Review> secondReview;
	
	/**
	 * Creates a BachelorThesis with an unspecified second reviewer
	 * @param topic Topic of the BachelorThesis
	 * @param author Author of the BachelorThesis (Student)
	 * @param firstReview First Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author) {
		this.topic = topic;
		this.author = author;
	}

	/**
	 * Creates a BachelorThesis
	 * @param topic Topic of the BachelorThesis
	 * @param author Author of the BachelorThesis (Student)
	 * @param firstReview First Review made by a Reviewer
	 * @param secondReview Second Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author, Optional<Review> firstReview, Optional<Review> secondReview) {
		this.topic = topic;
		this.author = author;
		this.firstReview = firstReview;
		this.secondReview = secondReview;
	}

	/**
	 * Empty constructor for creating a new BachelorThesis in editing-mode
	 */
	public BachelorThesis() {

	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Optional<Review> getSecondReview() {
		return secondReview;
	}
	
	public void setFirstReview(Review firstReview) {
		this.firstReview = Optional.of(firstReview);
		this.notifyObservers();
		this.setChanged();
	}

	public void setSecondReview(Review secondReview) {
		this.secondReview = Optional.of(secondReview);
		this.notifyObservers();
		this.setChanged();
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

	public BachelorThesis setAuthor(Author author) {
		this.author = author;
		return this;
	}
}
