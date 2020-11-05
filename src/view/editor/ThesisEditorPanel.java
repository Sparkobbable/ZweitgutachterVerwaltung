package view.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.EventSource;
import model.data.Author;
import model.data.BachelorThesis;
import model.data.Review;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.eventsources.ButtonEventSource;

@SuppressWarnings("deprecation")
public class ThesisEditorPanel extends AbstractView {

	private static final long serialVersionUID = 1L;
	private Optional<BachelorThesis> bachelorThesis;
	private Author author;
	private Optional<Review> firstReview;
	private Optional<Review> secReview;
	
	private JTextField authorName;
	private JTextField topic;
	private JLabel firstReviewerName;
	private JLabel secReviewerName;
	private JButton saveThesis;

	public ThesisEditorPanel(ViewId viewId, String title, Optional<BachelorThesis> bachelorThesis) {
		super(viewId, title);
		this.bachelorThesis = bachelorThesis;
		if (bachelorThesis.isPresent()) {
			this.author = bachelorThesis.get().getAuthor();
			this.firstReview = bachelorThesis.get().getFirstReview();
			this.secReview = bachelorThesis.get().getSecondReview();
		} else {
			this.author = new Author(null);
			this.bachelorThesis = Optional.of(new BachelorThesis().setAuthor(this.author));
			this.firstReview = Optional.empty();
			this.secReview = Optional.empty();
		}
			
		this.setBackground(Color.DARK_GRAY); // TODO only for component identification, remove before launch
		this.setLayout(new GridLayout(4, 1));
		
		this.createUIElements();
		this.registerEventSources();
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(	new ButtonEventSource(EventId.SAVE_THESIS, saveThesis, () -> getThesis()));
	}

	private BachelorThesis getThesis() {
		BachelorThesis thesis = this.bachelorThesis.get();
		thesis.getAuthor().setName(this.authorName.getText());
		thesis.setTopic(this.topic.getText());
		return thesis;
	}

	@Override
	protected void createUIElements() {
			this.authorName = new JTextField(this.bachelorThesis.map(thesis -> this.author.getName()).orElse(null));
			this.topic = new JTextField(this.bachelorThesis.map(thesis -> thesis.getTopic()).orElse(null));
			this.firstReviewerName = new JLabel(this.firstReview.map(review -> review.getReviewer().getName()).orElse(null));
			this.secReviewerName = new JLabel(this.secReview.map(review -> review.getReviewer().getName()).orElse(null));
			this.saveThesis = new JButton("Speichern");
			
			this.add(this.authorName);
			this.add(this.topic);
			this.add(this.firstReviewerName);
			this.add(this.secReviewerName);
			this.add(this.saveThesis);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
