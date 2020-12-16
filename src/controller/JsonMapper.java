package controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import model.domain.Author;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ReviewStatus;

/**
 * Maps JsonObjects to domain model objects.
 *
 */
public class JsonMapper {

	static final String REVIEWERS = "reviewers";
	static final String BACHELOR_THESES = "bachelorTheses";

	private static final String FIRST_REVIEWER = "firstReviewer";
	private static final String SECOND_REVIEWER = "secondReviewer";
	private static final String SECOND_REVIEW_STATUS = "secondReviewStatus";
	private static final String EMPTY_STRING = "";

	/**
	 * Create all objects from the JsonArray in the model.
	 * 
	 * @param value - Jsonvalue from the Json file
	 */
	public void createObjectsFromJson(JsonArray reviewers, JsonArray bachelorTheses) {
		List<Reviewer> mapToReviewers = mapToReviewers(reviewers);

		mapToBachelorTheses(bachelorTheses, mapToReviewers);

	}

	public static JsonObject mapToJson(List<Reviewer> reviwers, List<BachelorThesis> bachelorTheses) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add(REVIEWERS, mapToReviewersJson(reviwers));
		builder.add(BACHELOR_THESES, mapToBachelorThesesJson(bachelorTheses));
		return builder.build();
	}

	public static List<BachelorThesis> mapToBachelorTheses(JsonArray bachelorThesesJson, List<Reviewer> reviewers) {
		return bachelorThesesJson.stream().map(t -> mapToBachelorThesis(t.asJsonObject(), reviewers))
				.collect(Collectors.toList());
	}

	public static List<Reviewer> mapToReviewers(JsonArray reviewers) {
		return reviewers.stream().map(JsonMapper::mapToReviewer).collect(Collectors.toList());
	}

	private static BachelorThesis mapToBachelorThesis(JsonObject jThesis, List<Reviewer> reviewers) {
		Author author = mapToAuthor(jThesis.getJsonObject(BachelorThesis.AUTHOR));
		Reviewer firstReviewer = findReviewerById(jThesis.getInt(FIRST_REVIEWER), reviewers);
		BachelorThesis bachelorThesis = new BachelorThesis(nullableString(jThesis, BachelorThesis.TOPIC), author,
				firstReviewer, nullableString(jThesis, BachelorThesis.COMMENT));

		if (jThesis.containsKey(SECOND_REVIEWER)) {
			Reviewer secondReviewer = findReviewerById(jThesis.getInt(SECOND_REVIEWER), reviewers);
			ReviewStatus reviewStatus = ReviewStatus.valueOf(jThesis.getString(SECOND_REVIEW_STATUS));
			SecondReview secondReview = new SecondReview(secondReviewer, bachelorThesis, reviewStatus);
			secondReviewer.addSecondReview(secondReview);
			bachelorThesis.setSecondReview(secondReview);
		}
		return bachelorThesis;
	}

	private static Author mapToAuthor(JsonObject jAuthor) {
		return new Author(nullableString(jAuthor, Author.NAME), nullableString(jAuthor, Author.STUDY_GROUP),
				nullableString(jAuthor, Author.COMPANY));
	}

	private static Reviewer mapToReviewer(JsonValue jsonValue) {
		JsonObject jReviewer = jsonValue.asJsonObject();
		return new Reviewer(jReviewer.getString(Reviewer.NAME), jReviewer.getInt(Reviewer.MAX_SUPERVISED_THESES),
				nullableString(jReviewer, Reviewer.EMAIL), nullableString(jReviewer, Reviewer.COMMENT),
				jReviewer.getInt(Reviewer.INTERNAL_ID));

	}

	private static JsonArray mapToBachelorThesesJson(List<BachelorThesis> bachelorTheses) {
		List<JsonObject> jThesis = bachelorTheses.stream().map(JsonMapper::mapToBachelorThesisJson)
				.collect(Collectors.toList());
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		jThesis.forEach(arrayBuilder::add);
		return arrayBuilder.build();
	}

	private static JsonObject mapToBachelorThesisJson(BachelorThesis thesis) {
		JsonObjectBuilder thesisBuilder = Json.createObjectBuilder();
		thesisBuilder.add(BachelorThesis.TOPIC, thesis.getTopic());
		thesisBuilder.add(BachelorThesis.AUTHOR, mapToAuthorJson(thesis));

		thesisBuilder.add(FIRST_REVIEWER, thesis.getFirstReview().getReviewer().getInternalId());
		thesis.getSecondReview()
				.ifPresent(review -> thesisBuilder.add(SECOND_REVIEWER, review.getReviewer().getInternalId()));
		thesis.getSecondReview()
				.ifPresent(review -> thesisBuilder.add(SECOND_REVIEW_STATUS, nonNull(review.getStatus().name())));
		return thesisBuilder.build();
	}

	private static JsonObjectBuilder mapToAuthorJson(BachelorThesis thesis) {
		JsonObjectBuilder authorBuilder = Json.createObjectBuilder();
		authorBuilder.add(Author.NAME, nonNull(thesis.getAuthor().getName()));
		authorBuilder.add(Author.STUDY_GROUP, nonNull(thesis.getAuthor().getStudyGroup()));
		return authorBuilder;
	}

	private static JsonArray mapToReviewersJson(List<Reviewer> reviewers) {
		List<JsonObject> jReviewer = reviewers.stream().map(JsonMapper::mapToReviewerJson).collect(Collectors.toList());
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		jReviewer.forEach(arrayBuilder::add);
		return arrayBuilder.build();
	}

	private static JsonObject mapToReviewerJson(Reviewer r) {
		JsonObjectBuilder reviewerbuilder = Json.createObjectBuilder();
		reviewerbuilder.add(Reviewer.NAME, nonNull(r.getName()));
		reviewerbuilder.add(Reviewer.EMAIL, nonNull(r.getEmail()));
		reviewerbuilder.add(Reviewer.COMMENT, nonNull(r.getComment()));
		reviewerbuilder.add(Reviewer.MAX_SUPERVISED_THESES, r.getMaxSupervisedThesis());
		reviewerbuilder.add(Reviewer.INTERNAL_ID, r.getInternalId());

		return reviewerbuilder.build();
	}

	private static String nonNull(String s) {
		return Optional.ofNullable(s).orElse(EMPTY_STRING);
	}

	private static String nullableString(JsonObject jReviewer, String key) {
		if (jReviewer.containsKey(key)) {
			return jReviewer.getString(key);
		} else {
			return EMPTY_STRING;
		}
	}

	private static Reviewer findReviewerById(int internalId, List<Reviewer> reviewers) {
		return reviewers.stream().filter(reviewer -> reviewer.getInternalId() == internalId).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
