package view.panels.analyse;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
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
import view.panels.collaboration.CollaborationOptionsPanel;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.CollaboratingReviewerTableModel;
import view.tableModels.SingleAnalysisReviewerTableModel;
import view.widgets.BarChart;
import view.widgets.PieChart;

/**
 * Panel showing multiple analysis of collaborating reviewers
 */
@SuppressWarnings("serial") // should not be serialized
public class SingleAnalysisPanel extends DefaultPanel {

	private final String TABLE = "table";
	private final String PIECHART = "pieChart";
	private final String BARCHART = "barChart";
	
	private Model model;
	private CardLayout cardLayout;

	private AbstractViewPanel options;
	private JPanel chart;
	
	private PieChart pieChart;
	private JTable table;
	private Component barChart;
	private SingleAnalysisReviewerTableModel tableModel;
	private JScrollPane tableScrollPane;
	
	private CustomEventSource initializeEventSource;

	public SingleAnalysisPanel(Model model) {
		super("Einzelansicht Gutachter");
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
		this.pieChart = new PieChart("Verteilung Gutachten", this.model);
		this.barChart = new BarChart("Verteilung Gutachten", this.model);
		this.tableModel = new SingleAnalysisReviewerTableModel(this.model);
		this.table = new JTable(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.table);
		
		this.options = new SingleAnalysisOptionsPanel(this.model);
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
			break;
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
		this.onPropertyChange(Model.SINGLE_REVIEWS, (evt) -> this.tableModel.updateData());
	}
}

