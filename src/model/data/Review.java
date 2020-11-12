package model.data;

import java.beans.PropertyChangeSupport;

import model.enums.ReviewStatus;

public class Review {
	protected PropertyChangeSupport propertyChangeSupport;

	// descriptors
	public static final String REVIEWER = "reviewer";
	public static final String FIRST_REVIEW = "firstReview";
	public static final String STATUS = "reviewStatus";
	public static final String BACHELOR_THESIS = "bachelorThesis";

	// data
	protected Reviewer reviewer;
	protected boolean firstReview;
	protected ReviewStatus status;
	protected BachelorThesis bachelorThesis;

	protected Review(Reviewer reviewer, ReviewStatus status, BachelorThesis bachelorThesis) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.reviewer = reviewer;
		this.status = status;
		this.bachelorThesis = bachelorThesis;
	}

	protected Review(Reviewer reviewer, BachelorThesis bachelorThesis) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.reviewer = reviewer;
		this.bachelorThesis = bachelorThesis;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public BachelorThesis getBachelorThesis() {
		return bachelorThesis;
	}
}
