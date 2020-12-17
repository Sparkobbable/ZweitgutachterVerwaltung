package model.exceptions;

import model.domain.BachelorThesis;
import model.domain.Reviewer;

/**
 * Thrown when an action is about to be performed which is forbidden on reviewers supervising a {@link BachelorThesis} as first review
 */
public class SupervisesThesisException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String reviewerName;

	public SupervisesThesisException(String message, Reviewer reviewer) {
		super(message);
		this.reviewerName = reviewer.getName();
	}
	
	public String getReviewerName() {
		return this.reviewerName;
	}
}
