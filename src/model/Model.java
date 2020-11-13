package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.ApplicationState;

/**
 * Data store for all reviewers
 * <p>
 */
public class Model implements ChangeableProperties, PropertyChangeListener {

	private PropertyChangeSupport propertyChangeSupport;

	public static final String APPLICATION_STATE = "applicationState";
	public static final String SELECTED_REVIEWER = "selectedReviewer";
	public static final String REVIEWERS = "reviewers";
	public static final String THESES = "theses";

	private List<BachelorThesis> theses;
	private List<Reviewer> reviewers;
	private Optional<Reviewer> selectedReviewer;
	private ApplicationState applicationState;

	private PropertyChangeManager propertyChangeManager;
	
	public Model(List<BachelorThesis> theses, List<Reviewer> reviewers) {
		this(theses);
		reviewers.forEach(this::addReviewer);
	}

	public Model(List<BachelorThesis> theses) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.propertyChangeManager = new PropertyChangeManager();
		this.selectedReviewer = Optional.empty();
		this.theses = theses;
		this.reviewers = new ArrayList<>();
		this.theses.forEach(this::addReviewersForThesis);
	}

	public Model() {
		this(new ArrayList<>());
	}

	public Reviewer findReviewerByName(String name) {
		return this.reviewers.stream().filter(reviewers -> reviewers.getName().equals(name)).findAny().orElse(null);
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public Optional<Reviewer> getSelectedReviewer() {
		return selectedReviewer;
	}

	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setApplicationState(ApplicationState applicationState) {
		ApplicationState old = this.applicationState;
		this.applicationState = applicationState;
		this.propertyChangeSupport.firePropertyChange(APPLICATION_STATE, old, this.applicationState);
	}

	public void setSelectedReviewer(Reviewer selectedReviewer) {
		Optional<Reviewer> old = this.selectedReviewer;
		this.selectedReviewer = Optional.ofNullable(selectedReviewer);
		this.propertyChangeSupport.firePropertyChange(SELECTED_REVIEWER, old, this.selectedReviewer);
	}

	public void setSelectedReviewer(int reviewerIndex) {
		this.setSelectedReviewer(this.reviewers.get(reviewerIndex));
	}

	public void addReviewer(Reviewer reviewer) {
		if (this.reviewers.contains(reviewer)) {
			return;
		}
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.add(reviewer);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	public void addThesis(BachelorThesis thesis) {
		List<BachelorThesis> old = new ArrayList<>(this.theses);
		this.theses.add(thesis);
		this.addReviewersForThesis(thesis);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	protected void addReviewersForThesis(BachelorThesis thesis) {
		this.addReviewer(thesis.getFirstReview().getReviewer());
		thesis.getSecondReview().ifPresent(secondReview -> this.addReviewer(secondReview.getReviewer()));
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
		this.reviewers.forEach(reviewer -> reviewer.addPropertyChangeListener(propertyChangeListener));
	}

	public List<BachelorThesis> getThesisMissingSecReview() {
		return this.theses.stream().filter(thesis -> thesis.getSecondReview().isEmpty()).collect(Collectors.toList());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.propertyChangeManager.propertyChange(evt);
	}

	public void updateReviewer(Reviewer reviewer, Reviewer newReviewer) {
		this.reviewers.set(this.reviewers.indexOf(reviewer), newReviewer); //TODO hacky
	}

}
