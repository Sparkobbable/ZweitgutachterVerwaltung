package controller.search;

import java.util.ArrayList;
import java.util.Objects;

import model.data.BachelorThesis;
import model.data.FirstReview;
import model.data.Review;
import model.data.Reviewer;
import model.data.SecondReview;

public class SearchFieldController<E> {
	
	/**
	 * Searches the given List for entries containing the given String
	 * @param searchList ArrayList to be searched, atm. only {@link Review} {@link Reviewer} {@link BachelorThesis} possible
	 * @param searchText The String to search for
	 * @return			 Returns a List with the found entries
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<?> handleSearch(ArrayList<E> searchList, String searchText) {
		if (Objects.isNull(searchText) || searchText.isBlank()) {
			return searchList;
		}
		searchText = searchText.toLowerCase();
		Class<?> clazz = searchList.get(0).getClass();
		if (Reviewer.class.equals(clazz)) {
			return new ReviewerSearch().search((ArrayList<Reviewer>) searchList, searchText);
		} else if (BachelorThesis.class.equals(clazz)) {
			return new BachelorThesisSearch().search((ArrayList<BachelorThesis>) searchList, searchText);
		} else if (FirstReview.class.equals(clazz) || SecondReview.class.equals(clazz)) {
			return new ReviewSearch().search((ArrayList<Review>) searchList, searchText);
		}
		return null;
	}
	
}
