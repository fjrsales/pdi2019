package point_operations;

public class HistogramUtil {
	
	public static int getHistogramMax(int[] hist) {
		// TODO Auto-generated method stub
		int max = 255;
		for (int i = 255; i >= 0; i--) {
			if (hist[i] != 0) {
				max = i;
				break;
			}		
		}
		return max;
	}

	public static int getHistogramMin(int[] hist) {
		// TODO Auto-generated method stub
		int min = 0;
		for (int i = 0; i < hist.length; i++) {
			if (hist[i] != 0) {
				min = i;
				break;
			}		
		}
		return min;
	}

}
