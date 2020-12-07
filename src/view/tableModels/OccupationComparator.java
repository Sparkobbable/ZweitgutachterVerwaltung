package view.tableModels;

import java.util.Comparator;
import java.util.Objects;

public class OccupationComparator implements Comparator<Double[]> {

	@Override
	public int compare(Double[] occupation1, Double[] occupation2) {
		Double double1 = occupation1[2];
		Double double2 = occupation2[2];
		
		if (Objects.isNull(double1)) {
			return -1;
		} else if (Objects.isNull(double2)) {
			return 1;
		}
		
		return double1.compareTo(double2);
	}

}
