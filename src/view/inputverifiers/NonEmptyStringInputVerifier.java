package view.inputverifiers;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class NonEmptyStringInputVerifier extends InputVerifier{

	@Override
	public boolean verify(JComponent input) {
		JTextField tf = (JTextField) input;
		return tf.getText() != null && !tf.getText().isBlank();
	}
}
