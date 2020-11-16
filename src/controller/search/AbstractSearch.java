package controller.search;

import java.util.ArrayList;

public abstract class AbstractSearch<E> {
	protected abstract ArrayList<E> search(ArrayList<E> searchList, String searchText);
}
