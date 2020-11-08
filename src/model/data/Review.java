package model.data;

import java.beans.PropertyChangeSupport;

import model.enums.ReviewStatus;

public class Review {
	private PropertyChangeSupport propertyChangeSupport;

	// descriptors
	public static final String REVIEWER = "reviewer";
	public static final String FIRST_REVIEW = "firstReview";
	public static final String STATUS = "reviewStatus";
	public static final String BACHELOR_THESIS = "bachelorThesis";

	// data
	private Reviewer reviewer;
	private boolean firstReview;
	private ReviewStatus status;
	private BachelorThesis bachelorThesis;

	/**
	 * Creates a Review for a BachelorThesis
	 * 
	 * @param reviewer       The Reviewer reviewing the BachelorThesis
	 * @param isFirstReview  Whether the Review is the first Review for the
	 *                       BachelorThesis
	 * @param status         Status of the Review
	 * @param bachelorThesis The BachelorThesis to be reviewed
	 */
	public Review(Reviewer reviewer, boolean isFirstReview, ReviewStatus status, BachelorThesis bachelorThesis) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.reviewer = reviewer;
		this.firstReview = isFirstReview;
		this.status = status;
		this.bachelorThesis = bachelorThesis;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public boolean isFirstReview() {
		return firstReview;
	}

	public ReviewStatus getStatus() {
		return status;
	}

	public BachelorThesis getBachelorThesis() {
		return bachelorThesis;
	}

	public void setStatus(ReviewStatus status) {
		ReviewStatus old = this.status;
		this.status = status;
		propertyChangeSupport.firePropertyChange(STATUS, old, status);
	}
}
