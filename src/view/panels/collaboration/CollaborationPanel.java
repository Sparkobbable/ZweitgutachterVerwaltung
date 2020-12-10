package view.panels.collaboration;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.events.CustomEventSource;
import controller.events.EventSource;
import model.Model;
import model.enums.EventId;
import view.ViewProperties;
import view.ViewState;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.CollaboratingReviewerTableModel;
import view.tableModels.ReviewerOverviewTableModel;
import view.widgets.BarChart;
import view.widgets.PieChart;

/**
 * Panel showing multiple analysis of collaborating reviewers
 */
@SuppressWarnings("serial") // should not be serialized
public class CollaborationPanel extends DefaultPanel {

	private final String TABLE = "table";
	private final String PIECHART = "pieChart";
	private final String BARCHART = "barChart";
	
	private Model model;
	private CardLayout cardLayout;

	private AbstractViewPanel options;
	private JPanel chart;
	
	private PieChart pieChart;
	private BarChart barChart;
	private JTable table;
	private CollaboratingReviewerTableModel tableModel;
	private JScrollPane tableScrollPane;
	
	private CustomEventSource initializeEventSource;

	public CollaborationPanel(Model model) {
		super("Zusammenarbeit anzeigen");
		this.model = model;
		this.cardLayout = new CardLayout();
		this.initializeEventSource = new CustomEventSource(EventId.INITIALIZE);
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new BorderLayout());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeHandlers();
		this.observe(this.model);
	}

	private void createUIElements() {
		this.pieChart = new PieChart("Zusammenarbeit der Gutachter", this.model);
		this.barChart = new BarChart("Zusammenarbeit der Gutachter", this.model);
		this.tableModel = new CollaboratingReviewerTableModel(this.model);
		this.table = new JTable(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.table);
		
		this.options = new CollaborationOptionsPanel(this.model);
		this.chart = new JPanel();		
		this.chart.setLayout(cardLayout);
	}

	private void addUIElements() {
		this.add(this.options, BorderLayout.PAGE_START);
		this.add(this.chart, BorderLayout.CENTER);
		this.chart.add(tableScrollPane, TABLE);
		this.chart.add(pieChart, PIECHART);
		this.chart.add(barChart, BARCHART);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.options, this.initializeEventSource);
	}

	/**
	 * Switching the presentation of analysis for each {@link ViewState}
	 */
	@Override
	public void initializeState(ViewState viewState) {
		switch (viewState) {
		case TABLE:
			this.cardLayout.show(chart, TABLE);
			break;
		case PIECHART:
			this.cardLayout.show(chart, PIECHART);
			break;
		case BARCHART:
			this.cardLayout.show(chart, BARCHART);
		default:
			throw new IllegalArgumentException("Invalid ViewState");
		}
		initializeEventSource.trigger();
	}
	
	/**
	 * Defines the methods that should be called when an observed property is
	 * changed
	 */
	private void initializePropertyChangeHandlers() {
		this.onPropertyChange(Model.COLLABORATING_REVIEWERS, (evt) -> this.tableModel.updateData());
	}
}
