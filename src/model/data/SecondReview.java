package model.data;

import model.enums.ReviewStatus;
import model.enums.ReviewType;

public class SecondReview extends Review {

	public static final String STATUS = "reviewStatus";
	
	protected ReviewStatus status;
	
	/**
	 * Creates a SecondReview for a BachelorThesis
	 * 
	 * @param reviewer       The Reviewer reviewing the BachelorThesis
	 * @param bachelorThesis The BachelorThesis to be reviewed
	 * @param status		 Defines whether the reviewer accepted the second review
	 */
	public SecondReview(Reviewer reviewer, BachelorThesis bachelorThesis, ReviewStatus status) {
		super(reviewer, bachelorThesis);
		this.status = status;
	}

	/**
	 * Creates a SecondReview for a BachelorThesis
	 * <p>
	 * the value for {@link #status} is set to its default value APPROVED
	 * @param reviewer
	 * @param bachelorThesis
	 */
	public SecondReview(Reviewer reviewer, BachelorThesis bachelorThesis) {
		this(reviewer, bachelorThesis, ReviewStatus.REQUESTED);
	}

	public void setStatus(ReviewStatus status) {
		ReviewStatus old = this.status;
		this.status = status;
		propertyChangeSupport.firePropertyChange(STATUS, old, status);
	}

	public ReviewStatus getStatus() {
		return status;
	}
	
	@Override
	public ReviewType getReviewType() {
		return ReviewType.SECOND_REVIEW;
	}

	/**
	 * sets the {@link #status} to APPROVED
	 */
	public void approve() {
		this.setStatus(ReviewStatus.APPROVED);
	}

}
