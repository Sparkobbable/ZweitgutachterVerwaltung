package view.tableModels;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.domain.BachelorThesis;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ReviewStatus;

public class ThesesOverviewTableModel extends AbstractDataTableModel<BachelorThesis> {

	public static final Column<BachelorThesis, String> AUTHOR_NAME = Column.of("Autor (Name)",
			t -> t.getAuthor().getName(), String.class);
	public static final Column<BachelorThesis, String> AUTHOR_STUDY_GROUP = Column.of("Autor (Studiengruppe)",
			t -> t.getAuthor().getStudyGroup(), String.class);
	public static final Column<BachelorThesis, String> TOPIC = Column.of("Thema", t -> t.getTopic(), String.class);
	public static final LinkedColumn<BachelorThesis, String, Reviewer> FIRST_REVIEWER = LinkedColumn.of("Erstgutachter",
			t -> t.getFirstReview().getReviewer().getName(), t -> t.getFirstReview().getReviewer(), String.class);
	public static final LinkedColumn<BachelorThesis, String, Reviewer> SECOND_REVIEWER = LinkedColumn.of(
			"Zweitgutachter", t -> t.getSecondReview().map(Review::getReviewer).map(Reviewer::getName).orElse("-"),
			t -> t.getSecondReview().map(Review::getReviewer).orElse(null), String.class);
	public static final Column<BachelorThesis, String> SECOND_REVIEWER_STATUS = Column.of("Zweitgutachter-Status",
			t -> t.getSecondReview().map(SecondReview::getStatus).map(ReviewStatus::getLabel).orElse("-"),
			String.class);

	private static final long serialVersionUID = 1L;

	private Model model;

	public ThesesOverviewTableModel(List<Column<BachelorThesis, ?>> columns, List<Predicate<BachelorThesis>> filters,
			Model model) {
		super(columns, filters);
		this.model = model;
	}

	@Override
	protected Collection<BachelorThesis> getUnfilteredData() {
		return model.getTheses();
	}

}
