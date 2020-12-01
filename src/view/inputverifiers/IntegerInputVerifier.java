package view.inputverifiers;

import java.math.BigInteger;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class IntegerInputVerifier extends InputVerifier{

	private static final BigInteger MAX_INT = BigInteger.valueOf(Integer.MAX_VALUE);
	@Override
	public boolean verify(JComponent input) {
		JTextField tf = (JTextField) input;
		String text = tf.getText();
		if (!text.matches("\\d*")) {
			return false;
		}
		BigInteger intValue = new BigInteger(text);
		return intValue.compareTo(MAX_INT) <= 0;
	}

}
