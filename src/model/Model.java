package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.propertychangelistener.ChangeableProperties;
import model.domain.Author;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.ApplicationState;

/**
 * Data store for all reviewers
 */
public class Model implements ChangeableProperties {

	public static final String APPLICATION_STATE = "applicationState";
	public static final String SELECTED_REVIEWER = "selectedReviewer";
	public static final String ANALYSE_REVIEWERS = "analyseReviewers";
	public static final String COLLABORATING_REVIEWERS = "collaboratingReviewers";
	public static final String REVIEWERS = "reviewers";
	public static final String THESES = "theses";

	protected final PropertyChangeSupport propertyChangeSupport;

	private List<BachelorThesis> theses;
	private List<Reviewer> reviewers;

	private List<Reviewer> analyseReviewers;
	private Map<Reviewer, Double> collaboratingReviewers;
	private Optional<Reviewer> selectedReviewer;
	private ApplicationState applicationState;

	public Model(List<BachelorThesis> theses, List<Reviewer> reviewers) {
		this();
		this.theses.addAll(theses);
		this.reviewers.addAll(reviewers);
	}

	/**
	 * Creates a new Model without any theses or reviewers.
	 */
	public Model() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.selectedReviewer = Optional.empty();
		this.analyseReviewers = new ArrayList<>();
		this.collaboratingReviewers = new HashMap<>();
		this.theses = new ArrayList<>();
		this.reviewers = new ArrayList<>();
	}

	/**
	 * Returns a reviewer with the given name, if any exist.
	 * <p>
	 * Use this method with care, as names may or may not be unique
	 * 
	 * @param name
	 * @return
	 */
	public Optional<Reviewer> findReviewerByName(String name) {
		return this.reviewers.stream().filter(reviewers -> reviewers.getName().equals(name)).findAny();
	}

	/**
	 * @return The current state of the Application
	 */
	public ApplicationState getApplicationState() {
		return applicationState;
	}

	/**
	 * @return The selected Reviewer, if any exist
	 */
	public Optional<Reviewer> getSelectedReviewer() {
		return selectedReviewer;
	}

	/**
	 * Return a list of all collaborating Reviewers for the selected Reviewer
	 * 
	 * @return List<Reviewer>
	 */
	public Map<Reviewer, Double> getCollaboratingReviewers() {
		return this.collaboratingReviewers;
	}
	
	/**
	 * Return a list of all Reviewers for the current Analyse
	 * including the amount of their reviews
	 * 
	 * @return List<Reviewer>
	 */
	public List<Reviewer> getAnalyseReviewers() {
		return this.analyseReviewers;
	}

	/**
	 * @return A read-only view of the reviewer list
	 */
	public List<Reviewer> getReviewers() {
		return Collections.unmodifiableList(this.reviewers);
	}

	/**
	 * @return A read-only view of the theses list
	 */
	public List<BachelorThesis> getTheses() {
		return Collections.unmodifiableList(this.theses);
	}

	/**
	 * Set the ApplicationState and notify any observers
	 * 
	 * @param applicationState
	 */
	public void setApplicationState(ApplicationState applicationState) {
		ApplicationState old = this.applicationState;
		this.applicationState = applicationState;
		this.propertyChangeSupport.firePropertyChange(APPLICATION_STATE, old, this.applicationState);
	}

	/**
	 * Set the selected Reviewer and notify any observers
	 * 
	 * @param selectedReviewer
	 */
	public void setSelectedReviewer(Reviewer selectedReviewer) {
		Optional<Reviewer> old = this.selectedReviewer;
		this.selectedReviewer = Optional.ofNullable(selectedReviewer);
		this.propertyChangeSupport.firePropertyChange(SELECTED_REVIEWER, old, this.selectedReviewer);
	}
	
	/**
	 * Set the analysis of Reviewers for the current Analyse and notify any
	 * observers
	 * 
	 * @param list
	 */
	public void setAnalyseReviewers(List<Reviewer> list) {
		List<Reviewer> old = this.analyseReviewers;
		this.analyseReviewers = list;
		this.propertyChangeSupport.firePropertyChange(ANALYSE_REVIEWERS, old, this.analyseReviewers);
	}

	/**
	 * Set the collaborating Reviewers for the selected Reviewer and notify any
	 * observers
	 * 
	 * @param list
	 */
	public void setCollaboratingReviewers(HashMap<Reviewer, Double> list) {
		Map<Reviewer, Double> old = this.collaboratingReviewers;
		this.collaboratingReviewers = list;
		this.propertyChangeSupport.firePropertyChange(COLLABORATING_REVIEWERS, old, this.collaboratingReviewers);
	}

	/**
	 * Add a reviewer to the reviewers list. Notify observers.
	 * 
	 * @param reviewer
	 */
	public void addReviewer(Reviewer reviewer) {
		if (this.reviewers.contains(reviewer)) {
			throw new IllegalStateException("Reviewer already registered");
		}
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.add(reviewer);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	/**
	 * Add a thesis to the theses list. Notify observers.
	 * 
	 * @param thesis
	 */
	public void addThesis(BachelorThesis thesis) {
		if (this.theses.contains(thesis)) {
			throw new IllegalStateException("BachelorThesis already registered");
		}
		List<BachelorThesis> old = new ArrayList<>(this.theses);
		this.theses.add(thesis);
		this.propertyChangeSupport.firePropertyChange(THESES, old, this.theses);
	}

	/**
	 * Search a thesis for the given attributes. If not already existing, create a
	 * new one
	 * 
	 * @param topic         topic attribute of the thesis
	 * @param author        author attribute of the thesis
	 * @param firstreviewer firstreviewer of the thesis
	 * @return BachelorThesis that is linked within the model
	 */
	public Optional<BachelorThesis> findThesis(String topic, Author author, Reviewer firstreviewer) {
		for (BachelorThesis thesis : this.getTheses()) {
			if (thesis.getTopic().equals(topic) && thesis.getAuthor().equals(author)
					&& thesis.getFirstReview().getReviewer().equals(firstreviewer)) {
				return Optional.of(thesis);
			}
		}
		return Optional.empty();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	private void clearReviewers() {
		List<Reviewer> old = this.reviewers;
		this.reviewers = new ArrayList<>();
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	public void clear() {
		this.clearReviewers();
		this.theses = new ArrayList<>();
		this.clearSelectedReviewer();
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	public void clearSelectedReviewer() {
		this.setSelectedReviewer(null);
	}

	public void removeReviewer(Reviewer reviewer) {
		if (!this.reviewers.contains(reviewer)) {
			throw new IllegalStateException("Reviewer does not exist");
		}
		if (reviewer.getTotalReviewCount() > 0) {
			throw new IllegalStateException("Reviewer that supervises theses cannot be deleted");
		}

		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.remove(reviewer);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	public Reviewer getNewestReviewer() {
		if (this.reviewers.isEmpty()) {
			throw new IllegalStateException("No Reviewer has been added yet.");
		}
		return this.reviewers.get(this.reviewers.size() - 1);
	}

	public void overrideReviewers(List<Reviewer> reviewers) {
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.clear();
		this.reviewers.addAll(reviewers);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	public void overrideBachelorTheses(List<BachelorThesis> bachelorTheses) {
		List<BachelorThesis> old = new ArrayList<>(this.theses);
		this.theses.clear();
		this.theses.addAll(bachelorTheses);
		this.propertyChangeSupport.firePropertyChange(THESES, old, this.theses);		
	}

}
