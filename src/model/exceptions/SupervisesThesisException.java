package model.exceptions;

import model.domain.Reviewer;

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
