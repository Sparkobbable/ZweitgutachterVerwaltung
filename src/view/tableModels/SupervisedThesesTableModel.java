package view.tableModels;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.swing.table.TableModel;

import model.Model;
import model.domain.BachelorThesis;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.ReviewType;

/**
 * This {@link AbstractDataTableModel} contains the {@link Column}s {@link #TITLE}, {@link #AUTHOR_NAME}, {@link #AUTHOR_STUDY_GROUP}, {@link #TYPE}, and {@link #STATUS}
 */
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

	/**
	 * Creates a new {@link TableModel} for presenting a list of {@link BachelorThesis}
	 * @param columns			The {@link Column}s to be presented
	 * @param filters			The filters to be applied to the list
	 * @param selectedReviewer	The {@link Reviewer} to supervise {@link BachelorThesis}
	 */
	public SupervisedThesesTableModel(List<Column<Review, ?>> columns, List<Predicate<Review>> filters,
			Optional<Reviewer> selectedReviewer) {
		super(columns, filters);
		selectedReviewer.ifPresent(this::setSelectedReviewer);

	}

	/**
	 * Creates a new {@link TableModel} for presenting a list of {@link BachelorThesis}
	 * <p> features the {@link Column}s {@link #TITLE}, {@link #AUTHOR_NAME}, {@link #AUTHOR_STUDY_GROUP}, {@link #TYPE}, and {@link #STATUS}
	 * @param selectedReviewer		Needs the {@link Reviewer} for data accessing
	 */
	public void setSelectedReviewer(Reviewer selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
		this.updateData();
	}

	@Override
	protected Collection<Review> getUnfilteredData() {
		return this.selectedReviewer.getAllSupervisedReviews();
	}

}
