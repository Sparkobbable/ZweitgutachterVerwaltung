package controller.search;

public interface SearchStrategy<E> {

	public boolean match(E obj, String searchText);
}
