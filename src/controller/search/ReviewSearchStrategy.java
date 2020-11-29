package controller.search;

import model.domain.Review;

public class ReviewSearchStrategy implements SearchStrategy<Review> {

	@Override
	public boolean match(Review obj, String searchText) {
		return SearchStrategy.containsIgnoreCase(obj.getBachelorThesis().getTopic(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getReviewer().getName(), searchText);
	}

}
