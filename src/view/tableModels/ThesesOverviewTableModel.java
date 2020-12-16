package view.tableModels;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.table.TableModel;

import model.Model;
import model.domain.BachelorThesis;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ReviewStatus;

/**
 * This {@link AbstractDataTableModel} contains the {@link Column}s {@link #AUTHOR_NAME}, {@link #AUTHOR_STUDY_GROUP}, {@link #TOPIC}, {@link #COMMENT}, 
 * {@link #FIRST_REVIEWER}, {@link #SECOND_REVIEWER} and {@link #SECOND_REVIEWER_STATUS}
 */
public class ThesesOverviewTableModel extends AbstractDataTableModel<BachelorThesis> {

	public static final Column<BachelorThesis, String> AUTHOR_NAME = Column.of("Autor (Name)",
			t -> t.getAuthor().getName(), String.class);
	public static final Column<BachelorThesis, String> AUTHOR_STUDY_GROUP = Column.of("Autor (Studiengruppe)",
			t -> t.getAuthor().getStudyGroup(), String.class);
	public static final Column<BachelorThesis, String> TOPIC = Column.of("Thema", t -> t.getTopic(), String.class);
	public static final Column<BachelorThesis, String> COMMENT = Column.of("Bemerkung", t -> t.getComment(), String.class);
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

	/**
	 * Creates a new {@link TableModel} for presenting a list of {@link BachelorThesis}
	 * @param columns			The {@link Column}s to be presented
	 * @param filters			The filters to be applied to the list
	 * @param model				The {@link Model} for accessing the data
	 */
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
