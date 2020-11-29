package controller.search;

import model.domain.Review;

public class ReviewSearchStrategy implements SearchStrategy<Review> {

	@Override
	public boolean match(Review obj, String searchText) {
		String lower = searchText.toLowerCase();
		return obj.getBachelorThesis().getTopic().toLowerCase().contains(lower)
				|| obj.getReviewer().getName().toLowerCase().contains(lower);
	}

}
