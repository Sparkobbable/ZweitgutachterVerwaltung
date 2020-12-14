package controller.search;

import model.domain.BachelorThesis;
import model.domain.Review;

/**
 *	Supplies a searching strategy for searching in {@link Review}
 */
public class ReviewSearchStrategy implements SearchStrategy<Review> {

	@Override
	public boolean match(Review obj, String searchText) {
		return SearchStrategy.containsIgnoreCase(obj.getBachelorThesis().getTopic(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getReviewer().getName(), searchText);
	}

}
