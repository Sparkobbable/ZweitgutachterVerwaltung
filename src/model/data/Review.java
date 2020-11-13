package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import model.ChangeableProperties;

public class Review implements ChangeableProperties {
	protected PropertyChangeSupport propertyChangeSupport;

	// descriptors
	public static final String REVIEWER = "reviewer";
	public static final String FIRST_REVIEW = "firstReview";
	public static final String BACHELOR_THESIS = "bachelorThesis";

	// data
	protected Reviewer reviewer;
	protected boolean firstReview;
	protected BachelorThesis bachelorThesis;

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
}
