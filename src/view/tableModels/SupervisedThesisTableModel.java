package view.tableModels;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import model.data.Review;
import model.data.Reviewer;
import model.data.SecondReview;
import model.enums.ReviewType;

public class SupervisedThesisTableModel extends AbstractDataTableModel<Review> {

	private static final long serialVersionUID = 1L;
	private Reviewer selectedReviewer;

	private static final Column<Review, String> TITLE = Column.of("Titel", r -> r.getBachelorThesis().getTopic(),
			String.class);
	private static final Column<Review, String> AUTHOR_NAME = Column.of("Autor (Name)",
			r -> r.getBachelorThesis().getAuthor().getName(), String.class);
	private static final Column<Review, String> AUTHOR_STUDY_GROUP = Column.of("Autor (Studiengruppe)",
			r -> r.getBachelorThesis().getAuthor().getStudyGroup(), String.class);
	private static final Column<Review, String> TYPE = Column.of("Art des Gutachtens",
			r -> r.getReviewType().getLabel(), String.class);
	private static final Column<Review, String> STATUS = Column.of("Status",
			r -> r.getReviewType() == ReviewType.SECOND_REVIEW ? ((SecondReview) r).getStatus().getLabel() : "-",
			String.class);

	public SupervisedThesisTableModel(List<Column<Review, ?>> columns, List<Predicate<Review>> filters,
			Optional<Reviewer> selectedReviewer) {
		super(columns, filters);
		selectedReviewer.ifPresent(this::setSelectedReviewer);

	}

	public SupervisedThesisTableModel(Optional<Reviewer> selectedReviewer) {
		this(List.of(TITLE, AUTHOR_NAME, AUTHOR_STUDY_GROUP, TYPE, STATUS), Collections.emptyList(), selectedReviewer);

	}

	public void setSelectedReviewer(Reviewer selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
		this.updateData();
	}

	@Override
	protected Collection<Review> getUnfilteredData() {
		return this.selectedReviewer.getAllReviews();
	}

}
