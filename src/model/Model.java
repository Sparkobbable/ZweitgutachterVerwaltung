package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.ApplicationState;

/**
 * Data store for all reviewers
 * <p>
 */
public class Model implements ChangeableProperties{

	private PropertyChangeSupport propertyChangeSupport;

	public static final String APPLICATION_STATE = "applicationState";

	public static final String SELECTED_REVIEWER = "selectedReviewer";

	public static final String REVIEWERS = "reviewers";
	private List<Reviewer> reviewers;
	private Optional<Reviewer> selectedReviewer;
	private ApplicationState applicationState;

	public Model() {
		this(new ArrayList<>());
	}

	public Model(List<Reviewer> reviewers) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.reviewers = reviewers;
		this.selectedReviewer = Optional.empty();
	}

	/**
	 * Finds all bachelorThesis without a second review
	 * 
	 * @return ArrayList of the found bachelorThesis
	 */
	public ArrayList<BachelorThesis> getThesisMissingSecReview(Optional<List<BachelorThesis>> excludedTheses) {
		ArrayList<BachelorThesis> thesisList = new ArrayList<>();
		for (Reviewer reviewer : this.getReviewers()) {
			for (BachelorThesis thesis : reviewer.getSupervisedThesis()) {
				if (thesis.getSecondReview().isEmpty()) {
					thesisList.add(thesis);
				}
			}
		}
		excludedTheses.ifPresent(excludeList -> thesisList.removeAll(excludeList));
		return thesisList;
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

	public void setReviewers(List<Reviewer> reviewers) {
		List<Reviewer> old = this.reviewers;
		this.reviewers = reviewers;
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, reviewers);
	}

	public void addReviewer(Reviewer reviewer) {
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.add(reviewer);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	public void removeByIndex(int index) {
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.remove(index);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	public void removeByIndices(int[] indices) {
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		IntStream.of(indices).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue)
				.forEach(this.reviewers::remove);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);

	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
		this.reviewers.forEach(reviewer -> reviewer.addPropertyChangeListener(propertyChangeListener));
	}

	public ArrayList<BachelorThesis> getThesisMissingSecReview() {
		return getThesisMissingSecReview(Optional.empty());
	}

}
