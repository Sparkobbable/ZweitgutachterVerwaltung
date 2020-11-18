package controller.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.domain.Review;

public class ReviewSearch extends AbstractSearch<Review> {

	@Override
	protected ArrayList<Review> search(ArrayList<Review> searchList, String searchText) {
		List<Review> collect = searchList.stream().filter(review -> (review.getBachelorThesis().getTopic().toLowerCase().contains(searchText) 
				|| review.getReviewer().getName().toLowerCase().contains(searchText))).collect(Collectors.toList());
		searchList.clear();
		searchList.addAll(collect);
		return searchList;
	}

}
