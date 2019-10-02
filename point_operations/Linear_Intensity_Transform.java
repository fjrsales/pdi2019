package point_operations;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Linear_Intensity_Transform implements PlugInFilter {

	@Override
	public void run(ImageProcessor ip) {
		// TODO Auto-generated method stub
		int[] hist = ip.getHistogram();
		int h = ip.getHeight();
		int w = ip.getWidth();

		int min = HistogramUtil.getHistogramMin(hist);
		int max = HistogramUtil.getHistogramMax(hist);
		
		GenericDialog gd = this.createParametersDialog();
		double newMin = 0;
		double newMax = 255;
		
		if (gd.wasOKed()) {
			newMin = (int) gd.getNextNumber();
			newMax = (int) gd.getNextNumber();
		} else {
			IJ.error("ERRO!!!", "Plugin Terminado Prematuramente!");
		}
		
		double a = ((newMax - newMin)/(double)(max - min));
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int temp = ip.get(x,y);
				double dummy = (a*((double)(temp - min))+ newMin);
				ip.set(x, y, (int) Math.round(dummy)); 
			}	
		}
	}

	@Override
	public int setup(String arg0, ImagePlus imp) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}
	
	private GenericDialog createParametersDialog() {
		
		GenericDialog gd = new GenericDialog("Linear Intensity Transform");	
		gd.addNumericField("New Min:", 0, 1);				
		gd.addNumericField("New Max:", 255, 1);
		gd.pack();												
		gd.showDialog();
		return gd;
	}

}
