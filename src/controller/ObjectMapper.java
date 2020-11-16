package controller;

import java.util.List;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import model.Model;
import model.data.Author;
import model.data.BachelorThesis;
import model.data.FirstReview;
import model.data.Reviewer;
import model.data.SecondReview;
import model.enums.ReviewType;

public class ObjectMapper {

	private Model model;

	public ObjectMapper(Model model) {
		this.model = model;
	}

	/**
	 * Create all objects from the JsonArray in the model.
	 * 
	 * @param value - Jsonvalue from the Json file
	 */
	public void createObjectsFromJson(JsonArray reviewers, JsonArray bachelorTheses) {
		for (JsonValue set : reviewers) {
			JsonObject jReviewer = set.asJsonObject();

			Reviewer reviewer = new Reviewer(jReviewer.getString("name"), jReviewer.getInt("maxSupervisedThesis"),
					jReviewer.getString("email"), jReviewer.getString("comment"));
			this.model.addReviewer(reviewer);
		}
		
		for (JsonValue set : bachelorTheses) {
			JsonObject jThesis = set.asJsonObject();
			JsonObject jAuthor = jThesis.getJsonObject("author");

			Author author = new Author(jAuthor.getString("name"), jAuthor.getString("studyGroup"));
			Reviewer firstReviewer = this.model.findReviewerByName(jThesis.getString("firstReviewer")).orElseThrow();
			BachelorThesis newThesis = new BachelorThesis(jThesis.getString("topic"), author, firstReviewer);
			try {
				this.model.findReviewerByName(jThesis.getString("secondReviewer")).ifPresent(secondReviewer -> newThesis.setSecondReviewer(secondReviewer));
				this.model.addThesis(newThesis);
			} catch(NullPointerException e) {
				
			}
		}
		
	}

	public JsonObject createJsonFromObject() {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder reviewers = Json.createArrayBuilder();
		List<Reviewer> reviewerlist = this.model.getReviewers();
		for (Reviewer r : reviewerlist) {
			JsonObjectBuilder reviewerbuilder = Json.createObjectBuilder();
			reviewerbuilder.add("name", r.getName());
			reviewerbuilder.add("email", r.getEmail());
			reviewerbuilder.add("comment", r.getComment());
			reviewerbuilder.add("maxSupervisedThesis", r.getMaxSupervisedThesis());
			reviewerbuilder.add("occupation", r.getOccupation());

			reviewers.add(reviewerbuilder);
		}
		builder.add("reviewers", reviewers);
		
		JsonArrayBuilder theses = Json.createArrayBuilder();
		List<BachelorThesis> thesislist = this.model.getTheses();
		for(BachelorThesis thesis : thesislist) {
			JsonObjectBuilder thesisBuilder = Json.createObjectBuilder();
			thesisBuilder.add("topic", thesis.getTopic());
			
			JsonObjectBuilder authorBuilder = Json.createObjectBuilder();
			authorBuilder.add("name", thesis.getAuthor().getName());
			authorBuilder.add("studyGroup", thesis.getAuthor().getStudyGroup());
			thesisBuilder.add("author", authorBuilder);
			
			thesisBuilder.add("firstReviewer", thesis.getFirstReview().getReviewer().getName());
			thesis.getSecondReview().ifPresent(review -> thesisBuilder.add("secondReviewer", review.getReviewer().getName()));
			thesis.getSecondReview().ifPresent(review -> thesisBuilder.add("secondReviewStatus", review.getStatus().toString()));
			theses.add(thesisBuilder);
		}
		builder.add("bachelorTheses", theses);
		return builder.build();
	}
}
