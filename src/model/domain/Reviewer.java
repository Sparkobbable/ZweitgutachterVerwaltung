package model.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.propertychangelistener.ChangeableProperties;

/**
 * Reviewer for supervising {@link BachelorThesis}
 */
public class Reviewer implements ChangeableProperties {

	private static final AtomicInteger INTERNAL_ID_GENERATOR = new AtomicInteger();
	
	protected final PropertyChangeSupport propertyChangeSupport;

	// Descriptors
	public static final String NAME = "name";
	public static final String MAX_SUPERVISED_THESES = "maxSupervisedTheses";
	public static final String FIRST_REVIEWS = "firstReviews";
	public static final String SECOND_REVIEWS = "secondReviews";
	public static final String REJECTED_SECOND_REVIEWS = "rejectedSecondReviews";
	public static final String EMAIL = "email";
	public static final String COMMENT = "comment";
	public static final String OCCUPATION = "occupation";
	public static final String INTERNAL_ID = "internalId";

	// Data
	private String name;
	private String email;
	private String comment;
	private int maxSupervisedTheses;
	private Double occupation;
	private Double firstOccupation;
	private Double secOccupation;
	private int internalId;

	private List<FirstReview> firstReviews;
	private List<SecondReview> secondReviews;
	private List<SecondReview> rejectedSecondReviews;

	/**
	 * Creates a Reviewer for supervising {@link BachelorThesis}
	 * @param name					Name of the reviewer
	 * @param maxSupervisedTheses	Maximum amount of reviewed {@link BachelorThesis}
	 * @param email					Email of the reviewer
	 * @param comment				Comment on the reviewer		
	 * @param internalId			ID for internal purposes	
	 */
	public Reviewer(String name, int maxSupervisedTheses, String email, String comment, int internalId) {
		this(name, maxSupervisedTheses, email, comment);
		this.internalId = internalId;
	}
	
	/**
	 * Creates a Reviewer for supervising {@link BachelorThesis}
	 * @param name					Name of the reviewer
	 * @param maxSupervisedTheses	Maximum amount of reviewed {@link BachelorThesis}
	 * @param email					Email of the reviewer
	 * @param comment				Comment on the reviewer			
	 */
	public Reviewer(String name, int maxSupervisedTheses, String email, String comment) {
		this(name, maxSupervisedTheses);
		this.email = email;
		this.comment = comment;
	}

