package controller.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.data.BachelorThesis;

public class BachelorThesisSearch extends AbstractSearch<BachelorThesis> {

	@Override
	protected ArrayList<BachelorThesis> search(ArrayList<BachelorThesis> searchList, String searchText) {
		List<BachelorThesis> collect = searchList.stream().filter(thesis -> (thesis.getTopic().toLowerCase().contains(searchText))
				|| (thesis.getAuthor().getName().toLowerCase().contains(searchText))
				|| (thesis.getFirstReview().getReviewer().getName().toLowerCase().contains(searchText))).collect(Collectors.toList());
		searchList.clear();
		searchList.addAll(collect);
		return searchList;
	}

}
