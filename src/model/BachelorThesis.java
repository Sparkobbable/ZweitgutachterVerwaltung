package model;

@SuppressWarnings("deprecation")
public class BachelorThesis extends AbstractModel {
	private String topic;
	private Author author;
	private Review firstReview;
	private Review secondReview;
	
	/**
	 * Creates a BachelorThesis
	 * @param topic Topic of the BachelorThesis
	 * @param author Author of the BachelorThesis (Student)
	 * @param firstReview First Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author, Review firstReview) {
		this.topic = topic;
		this.author = author;
		this.firstReview = firstReview;
	}

	/**
	 * Creates a BachelorThesis
	 * @param topic Topic of the BachelorThesis
	 * @param author Author of the BachelorThesis (Student)
	 * @param firstReview First Review made by a Reviewer
	 * @param secondReview Second Review made by a Reviewer
	 */
	public BachelorThesis(String topic, Author author, Review firstReview, Review secondReview) {
		this.topic = topic;
		this.author = author;
		this.firstReview = firstReview;
		this.secondReview = secondReview;
	}

	public Review getSecondReview() {
		return secondReview;
	}

	public void setSecondReview(Review secondReview) {
		this.secondReview = secondReview;
		this.notifyObservers();
		this.setChanged();
	}

	public String getTopic() {
		return topic;
	}

	public Author getAuthor() {
		return author;
	}

	public Review getFirstReview() {
		return firstReview;
	}
}
