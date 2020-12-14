package model.domain;

import model.enums.ReviewStatus;
import model.enums.ReviewType;

/**
 * Second {@link Review} of a {@link BachelorThesis}
 */
public class SecondReview extends Review {

	public static final String STATUS = "reviewStatus";
	
	protected ReviewStatus status;
	
	/**
	 * Creates a SecondReview for a {@link BachelorThesis}
	 * 
	 * @param reviewer       The {@link Reviewer} reviewing the {@link BachelorThesis}
	 * @param bachelorThesis The {@link BachelorThesis} to be reviewed
	 * @param status		 Defines whether the reviewer accepted the second review
	 */
	public SecondReview(Reviewer reviewer, BachelorThesis bachelorThesis, ReviewStatus status) {
		super(reviewer, bachelorThesis);
		this.status = status;
	}

	/**
	 * Creates a SecondReview for a {@link BachelorThesis}
	 * <p>
	 * The value for {@link #status} is set to its default value {@link ReviewStatus#REQUESTED}
	 * @param reviewer			The {@link Reviewer} reviewing the {@link BachelorThesis}
	 * @param bachelorThesis	The {@link BachelorThesis} to be reviewed
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
	
	public boolean isApproved() {
		if(status == ReviewStatus.APPROVED) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public ReviewType getReviewType() {
		return ReviewType.SECOND_REVIEW;
	}
}
