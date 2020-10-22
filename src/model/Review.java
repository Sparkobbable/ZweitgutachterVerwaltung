package model;

import util.ReviewStatus;

@SuppressWarnings("deprecation")
public class Review extends AbstractModel {
	private Reviewer reviewer;
	private boolean isFirstReview;
	private ReviewStatus status;
	private BachelorThesis bachelorThesis;
	
	/**
	 * Creates a Review for a BachelorThesis
	 * @param reviewer The Reviewer reviewing the BachelorThesis
	 * @param isFirstReview Whether the Review is the first Review for the BachelorThesis
	 * @param status Status of the Review
	 * @param bachelorThesis The BachelorThesis to be reviewed
	 */
	public Review(Reviewer reviewer, boolean isFirstReview, ReviewStatus status, BachelorThesis bachelorThesis) {
		this.reviewer = reviewer;
		this.isFirstReview = isFirstReview;
		this.status = status;
		this.bachelorThesis = bachelorThesis;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public boolean isFirstReview() {
		return isFirstReview;
	}

	public ReviewStatus getStatus() {
		return status;
	}
	
	public void setStatus(ReviewStatus status) {
		this.status = status;
		this.notifyObservers();
		this.setChanged();
	}

	public BachelorThesis getBachelorThesis() {
		return bachelorThesis;
	}
}
