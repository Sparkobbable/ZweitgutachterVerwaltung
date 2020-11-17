package view.tableModels;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.data.BachelorThesis;
import model.data.Review;
import model.data.Reviewer;

public class ThesesOverviewTableModel extends AbstractDataTableModel<BachelorThesis> {

	public static final Column<BachelorThesis, String> AUTHOR_NAME = Column.of("Autor (Name)",
			t -> t.getAuthor().getName(), String.class);
	public static final Column<BachelorThesis, String> AUTHOR_STUDY_GROUP = Column.of("Autor (Studiengruppe)",
			t -> t.getAuthor().getStudyGroup(), String.class);
	public static final Column<BachelorThesis, String> TOPIC = Column.of("Thema", t -> t.getTopic(), String.class);
	public static final Column<BachelorThesis, String> FIRST_REVIEWER = Column.of("Erstgutachter",
			t -> t.getFirstReview().getReviewer().getName(), String.class);
	public static final Column<BachelorThesis, String> SECOND_REVIEWER = Column.of("Zweitgutachter",
			t -> t.getSecondReview().map(Review::getReviewer).map(Reviewer::getName).orElse("-"), String.class);

	private static final long serialVersionUID = 1L;

	private Model model;

	public ThesesOverviewTableModel(List<Column<BachelorThesis, ?>> columns, List<Predicate<BachelorThesis>> filters,
			Model model) {
		super(columns, filters);
		this.model = model;
	}

	public ThesesOverviewTableModel(Model model) {
		this(List.of(AUTHOR_NAME, AUTHOR_STUDY_GROUP, TOPIC, FIRST_REVIEWER, SECOND_REVIEWER), Collections.emptyList(),
				model);
	}

	@Override
	protected Collection<BachelorThesis> getUnfilteredData() {
		return model.getDisplayedTheses();
	}

}
