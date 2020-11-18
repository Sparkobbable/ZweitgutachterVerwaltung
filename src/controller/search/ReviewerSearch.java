package controller.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.domain.Reviewer;

public class ReviewerSearch extends AbstractSearch<Reviewer> {

	@Override
	protected ArrayList<Reviewer> search(ArrayList<Reviewer> searchList, String searchText) {
		List<Reviewer> collect = searchList.stream().filter(reviewer -> (reviewer.getName().toLowerCase().contains(searchText) 
				|| reviewer.getEmail().toLowerCase().contains(searchText) 
				|| reviewer.getComment().toLowerCase().contains(searchText))).collect(Collectors.toList());
		searchList.clear();
		searchList.addAll(collect);
		return searchList;
	}

}
