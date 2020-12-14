package model.domain;

import model.enums.ReviewType;

/**
 * First {@link Review} of a {@link BachelorThesis}
 */
public class FirstReview extends Review {

	/**
	 * Creates a Review for a {@link BachelorThesis} made by the first {@link Reviewer}
	 * 
	 * @param reviewer       The {@link Reviewer} reviewing the BachelorThesis
	 * @param bachelorThesis The {@link BachelorThesis} to be reviewed
	 */
	public FirstReview(Reviewer reviewer, BachelorThesis bachelorThesis) {
		super(reviewer, bachelorThesis);
	}

	@Override
	public ReviewType getReviewType() {
		return ReviewType.FIRST_REVIEW;
	}

}
