package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.domain.Author;
import model.domain.BachelorThesis;
import model.domain.FirstReview;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ApplicationState;

/**
 * Main data access.
 * This class stores all data starting with a lists of {@link Reviewer} and {@link BachelorThesis}.
 * It also features several methods for accessing the data.
 */
public class Model implements ChangeableProperties {

	public static final String APPLICATION_STATE = "applicationState";
	public static final String SELECTED_REVIEWER = "selectedReviewer";
	public static final String SINGLE_REVIEWS = "singleReviews";
	public static final String ANALYSE_REVIEWERS = "analyseReviewers";
	public static final String COLLABORATING_REVIEWERS = "collaboratingReviewers";
	public static final String REVIEWERS = "reviewers";
	public static final String THESES = "theses";

	protected final PropertyChangeSupport propertyChangeSupport;

	private List<BachelorThesis> theses;
	private List<Reviewer> reviewers;

	private Pair<Optional<Integer>, Optional<Integer>> singleReviews;
	private Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> analyseReviewers;
	private Map<Reviewer, Double> collaboratingReviewers;
	private Optional<Reviewer> selectedReviewer;
	private ApplicationState applicationState;

	/**
	 * Creates a new Model based on the given values
	 * @param theses	{@link BachelorThesis} to be stored
	 * @param reviewers	{@link Reviewer} to be stored
	 */
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
		this.singleReviews = new Pair<>(Optional.empty(), Optional.empty());
		this.selectedReviewer = Optional.empty();
		this.analyseReviewers = new HashMap<>();
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
		return this.applicationState;
	}

	/**
	 * @return The selected Reviewer, if any exist
	 */
	public Optional<Reviewer> getSelectedReviewer() {
		return this.selectedReviewer;
	}

	/**
	 * @return The single reviews from all {@link Review}
	 */
	public Pair<Optional<Integer>, Optional<Integer>> getSingleReviews() {
		return this.singleReviews;
	}

	/**
	 * Return a map of all collaborating {@link Reviewer} for the selected Reviewer
	 * 
	 * @return Map<Reviewer,Double> Collaborating {@link Reviewer} and its size
	 */
	public Map<Reviewer, Double> getCollaboratingReviewers() {
		return this.collaboratingReviewers;
	}

	/**
	 * Return a map of all {@link Reviewer} for the current analyse including the
	 * amount of their {@link FirstReview} and/ or {@link SecondReview}
	 * 
	 * @return analyseReviewers<Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>>
	 */
	public Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> getAnalyseReviewers() {
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
	 * @param applicationState<ApplicationState>
	 */
	public void setApplicationState(ApplicationState applicationState) {
		ApplicationState old = this.applicationState;
		this.applicationState = applicationState;
		this.propertyChangeSupport.firePropertyChange(APPLICATION_STATE, old, this.applicationState);
	}

	/**
	 * Set the selected Reviewer and notify any observers
	 * 
	 * @param selectedReviewer<Reviewer>
	 */
	public void setSelectedReviewer(Reviewer selectedReviewer) {
		Optional<Reviewer> old = this.selectedReviewer;
		this.selectedReviewer = Optional.ofNullable(selectedReviewer);
		this.propertyChangeSupport.firePropertyChange(SELECTED_REVIEWER, old, this.selectedReviewer);
	}

	/**
	 * Set the single Reviews for the selected Reviewer.
	 * 
	 * @param selectedReviewer<Pair<Optional<Integer>, Optional<Integer>>
	 */
	public void setSingleReviews(Pair<Optional<Integer>, Optional<Integer>> reviews) {
		Pair<Optional<Integer>, Optional<Integer>> old = this.singleReviews;
		this.singleReviews = reviews;
		this.propertyChangeSupport.firePropertyChange(SINGLE_REVIEWS, old, this.singleReviews);
	}

	/**
	 * Set the analysis of Reviewers for the current Analyse and notify any
	 * observers
	 * 
	 * @param Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>>
	 */
	public void setAnalyseReviewers(Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> list) {
		Map<Reviewer, Pair<Optional<Integer>, Optional<Integer>>> old = this.analyseReviewers;
		this.analyseReviewers = list;
		this.propertyChangeSupport.firePropertyChange(ANALYSE_REVIEWERS, old, this.analyseReviewers);
	}

	/**
	 * Set the collaborating Reviewers for the selected Reviewer and notify any
	 * observers
	 * 
	 * @param list<HashMap<Reviewer, Double>
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
		if (reviewer.isPresentInList(this.reviewers)) {
			return;
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
		if (thesis.isThesisInList(this.theses)) {
			return;
		}
		List<BachelorThesis> old = new ArrayList<>(this.theses);
		this.theses.add(thesis);
		this.propertyChangeSupport.firePropertyChange(THESES, old, this.theses);
	}

	/**
	 * Search a thesis for the given attributes. If not already existing, create a
	 * new one
	 * 
	 * @param topic        	 	topic attribute of the thesis
	 * @param author        	author attribute of the thesis
	 * @param firstreviewer 	firstreviewer of the thesis
	 * @return BachelorThesis 	that is linked within the model
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

	/**
	 * Clears the list of {@link Reviewer} and the {@link #selectedReviewer}
	 */
	public void clear() {
		this.clearReviewers();
		this.theses = new ArrayList<>();
		this.clearSelectedReviewer();
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

	/**
	 * Clears the {@link #selectedReviewer}
	 */
	public void clearSelectedReviewer() {
		this.setSelectedReviewer(null);
	}

	/**
	 * Removes a {@link Reviewer}, if existing and not supervising a {@link BachelorThesis}
	 * @param reviewer	The {@link Reviewer} to be removed
	 */
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

	/**
	 * Search for the most lately added {@link Reviewer}, if present
	 * @return	The most lately added {@link Reviewer}
	 */
	public Reviewer getNewestReviewer() {
		if (this.reviewers.isEmpty()) {
			throw new IllegalStateException("No Reviewer has been added yet.");
		}
		return this.reviewers.get(this.reviewers.size() - 1);
	}

	/**
	 * Overrides (sets) the list of {@link Reviewer}
	 * @param reviewers	The new list of {@link Reviewer}
	 */
	public void overrideReviewers(List<Reviewer> reviewers) { 
		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.clear();
		this.reviewers.addAll(reviewers);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	/**
	 * Overrides (sets) the list of {@link BachelorThesis}
	 * @param bachelorTheses	The new list of {@link BachelorThesis}
	 */
	public void overrideBachelorTheses(List<BachelorThesis> bachelorTheses) {
		List<BachelorThesis> old = new ArrayList<>(this.theses);
		this.theses.clear();
		this.theses.addAll(bachelorTheses);
		this.propertyChangeSupport.firePropertyChange(THESES, old, this.theses);
	}

	/**
	 * Adds a list of {@link Reviewer} to the list
	 * @param reviewers	The new list of {@link Reviewer} to be added
	 */
	public void addReviewers(List<Reviewer> reviewers) {
		reviewers.forEach(this::addReviewer);
	}

	/**
	 * Adds a list of {@link BachelorThesis} to the list
	 * @param bachelortheses	The new list of {@link BachelorThesis} to be added
	 */
	public void addBachelorTheses(List<BachelorThesis> bachelortheses) {
		bachelortheses.forEach(this::addThesis);
	}

}
