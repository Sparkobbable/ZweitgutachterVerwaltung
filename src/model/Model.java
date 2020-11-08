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

	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<Reviewer> reviewers) {
		List<Reviewer> old = this.reviewers;
		this.reviewers = reviewers;
		this.propertyChangeSupport.firePropertyChange("reviewers", old, reviewers);
	}

	public void addReviewer(Reviewer reviewer) {
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.add(reviewer);
		this.propertyChangeSupport.firePropertyChange("reviewers", old, this.reviewers);
	}

	public Optional<Reviewer> getSelectedReviewer() {
		return selectedReviewer;
	}

	public void setSelectedReviewer(Reviewer selectedReviewer) {
		Optional<Reviewer> old = this.selectedReviewer;
		this.selectedReviewer = Optional.ofNullable(selectedReviewer);
		this.propertyChangeSupport.firePropertyChange("selectedReviewer", old, this.selectedReviewer);
	}

	public void setSelectedReviewer(int reviewerIndex) {
		this.setSelectedReviewer(this.reviewers.get(reviewerIndex));
	}

	public Reviewer findReviewerByName(String name) {
		return this.reviewers.stream().filter(reviewers -> reviewers.getName().equals(name)).findAny().orElse(null);
	}

	public void removeByIndex(int index) {
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.remove(index);
		this.propertyChangeSupport.firePropertyChange("reviewers", old, this.reviewers);
	}

	public void removeByIndices(int[] indices) {
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		IntStream.of(indices).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue)
				.forEach(this.reviewers::remove);
		this.propertyChangeSupport.firePropertyChange("reviewers", old, this.reviewers);

	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(ApplicationState applicationState) {
		ApplicationState old = this.applicationState;
		this.applicationState = applicationState;
		this.propertyChangeSupport.firePropertyChange("applicationState", old, this.applicationState);
	}

	/**
	 * Finds all bachelorThesis without a second review
	 * 
	 * @return ArrayList of the found bachelorThesis
	 */
	public ArrayList<BachelorThesis> getThesisMissingSecReview() {
		ArrayList<BachelorThesis> thesisList = new ArrayList<>();
		for (Reviewer reviewer : this.getReviewers()) {
			for (BachelorThesis thesis : reviewer.getSupervisedThesis()) {
				if (thesis.getSecondReview().isEmpty()) {
					thesisList.add(thesis);
				}
			}
		}
		return thesisList;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * | Delegate methods to the responsible Objects
	 * -----------------------------------------------------------------------------
	 */

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		this.propertyChangeSupport.addPropertyChangeListener(pcl);
	}

}
