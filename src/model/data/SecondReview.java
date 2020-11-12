package model.data;

import model.enums.ReviewStatus;

public class SecondReview extends Review {

	/**
	 * Creates a Review for a BachelorThesis made by the first reviewer
	 * 
	 * @param reviewer       The Reviewer reviewing the BachelorThesis
	 * @param status		 Defines whether the reviewer accepted the second review
	 * @param bachelorThesis The BachelorThesis to be reviewed
	 */
	public SecondReview(Reviewer reviewer, ReviewStatus status, BachelorThesis bachelorThesis) {
		super(reviewer, status, bachelorThesis);
	}

	public void setStatus(ReviewStatus status) {
		ReviewStatus old = this.status;
		this.status = status;
		propertyChangeSupport.firePropertyChange(STATUS, old, status);
	}

	public ReviewStatus getStatus() {
		return status;
	}
}
