package controller.search;

import model.domain.BachelorThesis;
import model.domain.Reviewer;

/**
 *	Supplies a searching strategy for searching in {@link Reviewer}
 */
public class ReviewerSearchStrategy implements SearchStrategy<Reviewer> {

	@Override
	public boolean match(Reviewer obj, String searchText) {
		return SearchStrategy.containsIgnoreCase(obj.getName(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getEmail(), searchText)
				|| SearchStrategy.containsIgnoreCase(obj.getComment(), searchText);
	}

}
