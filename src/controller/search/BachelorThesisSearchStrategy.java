package controller.search;

import model.domain.BachelorThesis;

public class BachelorThesisSearchStrategy implements SearchStrategy<BachelorThesis> {

	@Override
	public boolean match(BachelorThesis obj, String searchText) {
		return SearchStrategy.containsIgnoreCase(obj.getTopic(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getAuthor().getName(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getFirstReview().getReviewer().getName(), searchText);
	}

}
