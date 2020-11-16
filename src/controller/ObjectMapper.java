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
	public void createObjectsFromJson(JsonArray reviewers) {
		for (JsonValue set : reviewers) {
			JsonObject jReviewer = set.asJsonObject();

			Reviewer reviewer = new Reviewer(jReviewer.getString("name"), jReviewer.getInt("maxSupervisedThesis"),
					jReviewer.getString("email"), jReviewer.getString("comment"));
			this.model.addReviewer(reviewer);
		}
		
		for (JsonValue set : reviewers) {
			JsonObject jReviewer = set.asJsonObject();
			Reviewer reviewer = this.model.findReviewerByName(jReviewer.getString("name")).orElseThrow();
			for (JsonValue firstReview : jReviewer.getJsonArray("firstReviews")) {
				JsonObject jfR = firstReview.asJsonObject();
				JsonObject jThesis = jfR.getJsonObject("bachelorThesis");
				JsonObject jAuthor = jThesis.getJsonObject("author");

				Author author = new Author(jAuthor.getString("name"), jAuthor.getString("studyGroup"));
				BachelorThesis thesis = this.model.findThesis(jThesis.getString("topic"), author, reviewer);
				reviewer.addBachelorThesis(thesis, ReviewType.FIRST_REVIEW);
			}
			for (JsonValue secondReview : jReviewer.getJsonArray("secondReviews")) {
				JsonObject jsR = secondReview.asJsonObject();
				JsonObject jThesis = jsR.getJsonObject("bachelorThesis");
				JsonObject jAuthor = jThesis.getJsonObject("author");

				Author author = new Author(jAuthor.getString("name"), jAuthor.getString("studyGroup"));
				Optional<Reviewer> firstReviewer = this.model.findReviewerByName(jThesis.getString("firstReviewer"));
				if (firstReviewer.isEmpty()) {
					throw new IllegalArgumentException("Missing firstReviewer in secondReview");
				}
				BachelorThesis thesis = this.model.findThesis(jThesis.getString("topic"), author, firstReviewer.get());
				thesis.setSecondReviewer(reviewer);
				reviewer.addBachelorThesis(thesis, ReviewType.SECOND_REVIEW);
			}
		}
	}

	public JsonObject createJsonFromObject(List<Reviewer> list) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder reviewers = Json.createArrayBuilder();
		for (Reviewer r : list) {
			JsonObjectBuilder reviewerbuilder = Json.createObjectBuilder();
			reviewerbuilder.add("name", r.getName());
			reviewerbuilder.add("email", r.getEmail());
			reviewerbuilder.add("comment", r.getComment());
			reviewerbuilder.add("maxSupervisedThesis", r.getMaxSupervisedThesis());
			reviewerbuilder.add("occupation", r.getOccupation());

			JsonArrayBuilder firstReviews = Json.createArrayBuilder();
			for (FirstReview fR : r.getFirstReviews()) {
				JsonObjectBuilder firstReviewBuilder = Json.createObjectBuilder();
				firstReviewBuilder.add("reviewer", fR.getReviewer().getName());

				JsonObjectBuilder thesisBuilder = Json.createObjectBuilder();
				thesisBuilder.add("topic", fR.getBachelorThesis().getTopic());

				JsonObjectBuilder authorBuilder = Json.createObjectBuilder();
				authorBuilder.add("name", fR.getBachelorThesis().getAuthor().getName());
				authorBuilder.add("studyGroup", fR.getBachelorThesis().getAuthor().getStudyGroup());
				thesisBuilder.add("author", authorBuilder);
				thesisBuilder.add("firstReviewer", fR.getReviewer().getName());
				fR.getBachelorThesis().getSecondReview()
						.ifPresent(sR -> thesisBuilder.add("secondReviewer", sR.getReviewer().getName()));
				firstReviewBuilder.add("bachelorThesis", thesisBuilder);
				firstReviews.add(firstReviewBuilder);
			}
			reviewerbuilder.add("firstReviews", firstReviews);

			JsonArrayBuilder secondReviews = Json.createArrayBuilder();
			for (SecondReview sR : r.getSecondReviews()) {
				JsonObjectBuilder secondReviewBuilder = Json.createObjectBuilder();
				secondReviewBuilder.add("reviewer", sR.getReviewer().getName());

				JsonObjectBuilder thesisBuilder = Json.createObjectBuilder();
				thesisBuilder.add("topic", sR.getBachelorThesis().getTopic());

				JsonObjectBuilder authorBuilder = Json.createObjectBuilder();
				authorBuilder.add("name", sR.getBachelorThesis().getAuthor().getName());
				authorBuilder.add("studyGroup", sR.getBachelorThesis().getAuthor().getStudyGroup());

				thesisBuilder.add("author", authorBuilder);
				thesisBuilder.add("firstReviewer", sR.getBachelorThesis().getFirstReview().getReviewer().getName());
				thesisBuilder.add("secondReviewer", sR.getReviewer().getName());
				secondReviewBuilder.add("bachelorThesis", thesisBuilder);
				secondReviews.add(secondReviewBuilder);
			}
			reviewerbuilder.add("secondReviews", secondReviews);
			reviewers.add(reviewerbuilder);
		}
		builder.add("reviewers", reviewers);
		return builder.build();
	}
}
