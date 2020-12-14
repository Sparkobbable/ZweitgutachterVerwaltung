package controller.search;

/**
 *	Interface containing methods for general searching
 * @param <E> The Class of the Object to be searched
 */
public interface SearchStrategy<E> {

	public boolean match(E obj, String searchText);

	public static boolean containsIgnoreCase(String attribute, String searchText) {
		if (attribute == null) {
			return false;
		}
		if (searchText == null) {
			return true;
		}
		return attribute.toLowerCase().contains(searchText.toLowerCase());
	}
}
