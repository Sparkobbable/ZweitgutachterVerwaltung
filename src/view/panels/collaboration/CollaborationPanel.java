package view.panels.collaboration;

import java.awt.BorderLayout;
import java.util.List;

import controller.events.CustomEventSource;
import controller.events.EventSource;
import model.Model;
import model.enums.EventId;
import view.ViewProperties;
import view.ViewState;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;

@SuppressWarnings("serial") // should not be serialized
public class CollaborationPanel extends DefaultPanel {

	private Model model;

	private AbstractViewPanel options;
	private AbstractViewPanel chart;
	
	private PieChart pieChart;
	private CollaborationTable table;
	
	private CustomEventSource initializeEventSource;

	public CollaborationPanel(Model model) {
		super("Zusammenarbeit anzeigen");
		this.model = model;
		this.initializeEventSource = new CustomEventSource(EventId.INITIALIZE);
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new BorderLayout());

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.observe(this.model);
	}

	private void createUIElements() {
		this.pieChart = new PieChart(this.model);
		this.table = new CollaborationTable(this.model);
		
		this.options = new CollaborationOptionsPanel(this.model);
		this.chart = this.table;									
	}

	private void addUIElements() {
		this.add(this.options, BorderLayout.PAGE_START);
		this.add(this.chart, BorderLayout.CENTER);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.options, this.initializeEventSource);
	}

	@Override
	public void initializeState(ViewState viewState) {
		switch (viewState) {
		case TABLE:
			this.remove(chart);
			this.chart = this.table;
			this.add(chart);
			break;
		case PIECHART:
			this.remove(chart);
			this.chart = this.pieChart;
			this.add(chart);
			break;
		default:
			throw new IllegalArgumentException("Invalid ViewState");
		}
		
		initializeEventSource.trigger();
	}
}
