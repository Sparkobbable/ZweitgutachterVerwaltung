package controller.search;

import model.domain.Reviewer;

public class ReviewerSearchStrategy implements SearchStrategy<Reviewer> {

	@Override
	public boolean match(Reviewer obj, String searchText) {
		return SearchStrategy.containsIgnoreCase(obj.getName(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getEmail(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getComment(), searchText);
	}

}
