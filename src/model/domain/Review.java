package model.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.enums.ReviewType;
import model.propertychangelistener.ChangeableProperties;

/**
 * Review of a {@link BachelorThesis}
 * Use the specified classes {@link FirstReview} and {@link SecondReview} for creating reviews
 */
public abstract class Review implements ChangeableProperties {
	protected final PropertyChangeSupport propertyChangeSupport;

	// descriptors
	public static final String REVIEWER = "reviewer";
	public static final String FIRST_REVIEW = "firstReview";
	public static final String BACHELOR_THESIS = "bachelorThesis";

	// data
	protected final Reviewer reviewer;
	protected final BachelorThesis bachelorThesis;

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

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public abstract ReviewType getReviewType();
}
