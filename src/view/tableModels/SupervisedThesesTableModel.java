package view.tableModels;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ReviewType;

public class SupervisedThesesTableModel extends AbstractDataTableModel<Review> {

	private static final long serialVersionUID = 1L;
	private Reviewer selectedReviewer;

	public static final Column<Review, String> TITLE = Column.of("Titel", r -> r.getBachelorThesis().getTopic(),
			String.class);
	public static final Column<Review, String> AUTHOR_NAME = Column.of("Autor (Name)",
			r -> r.getBachelorThesis().getAuthor().getName(), String.class);
	public static final Column<Review, String> AUTHOR_STUDY_GROUP = Column.of("Autor (Studiengruppe)",
			r -> r.getBachelorThesis().getAuthor().getStudyGroup(), String.class);
	public static final Column<Review, String> TYPE = Column.of("Art des Gutachtens",
			r -> r.getReviewType().getLabel(), String.class);
	public static final Column<Review, String> STATUS = Column.of("Status",
			r -> r.getReviewType() == ReviewType.SECOND_REVIEW ? ((SecondReview) r).getStatus().getLabel() : "-",
			String.class);

	public SupervisedThesesTableModel(List<Column<Review, ?>> columns, List<Predicate<Review>> filters,
			Optional<Reviewer> selectedReviewer) {
		super(columns, filters);
		selectedReviewer.ifPresent(this::setSelectedReviewer);

	}

	public void setSelectedReviewer(Reviewer selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
		this.updateData();
	}

	@Override
	protected Collection<Review> getUnfilteredData() {
		return this.selectedReviewer.getAllSupervisedReviews();
	}

}
