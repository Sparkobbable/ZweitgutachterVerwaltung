package controller.search;

import model.domain.Reviewer;

public class ReviewerSearchStrategy implements SearchStrategy<Reviewer> {

	@Override
	public boolean match(Reviewer obj, String searchText) {
		String lower = searchText.toLowerCase();
		return obj.getName().toLowerCase().contains(lower) || obj.getEmail().toLowerCase().contains(lower)
				|| obj.getComment().toLowerCase().contains(lower);
	}

}
