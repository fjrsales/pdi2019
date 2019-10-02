package point_operations;

import ij.ImagePlus;
import ij.gui.Plot;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Histogram_ implements PlugInFilter {

	private int LEVELS = 256;
		
	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		byte[] pixels = (byte[]) ip.getPixels();		//Pegando todos os pixels
		double[] counts = new double[LEVELS];
		double[] tons = new double[LEVELS];
		for (int i = 0; i < LEVELS; i++) {
			tons[i] = i;
		}
		
		for (int i = 0; i < pixels.length; i++) {
			int temp = (int) 0xff & pixels[i];				
			counts[temp]++;
		}
		Plot p = new Plot("Histograma", "Tons", "Contagens");
		p.addPoints(tons, counts, Plot.CIRCLE);
//		p.addPoints(tons, counts, 0);
		p.show();
	}

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		return DOES_8G;
	}

}
