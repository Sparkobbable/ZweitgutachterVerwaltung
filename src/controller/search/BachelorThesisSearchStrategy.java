package controller.search;

import model.domain.BachelorThesis;

public class BachelorThesisSearchStrategy implements SearchStrategy<BachelorThesis> {

	@Override
	public boolean match(BachelorThesis obj, String searchText) {
		String lower = searchText.toLowerCase();
		return obj.getTopic().toLowerCase().contains(lower)
				|| obj.getAuthor().getName().toLowerCase().contains(lower)
				|| obj.getFirstReview().getReviewer().getName().toLowerCase().contains(lower);
	}

}