	/**
	 * Creates a Reviewer for supervising {@link BachelorThesis}
	 * @param name					Name of the reviewer
	 * @param maxSupervisedTheses	Maximum amount of reviewed {@link BachelorThesis}		
	 */
	public Reviewer(String name, int maxSupervisedTheses) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.firstReviews = new ArrayList<>();
		this.secondReviews = new ArrayList<>();
		this.rejectedSecondReviews = new ArrayList<>();
		this.name = name;
		this.maxSupervisedTheses = maxSupervisedTheses;
	}

	public String getName() {
		return this.name;
	}

	public int getMaxSupervisedThesis() {
		return maxSupervisedTheses;
	}

	public String getEmail() {
		return this.email;
	}

	public String getComment() {
		return this.comment;
	}

	/**
	 * Lists all supervised {@link Review}, containing both first and second reviews
	 * @return {@link List} of all {@link Review}
	 */
	public List<Review> getAllSupervisedReviews() {
		return Stream.concat(this.firstReviews.stream(), this.getUnrejectedSecondReviews().stream())
				.collect(Collectors.toList());
	}

	/**
	 * @return The total count of {@link FirstReview} and {@link SecondReview} that were not
	 *         rejected
	 */
	public int getTotalReviewCount() {
		return this.firstReviews.size() + this.getUnrejectedSecondReviews().size();
	}
	
	/**
	 * @return The count of {@link FirstReview}
	 */
	public int getFirstReviewCount() {
		return this.firstReviews.size();
	}
	
	/**
	 * @return The count of {@link SecondReview} that were not rejected
	 */
	public int getSecondReviewCount() {
		return this.secondReviews.size();
	}
	
	/**
	 * @return The count of {@link SecondReview} that were approved
	 */
	public int getApprovedSecondReviewCount() {
		return (int) this.secondReviews.stream().filter(SecondReview::isApproved).count();
	}
	
	/**
	 * Counts how many {@link Review} this reviewer has, where the reviewer is set to this reviewer
	 * @param optional Reviewer to be checked
	 * @return		   Count of {@link Review}
	 */
	public int getReviewsWithSelectedReviewerCount(Optional<Reviewer> optional) {
		int count = 0;
		if(optional.isPresent()) {
			for(Review r : this.firstReviews) {
				if(r.getReviewer().equals(optional.get())) {
					count++;
				}
			}
			for(Review r : this.secondReviews) {
				if(r.getReviewer().equals(optional.get())) {
					count++;
				}
			}
		}
		return count;
		
	}

	public Double getOccupation() {
		return occupation;
	}

	public List<SecondReview> getUnrejectedSecondReviews() {
		return Collections.unmodifiableList(this.secondReviews);
	}

	/**
	 * @return All {@link BachelorThesis} that are second-reviewed by this reviewer and not
	 *         rejected
	 * @deprecated replaced by {@link #getUnrejectedSecondReviews()}
	 */
	public List<SecondReview> getSecondReviews() {
		return this.getUnrejectedSecondReviews();
	}

	/**
	 * @return All {@link BachelorThesis} that are second-reviewed by this reviewer. This
	 *         includes rejected reviews.
	 */
	public List<SecondReview> getAllSecondReviews() {
		return Stream.concat(this.secondReviews.stream(), this.rejectedSecondReviews.stream())
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return All {@link BachelorThesis} that are first-reviewed by this reviewer
	 * 
	 */
	public List<FirstReview> getFirstReviews() {
		return Collections.unmodifiableList(this.firstReviews);
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.propertyChangeSupport.firePropertyChange(NAME, old, name);
	}

	public void setEmail(String email) {
		String old = this.email;
		this.email = email;
		this.propertyChangeSupport.firePropertyChange(EMAIL, old, email);
	}

	public void setComment(String comment) {
		String old = this.comment;
		this.comment = comment;
		this.propertyChangeSupport.firePropertyChange(COMMENT, old, comment);
	}

	public void setMaxSupervisedTheses(int maxSupervisedThesis) {
		int old = this.maxSupervisedTheses;
		this.maxSupervisedTheses = maxSupervisedThesis;
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(MAX_SUPERVISED_THESES, old, maxSupervisedThesis);
	}

	/**
	 * Add a {@link FirstReview} to this Reviewer.
	 * 
	 * @param review	The {@link FirstReview} to be added
	 */
	void addFirstReviewerReview(FirstReview review) {
		ArrayList<Review> old = new ArrayList<>(this.firstReviews);
		this.firstReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEWS, old, this.firstReviews);
	}

	/**
	 * Add a {@link SecondReview} to this Reviewer.
	 * 
	 * @param review	The {@link SecondReview} to be added
	 */
	public void addSecondReview(SecondReview review) {
		ArrayList<Review> old = new ArrayList<>(this.secondReviews);
		this.secondReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEWS, old, this.secondReviews);
	}

	/**
	 * Add a rejected {@link SecondReview} to this Reviewer.
	 * 
	 * @param review	The rejected {@link SecondReview} to be added
	 */
	public void addRejectedSecondReview(SecondReview review) {
		ArrayList<Review> old = new ArrayList<>(this.rejectedSecondReviews);
		this.rejectedSecondReviews.add(review);
		this.propertyChangeSupport.firePropertyChange(REJECTED_SECOND_REVIEWS, old, this.rejectedSecondReviews);
	}

	/**
	 * Removes the given review
	 * @param review	{@link SecondReview} to be removed
	 */
	public void removeSecondReview(SecondReview review) {
		ArrayList<SecondReview> old = new ArrayList<>(this.secondReviews);
		this.secondReviews.remove(review);
		updateOppucation();
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEWS, old, this.secondReviews);
	}

	/**
	 * Removes the given rejected review
	 * @param review	Rejected {@link SecondReview} to be removed
	 */
	public void removeRejectedSecondReview(SecondReview review) {
		ArrayList<SecondReview> old = new ArrayList<>(this.rejectedSecondReviews);
		this.rejectedSecondReviews.remove(review);
		this.propertyChangeSupport.firePropertyChange(REJECTED_SECOND_REVIEWS, old, this.rejectedSecondReviews);
	}

	private void updateOppucation() {
		Double old = this.occupation;
		if (this.maxSupervisedTheses == 0) {
			this.occupation = Double.valueOf(100);
			this.firstOccupation = null;
			this.secOccupation = null;
		}
		this.occupation = (double) (this.firstReviews.size() + this.secondReviews.size()) / this.maxSupervisedTheses;
		this.firstOccupation = (double) this.firstReviews.size() / this.maxSupervisedTheses;
		this.secOccupation = (double) this.secondReviews.size() / this.maxSupervisedTheses;
		this.propertyChangeSupport.firePropertyChange(OCCUPATION, old, this.occupation);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * Checks whether this reviewer reviews the given {@link BachelorThesis}
	 * 
	 * @param thesis	The {@link BachelorThesis} to be checked
	 * @return 			True if and only if this Reviewer reviews the given {@link BachelorThesis}
	 */
	public boolean reviewsThesis(BachelorThesis thesis) {
		return this.getAllSupervisedReviews().stream().map(Review::getBachelorThesis).anyMatch(t -> t == thesis);
	}

	@Override
	public String toString() {
		return String.format("%s (%d/%d)", this.name, this.getTotalReviewCount(), this.getMaxSupervisedThesis());
	}

	public Double getFirstOccupation() {
		return this.firstOccupation;
	}

	public Double getSecOccupation() {
		return this.secOccupation;
	}
	
	public int getInternalId() {
		return internalId;
	}

	public List<SecondReview> getRejectedSecondReviews() {
		return Collections.unmodifiableList(this.rejectedSecondReviews);
	}
	
	/**
	 * Checks whether this reviewer is in the given {@link List} of reviewers
	 * 
	 * @param reviewerList	The {@link List} to search in
	 * @return 				True if and only if this reviewer is in the given {@link List}
	 */
	public boolean isPresentInList(List<Reviewer> reviewerList) {
		return reviewerList.stream().filter(reviewerInList -> reviewerInList.getName().equals(this.getName())).findAny().isPresent();
	}

}
