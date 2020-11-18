package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.ChangeableProperties;
import model.enums.CascadeMode;
import model.enums.ReviewType;

public class Reviewer implements ChangeableProperties {

	protected final PropertyChangeSupport propertyChangeSupport;

	// Descriptors
	public static final String NAME = "name";
	public static final String MAX_SUPERVISED_THESES = "maxSupervisedTheses";
	public static final String FIRST_REVIEWS = "firstReviews";
	public static final String SECOND_REVIEWS = "secondReviews";
	public static final String EMAIL = "email";
	public static final String COMMENT = "comment";

	// Data
	private String name;
	private String email;
	private String comment;
	private int maxSupervisedThesis;
	private float occupation;

	private List<FirstReview> firstReviews;
	private List<SecondReview> secondReviews;

	/**
	 * Creates a Reviewer for BachelorThesis
	 * 
	 */
	public Reviewer(String name, int maxSupervisedThesis, String email, String comment) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.firstReviews = new ArrayList<>();
		this.secondReviews = new ArrayList<>();
		this.name = name;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.email = email;
		this.comment = comment;
	}

	public String getName() {
		return this.name;
	}

	public int getMaxSupervisedThesis() {
		return maxSupervisedThesis;
	}

	public String getEmail() {
		return this.email;
	}

	public String getComment() {
		return this.comment;
	}

	public List<Review> getAllReviews() {
		return Stream.concat(this.firstReviews.stream(), this.secondReviews.stream()).collect(Collectors.toList());
	}

	public int getTotalReviewCount() {
		return this.firstReviews.size() + this.secondReviews.size();
	}

	public float getOccupation() {
		return occupation;
	}

	/**
	 * @return all bachelor theses that are second-reviewed by this reviewer
	 */
	public List<SecondReview> getSecondReviews() {
		return this.secondReviews;
	}

	/**
	 * 
	 * @return all bachelor theses that are first-reviewed by this reviewer
	 * 
	 */
	public List<FirstReview> getFirstReviews() {
		return this.firstReviews;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.propertyChangeSupport.firePropertyChange(NAME, old, name);
	}

	public void setEmail(String email) {
		String old = this.email;
		this.email = name;
		this.propertyChangeSupport.firePropertyChange(EMAIL, old, email);
	}

	public void setComment(String comment) {
		String old = this.comment;
		this.comment = name;
		this.propertyChangeSupport.firePropertyChange(COMMENT, old, comment);
	}

	public void setMaxSupervisedThesis(int maxSupervisedThesis) {
		int old = this.maxSupervisedThesis;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(MAX_SUPERVISED_THESES, old, maxSupervisedThesis);
	}

	/**
	 * Adds a bachelorThesis to this Reviewer according to the given ReviewType.
	 * 
	 * @param bachelorThesis
	 * @param reviewType
	 */
	public void addBachelorThesis(BachelorThesis bachelorThesis, ReviewType reviewType) {
		if (reviewType == ReviewType.FIRST_REVIEW) {
			this.addFirstReviewerReview(new FirstReview(this, bachelorThesis), CascadeMode.CASCADE);
		} else if (reviewType == ReviewType.SECOND_REVIEW) {
			this.addSecondReviewerReview(new SecondReview(this, bachelorThesis), CascadeMode.CASCADE);
		} else {
			throw new IllegalArgumentException(String.format(
					"ReviewType must be one of FIRST_REVIEW or SECOND_REVIEW, but was %s. Review cannot be added to Reviewer.",
					reviewType));
		}
	}

	/**
	 * Deletes a collection of reviews.
	 * 
	 * @param reviews
	 */
	public void deleteReviews(Collection<Review> reviews) {
		reviews.forEach(this::deleteReview);
	}

	/**
	 * Delete a review from this reviewer.
	 * 
	 * @param review
	 */
	public void deleteReview(Review review) {
		if (review.getReviewType() == ReviewType.FIRST_REVIEW) {
			this.deleteFirstReview((FirstReview) review);
		} else if (review.getReviewType() == ReviewType.SECOND_REVIEW) {
			this.deleteSecondReview((SecondReview) review);
		}
	}

	/**
	 * Add a Review to this Reviewer. If the given {@link CascadeMode} is set to
	 * CASCADE, also adds the review to the BachelorThesis referenced within the
	 * Review.
	 * 
	 * @param review
	 * @param cascadeMode
	 */
	void addReview(Review review, CascadeMode cascadeMode) {
		if (review.getReviewType() == ReviewType.FIRST_REVIEW) {
			this.addFirstReviewerReview((FirstReview) review, cascadeMode);
		} else if (review.getReviewType() == ReviewType.SECOND_REVIEW) {
			this.addSecondReviewerReview((SecondReview) review, cascadeMode);
		} else {
			throw new IllegalArgumentException(String.format(
					"ReviewType must be one of FIRST_REVIEW or SECOND_REVIEW, but was %s. Review cannot be added to Reviewer.",
					review.getReviewType()));
		}
	}

	/**
	 * Add a FirstReview to this Reviewer. If the given CascadeMode is set to
	 * CASCADE, also adds the review to the BachelorThesis referenced within
	 * theReview.
	 * 
	 * @see #addReview(Review, CascadeMode)
	 * 
	 * @param review
	 * @param cascadeMode
	 */
	void addFirstReviewerReview(FirstReview review, CascadeMode cascadeMode) {
		ArrayList<Review> old = new ArrayList<>(this.firstReviews);
		this.firstReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEWS, old, this.firstReviews);
		if (cascadeMode == CascadeMode.CASCADE) {
			review.getBachelorThesis().setFirstReview(review, CascadeMode.STOP);
		}
	}

	/**
	 * Add a SecondReview to this Reviewer. If the given CascadeMode is set to
	 * CASCADE, also adds the review to the BachelorThesis referenced within
	 * theReview.
	 * 
	 * @see #addReview(Review, CascadeMode)
	 * 
	 * @param review
	 * @param cascadeMode
	 */
	void addSecondReviewerReview(SecondReview review, CascadeMode cascadeMode) {
		ArrayList<Review> old = new ArrayList<>(this.secondReviews);
		this.secondReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEWS, old, this.secondReviews);
		if (cascadeMode == CascadeMode.CASCADE) {
			review.getBachelorThesis().setSecondReview(review, CascadeMode.STOP);
		}
	}

	private void deleteFirstReview(FirstReview review) {
		ArrayList<FirstReview> old = new ArrayList<>(this.firstReviews);
		this.firstReviews.remove(review);
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEWS, old, this.firstReviews);
	}

	private void deleteSecondReview(SecondReview review) {
		ArrayList<SecondReview> old = new ArrayList<>(this.secondReviews);
		this.secondReviews.remove(review);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEWS, old, this.secondReviews);
	}

	private void updateOppucation() {
		this.occupation = (float) (this.firstReviews.size() + this.secondReviews.size()) / this.maxSupervisedThesis;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	/**
	 * 
	 * @param thesis
	 * @return True if and only if this Reviewer reviews the given thesis as first
	 *         or second reviewer
	 */
	public boolean reviewsThesis(BachelorThesis thesis) {
		return this.getAllReviews().stream().map(Review::getBachelorThesis).anyMatch(t -> t == thesis);
	}

	/**
	 * 
	 * @param bachelorThesis
	 * @param reviewType
	 */
	public void removeSecondReviewBachelorThesis(BachelorThesis bachelorThesis) {
		this.getSecondReviews().stream().filter(r -> r.getBachelorThesis() == bachelorThesis).findAny()
				.ifPresent(this::deleteReview);

	}

}
