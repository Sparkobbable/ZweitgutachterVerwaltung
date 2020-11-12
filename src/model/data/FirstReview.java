package model.data;

public class FirstReview extends Review {

	/**
	 * Creates a Review for a BachelorThesis made by the first reviewer
	 * 
	 * @param reviewer       The Reviewer reviewing the BachelorThesis
	 * @param bachelorThesis The BachelorThesis to be reviewed
	 */
	public FirstReview(Reviewer reviewer, BachelorThesis bachelorThesis) {
		super(reviewer, bachelorThesis);
	}

}
