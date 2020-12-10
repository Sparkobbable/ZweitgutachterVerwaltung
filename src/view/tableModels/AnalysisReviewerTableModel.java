package view.tableModels;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.domain.Reviewer;

public class AnalysisReviewerTableModel extends AbstractDataTableModel<Reviewer> {

		private static final long serialVersionUID = 1L;
		private Model model;

		public static final Column<Reviewer, String> NAME = Column.of("Name", Reviewer::getName, String.class);
		public static final Column<Reviewer, Integer> FIRSTREVIEW_COUNT = Column.of("Anzahl Erstgutachten",
				Reviewer::getFirstReviewCount, Integer.class);
		public static final Column<Reviewer, Integer> SECONDREVIEW_COUNT = Column.of("Anzahl Zweitgutachten",
				Reviewer::getSecondReviewCount, Integer.class);

		public AnalysisReviewerTableModel(List<Column<Reviewer, ?>> columns, List<Predicate<Reviewer>> filters, Model model) {
			super(columns, filters);
			this.model = model;
		}

		public AnalysisReviewerTableModel(Model model) {
			this(List.of(NAME, FIRSTREVIEW_COUNT, SECONDREVIEW_COUNT), Collections.emptyList(), model);
		}

		@Override
		protected Collection<Reviewer> getUnfilteredData() {
			return this.model.getAnalyseReviewers();
		}

}
