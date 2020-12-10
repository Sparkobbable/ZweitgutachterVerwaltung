package util;

import java.util.List;

import model.Pair;

public class UmlautFormatter {
	private static final Pair<String, String> UE = new Pair<String, String>("Ã¼", "ü");
	private static final Pair<String, String> AE = new Pair<String, String>("Ã¤", "ä");
	private static final Pair<String, String> OE = new Pair<String, String>("Ã¶", "ö");
	private static final Pair<List<String>, String> SZ = new Pair<List<String>, String>(List.of("ÃŸ", "Ã"), "ß"); //Second UTF-8 Code for ß is not visible but there
	private static final Pair<String, String> EURO = new Pair<String, String>("", "€"); //UTF-8 Code for € is not visible but there
	
	public static String format(String stringToFormat) {
		if (stringToFormat.contains(UE.getLeft())) {
			stringToFormat = stringToFormat.replace(UE.getLeft(), UE.getRight());
		}
		if (stringToFormat.contains(AE.getLeft())) {
			stringToFormat = stringToFormat.replace(AE.getLeft(), AE.getRight());
		}
		if (stringToFormat.contains(OE.getLeft())) {
			stringToFormat = stringToFormat.replace(OE.getLeft(), OE.getRight());
		}
		if (stringToFormat.contains(EURO.getLeft())) {
			stringToFormat = stringToFormat.replace(EURO.getLeft(), EURO.getRight());
		}
		if (stringToFormat.contains(SZ.getLeft().get(0)) || stringToFormat.contains(SZ.getLeft().get(1))) {
			stringToFormat = stringToFormat.replace(SZ.getLeft().get(0), SZ.getRight());
			stringToFormat = stringToFormat.replace(SZ.getLeft().get(1), SZ.getRight());
		}
		return stringToFormat;
	}
}
