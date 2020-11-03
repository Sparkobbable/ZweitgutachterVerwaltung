package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.stream.IntStream;

import model.data.Reviewer;
import model.enums.ApplicationState;

/**
 * Data store for all reviewers
 * <p>
 * TODO extend AbstractModel??
 */
@SuppressWarnings("deprecation") 
// using the Observable pattern and therefore Observable is encouraged in this project
public class Model extends Observable {

	private List<Reviewer> reviewers;
	private Reviewer selectedReviewer;
	private ApplicationState applicationState;

	public Model() {
		this.reviewers = new ArrayList<>();
	}

	public Model(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

	public void addReviewer(Reviewer reviewer) {
		this.reviewers.add(reviewer);
		this.setChanged();
		this.notifyObservers();
	}

	public void setReviewer(List<Reviewer> reviewer) {
		this.reviewers = reviewer;
		this.setChanged();
		this.notifyObservers();
	}

	public Reviewer getSelectedReviewer() {
		return selectedReviewer;
	}

	public void setSelectedReviewer(Reviewer selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
		this.setChanged();
		this.notifyObservers(); // TODO Maybe pass an eventId to let the views know which change triggered the
								// observer, not sure if necessary (but would probably increase the performance)
								// A global observer would then be possible as well
	}

	public void setSelectedReviewer(int reviewerIndex) {
		this.setSelectedReviewer(this.reviewers.get(reviewerIndex));
	}

	public Reviewer findReviewerByName(String name) {
		return this.reviewers.stream().filter(reviewers -> reviewers.getName().equals(name)).findAny().orElse(null);
	}

	public void removeByIndex(int rowIdx) {
		this.reviewers.remove(rowIdx);
		this.setChanged();
		this.notifyObservers();
	}

	public void removeByIndices(int[] indices) {
		// TODO maybe use an ID system?
		IntStream.of(indices).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder()).forEach(this::removeByIndex);
	}

	public ApplicationState getApplicationState() {
		return applicationState;
	}

	public void setApplicationState(ApplicationState applicationState) {
		this.applicationState = applicationState;
		this.setChanged();
		this.notifyObservers();
	}

}
