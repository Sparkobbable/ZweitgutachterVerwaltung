package controller.search;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

import model.domain.BachelorThesis;
import model.domain.FirstReview;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import util.Log;

public class SearchFieldController<E> {

	/**
	 * Searches the given List for entries containing the given String
	 * 
	 * @param searchList ArrayList to be searched, atm. only {@link Review}
	 *                   {@link Reviewer} {@link BachelorThesis} possible
	 * @param searchText The String to search for
	 * @return Returns a List with the found entries
	 */
//	@SuppressWarnings("unchecked")
//	public ArrayList<?> handleSearch(ArrayList<E> searchList, String searchText) {
//		if (Objects.isNull(searchText) || searchText.isBlank()) {
//			return searchList;
//		}
//		Log.info(this.getClass().getName(), "Searching for: %S", searchText);
//		searchText = searchText.toLowerCase();
//		Class<?> clazz = searchList.get(0).getClass();
//		if (Reviewer.class.equals(clazz)) {
//			return new ReviewerSearch().search((ArrayList<Reviewer>) searchList, searchText);
//		} else if (BachelorThesis.class.equals(clazz)) {
//			return new BachelorThesisSearch().search((ArrayList<BachelorThesis>) searchList, searchText);
//		} else if (FirstReview.class.equals(clazz) || SecondReview.class.equals(clazz)) {
//			return new ReviewSearch().search((ArrayList<Review>) searchList, searchText);
//		}
//		// Other searches are possible but need to be implemented by creating a new
//		// subclass of AbstractSearch
//		throw new UnsupportedOperationException(String.format("Cannot handle search on %s objects.", clazz.getName()));
//	}

//	// TODO refactor
//	@SuppressWarnings("unchecked")
//	public Predicate<E> getReviewerFilter(Class<?> clazz, String searchText) {
//		if (Objects.isNull(searchText) || searchText.isBlank()) {
//			return (e) -> true;
//		}
//		Log.info(this.getClass().getName(), "Searching for: %S", searchText);
//		searchText = searchText.toLowerCase();
//		if (Reviewer.class.equals(clazz)) {
//			return (Predicate<E>) new ReviewerSearch().createFilter(searchText);
//		} else if (BachelorThesis.class.equals(clazz)) {
//			return (Predicate<E>) new BachelorThesisSearch().createFilter(searchText);
//		} else if (FirstReview.class.equals(clazz) || SecondReview.class.equals(clazz)) {
//			return (Predicate<E>) new ReviewSearch().createFilter(searchText);
//		}
//		// Other searches are possible but need to be implemented by creating a new
//		// subclass of AbstractSearch
//		throw new UnsupportedOperationException(String.format("Cannot handle search on %s objects.", clazz.getName()));
//	}

}
